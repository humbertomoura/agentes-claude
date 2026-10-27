package br.edu.ifrs.biblioteca.service;

import br.edu.ifrs.biblioteca.model.Aviso;
import br.edu.ifrs.biblioteca.model.Emprestimo;
import br.edu.ifrs.biblioteca.model.StatusEmprestimo;
import br.edu.ifrs.biblioteca.repository.AvisoRepository;
import br.edu.ifrs.biblioteca.repository.EmprestimoRepository;
import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvisoService {

  static final int ANTECEDENCIA_DIAS = 2;

  private final EmprestimoRepository emprestimos;
  private final AvisoRepository avisos;
  private final Clock clock;

  public AvisoService(EmprestimoRepository emprestimos, AvisoRepository avisos, Clock clock) {
    this.emprestimos = emprestimos;
    this.avisos = avisos;
    this.clock = clock;
  }

  /**
   * Gera os avisos de hoje: um empréstimo aberto, com vencimento de hoje até daqui a dois dias,
   * ganha um aviso. É idempotente: rodar de novo no mesmo dia não cria avisos repetidos.
   *
   * @return quantos avisos novos foram criados
   */
  @Transactional
  public int gerarAvisosDoDia() {
    LocalDate hoje = LocalDate.now(clock);
    List<Emprestimo> vencendo =
        emprestimos.findByStatusAndVencimentoBetweenOrderByIdAsc(
            StatusEmprestimo.ABERTO, hoje, hoje.plusDays(ANTECEDENCIA_DIAS));
    int criados = 0;
    for (Emprestimo e : vencendo) {
      if (!avisos.existsByEmprestimoIdAndDataAviso(e.getId(), hoje)) {
        int dias = (int) ChronoUnit.DAYS.between(hoje, e.getVencimento());
        avisos.save(new Aviso(e.getId(), hoje, dias));
        criados++;
      }
    }
    return criados;
  }

  public List<Aviso> listar() {
    return avisos.findAllByOrderByDataAvisoDescIdDesc();
  }
}
