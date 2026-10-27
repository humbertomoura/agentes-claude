package br.edu.ifrs.biblioteca.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LivroControllerIT {

  @Autowired MockMvc mvc;

  @Test
  void deveCriarLivroComTodasAsCopiasDisponiveis() throws Exception {
    mvc.perform(
            post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"titulo\":\"Arquitetura Limpa\",\"autor\":\"Robert Martin\","
                        + "\"isbn\":\"LIMPA-1\",\"copias\":2}"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.copias").value(2))
        .andExpect(jsonPath("$.disponiveis").value(2));
  }

  @Test
  void deveRejeitarLivroSemTitulo() throws Exception {
    mvc.perform(
            post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"\",\"autor\":\"Autor\",\"isbn\":\"X-1\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deveRejeitarIsbnJaCadastrado() throws Exception {
    mvc.perform(
            post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"Outro\",\"autor\":\"Autor\",\"isbn\":\"9780000000011\"}"))
        .andExpect(status().isConflict());
  }

  @Test
  void deveMostrarAsCopiasDisponiveisDoAcervo() throws Exception {
    mvc.perform(get("/livros/2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.copias").value(1))
        .andExpect(jsonPath("$.disponiveis").value(0));
  }
}
