package br.edu.ifrs.biblioteca.controller;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EmprestimoControllerIT {

  static final LocalDate HOJE = LocalDate.of(2026, 10, 23);

  @TestConfiguration
  static class RelogioFixo {
    @Bean
    @Primary
    Clock relogioFixo() {
      return Clock.fixed(HOJE.atStartOfDay(ZoneId.of("UTC")).toInstant(), ZoneId.of("UTC"));
    }
  }

  @Autowired MockMvc mvc;

  /**
   * Cria um livro só deste teste: o H2 sobrevive entre os contextos e outros testes mexem nos
   * dados.
   */
  long novoLivro(int copias) throws Exception {
    String corpo =
        "{\"titulo\":\"Livro de teste\",\"autor\":\"Autor\",\"isbn\":\""
            + UUID.randomUUID().toString().substring(0, 18)
            + "\",\"copias\":"
            + copias
            + "}";
    String local =
        mvc.perform(post("/livros").contentType(MediaType.APPLICATION_JSON).content(corpo))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader("Location");
    return Long.parseLong(local.substring(local.lastIndexOf('/') + 1));
  }

  String emprestar(long livroId, long leitorId) throws Exception {
    String corpo = "{\"livroId\":" + livroId + ",\"leitorId\":" + leitorId + "}";
    return mvc.perform(post("/emprestimos").contentType(MediaType.APPLICATION_JSON).content(corpo))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getHeader("Location");
  }

  @Test
  void deveEmprestarLivro() throws Exception {
    long livro = novoLivro(2);

    mvc.perform(
            post("/emprestimos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"livroId\":" + livro + ",\"leitorId\":1}"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.status").value("ABERTO"))
        .andExpect(jsonPath("$.emprestadoEm").value("2026-10-23"))
        .andExpect(jsonPath("$.atrasado").value(false));
  }

  @Test
  void deveRecusarEmprestimoQuandoNaoHaExemplar() throws Exception {
    long livro = novoLivro(1);
    emprestar(livro, 1);

    mvc.perform(
            post("/emprestimos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"livroId\":" + livro + ",\"leitorId\":2}"))
        .andExpect(status().isConflict());
  }

  @Test
  void deveFiltrarPorStatus() throws Exception {
    mvc.perform(get("/emprestimos").param("status", "DEVOLVIDO"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].status", everyItem(is("DEVOLVIDO"))));
  }

  @Test
  void deveMarcarEmprestimoVencidoComoAtrasado() throws Exception {
    mvc.perform(get("/emprestimos/3"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.atrasado").value(true))
        .andExpect(jsonPath("$.diasDeAtraso").value(11));
  }

  @Test
  void deveDevolverEmprestimo() throws Exception {
    String local = emprestar(novoLivro(1), 2);

    mvc.perform(patch(local + "/devolver"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("DEVOLVIDO"))
        .andExpect(jsonPath("$.devolvidoEm").value("2026-10-23"));
  }

  @Test
  void deveRetornar404ParaEmprestimoInexistente() throws Exception {
    mvc.perform(get("/emprestimos/9999")).andExpect(status().isNotFound());
  }

  @Test
  void deveRenovarEmprestimo() throws Exception {
    String local = emprestar(novoLivro(1), 2);

    mvc.perform(patch(local + "/renovar"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.renovacoes").value(1))
        .andExpect(jsonPath("$.vencimento").value("2026-11-20"));
  }

  @Test
  void deveRecusarRenovacaoDeEmprestimoAtrasado() throws Exception {
    mvc.perform(patch("/emprestimos/3/renovar")).andExpect(status().isConflict());
  }

  @Test
  void deveListarSomenteEmprestimosAtrasados() throws Exception {
    mvc.perform(get("/emprestimos").param("atrasado", "true"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].atrasado", everyItem(is(true))))
        .andExpect(jsonPath("$[*].status", everyItem(is("ABERTO"))));
  }

  @Test
  void deveCalcularAMultaDoEmprestimoAtrasado() throws Exception {
    mvc.perform(get("/emprestimos/3"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.multa").value(5.5));
  }

  @Test
  void deveCompletarOVencimentoDosEmprestimosAntigos() throws Exception {
    mvc.perform(get("/emprestimos/1"))
        .andExpect(jsonPath("$.vencimento").value("2026-11-03"))
        .andExpect(jsonPath("$.renovacoes").value(0));
    mvc.perform(get("/emprestimos/3")).andExpect(jsonPath("$.vencimento").value("2026-10-12"));
  }
}
