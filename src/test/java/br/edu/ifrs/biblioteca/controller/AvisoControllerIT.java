package br.edu.ifrs.biblioteca.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.edu.ifrs.biblioteca.model.Emprestimo;
import br.edu.ifrs.biblioteca.repository.AvisoRepository;
import br.edu.ifrs.biblioteca.repository.EmprestimoRepository;
import br.edu.ifrs.biblioteca.repository.LeitorRepository;
import br.edu.ifrs.biblioteca.repository.LivroRepository;
import br.edu.ifrs.biblioteca.service.AvisoService;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;

// Banco próprio: o H2 do projeto (DB_CLOSE_DELAY=-1) sobrevive entre contextos e vazaria dados
// de outras classes de teste para as contagens daqui.
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:avisos-it;DB_CLOSE_DELAY=-1")
@AutoConfigureMockMvc
class AvisoControllerIT {

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
  @Autowired AvisoService service;
  @Autowired EmprestimoRepository emprestimos;
  @Autowired AvisoRepository avisos;
  @Autowired LivroRepository livros;
  @Autowired LeitorRepository leitores;

  @BeforeEach
  void limpar() {
    avisos.deleteAll();
    emprestimos.deleteAll();
  }

  /** Salva um empréstimo aberto que vence na data pedida (o prazo é de 14 dias). */
  private Emprestimo salvar(LocalDate vencimento) {
    Emprestimo e =
        new Emprestimo(
            livros.findById(1L).orElseThrow(),
            leitores.findById(1L).orElseThrow(),
            vencimento.minusDays(Emprestimo.PRAZO_DIAS));
    return emprestimos.save(e);
  }

  @Test
  void deveGerarAvisosParaVencimentosDeHojeAteDoisDiasDepois() throws Exception {
    salvar(HOJE);
    salvar(HOJE.plusDays(1));
    salvar(HOJE.plusDays(2));

    assertThat(service.gerarAvisosDoDia()).isEqualTo(3);

    mvc.perform(get("/avisos")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void naoDeveGerarAvisoForaDaJanela() {
    salvar(HOJE.minusDays(1));
    salvar(HOJE.plusDays(3));

    assertThat(service.gerarAvisosDoDia()).isZero();
  }

  @Test
  void naoDeveGerarAvisoParaEmprestimoDevolvidoOuExtraviado() {
    Emprestimo devolvido = salvar(HOJE.plusDays(1));
    devolvido.devolver(HOJE);
    emprestimos.save(devolvido);
    Emprestimo extraviado = salvar(HOJE.plusDays(1));
    extraviado.registrarExtravio();
    emprestimos.save(extraviado);

    assertThat(service.gerarAvisosDoDia()).isZero();
  }

  @Test
  void deveSerIdempotenteNoMesmoDia() throws Exception {
    salvar(HOJE.plusDays(1));

    assertThat(service.gerarAvisosDoDia()).isEqualTo(1);
    assertThat(service.gerarAvisosDoDia()).isZero();

    mvc.perform(get("/avisos")).andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  void deveListarOsAvisosComOsDiasParaVencer() throws Exception {
    Emprestimo e = salvar(HOJE.plusDays(1));
    service.gerarAvisosDoDia();

    mvc.perform(get("/avisos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].emprestimoId").value(e.getId()))
        .andExpect(jsonPath("$[0].dataAviso").value("2026-10-23"))
        .andExpect(jsonPath("$[0].diasParaVencer").value(1));
  }

  @Test
  void devePararDeAvisarDepoisDeRenovarParaForaDaJanela() {
    Emprestimo e = salvar(HOJE.plusDays(1));
    e.renovar(HOJE);
    emprestimos.save(e);

    assertThat(service.gerarAvisosDoDia()).isZero();
  }
}
