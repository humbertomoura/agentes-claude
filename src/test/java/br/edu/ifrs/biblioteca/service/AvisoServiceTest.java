package br.edu.ifrs.biblioteca.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.edu.ifrs.biblioteca.model.Aviso;
import br.edu.ifrs.biblioteca.model.Emprestimo;
import br.edu.ifrs.biblioteca.model.StatusEmprestimo;
import br.edu.ifrs.biblioteca.repository.AvisoRepository;
import br.edu.ifrs.biblioteca.repository.EmprestimoRepository;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AvisoServiceTest {

  static final LocalDate HOJE = LocalDate.of(2026, 10, 23);

  EmprestimoRepository emprestimos;
  AvisoRepository avisos;
  AvisoService service;

  @BeforeEach
  void setUp() {
    emprestimos = mock(EmprestimoRepository.class);
    avisos = mock(AvisoRepository.class);
    Clock clock = Clock.fixed(HOJE.atStartOfDay(ZoneId.of("UTC")).toInstant(), ZoneId.of("UTC"));
    service = new AvisoService(emprestimos, avisos, clock);
  }

  private Emprestimo emprestimoVencendo(long id, LocalDate vencimento) {
    Emprestimo e = mock(Emprestimo.class);
    when(e.getId()).thenReturn(id);
    when(e.getVencimento()).thenReturn(vencimento);
    return e;
  }

  @Test
  void consultaAJanelaDeHojeAteDoisDiasDepois() {
    when(emprestimos.findByStatusAndVencimentoBetweenOrderByIdAsc(any(), any(), any()))
        .thenReturn(List.of());

    service.gerarAvisosDoDia();

    verify(emprestimos)
        .findByStatusAndVencimentoBetweenOrderByIdAsc(
            StatusEmprestimo.ABERTO, HOJE, HOJE.plusDays(2));
  }

  @Test
  void deveCalcularOsDiasParaVencer() {
    Emprestimo e = emprestimoVencendo(7L, HOJE.plusDays(2));
    when(emprestimos.findByStatusAndVencimentoBetweenOrderByIdAsc(any(), any(), any()))
        .thenReturn(List.of(e));
    when(avisos.existsByEmprestimoIdAndDataAviso(7L, HOJE)).thenReturn(false);

    int criados = service.gerarAvisosDoDia();

    assertThat(criados).isEqualTo(1);
    verify(avisos)
        .save(
            argThat(
                (Aviso a) ->
                    a.getEmprestimoId().equals(7L)
                        && a.getDataAviso().equals(HOJE)
                        && a.getDiasParaVencer() == 2));
  }

  @Test
  void naoDeveDuplicarOAvisoDoMesmoDia() {
    Emprestimo e = emprestimoVencendo(7L, HOJE.plusDays(1));
    when(emprestimos.findByStatusAndVencimentoBetweenOrderByIdAsc(any(), any(), any()))
        .thenReturn(List.of(e));
    when(avisos.existsByEmprestimoIdAndDataAviso(7L, HOJE)).thenReturn(true);

    int criados = service.gerarAvisosDoDia();

    assertThat(criados).isZero();
    verify(avisos, never()).save(any(Aviso.class));
  }
}
