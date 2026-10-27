package br.edu.ifrs.biblioteca.service;

import br.edu.ifrs.biblioteca.dto.EmprestarRequest;
import br.edu.ifrs.biblioteca.model.Emprestimo;
import br.edu.ifrs.biblioteca.model.Leitor;
import br.edu.ifrs.biblioteca.model.Livro;
import br.edu.ifrs.biblioteca.model.StatusEmprestimo;
import br.edu.ifrs.biblioteca.repository.EmprestimoRepository;
import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmprestimoService {

  /** Todo empréstimo vence 14 dias depois da data em que foi feito. */
  static final int PRAZO_PADRAO_DIAS = 14;

  private final EmprestimoRepository emprestimos;
  private final LivroService livros;
  private final LeitorService leitores;
  private final Clock clock;

  public EmprestimoService(
      EmprestimoRepository emprestimos, LivroService livros, LeitorService leitores, Clock clock) {
    this.emprestimos = emprestimos;
    this.livros = livros;
    this.leitores = leitores;
    this.clock = clock;
  }

  public Emprestimo emprestar(EmprestarRequest req) {
    if (req.livroId() == null || req.leitorId() == null) {
      throw new IllegalArgumentException("Informe o livro e o leitor");
    }
    Livro livro = livros.buscar(req.livroId());
    Leitor leitor = leitores.buscar(req.leitorId());
    if (livros.disponiveis(livro) < 1) {
      throw new IllegalStateException("Nenhum exemplar disponível");
    }
    return emprestimos.save(new Emprestimo(livro, leitor, hoje()));
  }

  @Transactional(readOnly = true)
  public List<Emprestimo> listar(StatusEmprestimo status) {
    if (status != null) {
      return emprestimos.findByStatusOrderByIdAsc(status);
    }
    return emprestimos.findAllByOrderByIdAsc();
  }

  @Transactional(readOnly = true)
  public Emprestimo buscar(Long id) {
    return emprestimos.findById(id).orElseThrow(() -> new NaoEncontradoException("Empréstimo", id));
  }

  public Emprestimo devolver(Long id) {
    Emprestimo e = buscar(id);
    e.devolver(hoje());
    return e;
  }

  public Emprestimo registrarExtravio(Long id) {
    Emprestimo e = buscar(id);
    e.registrarExtravio();
    return e;
  }

  /** Dias de atraso de um empréstimo aberto; zero se está em dia ou já foi encerrado. */
  public long calcularAtraso(Emprestimo e) {
    if (e.getStatus() != StatusEmprestimo.ABERTO) {
      return 0;
    }
    LocalDate vencimento = e.getEmprestadoEm().plusDays(PRAZO_PADRAO_DIAS);
    long dias = ChronoUnit.DAYS.between(vencimento, hoje());
    return Math.max(dias, 0);
  }

  public boolean estaAtrasado(Emprestimo e) {
    return calcularAtraso(e) > 0;
  }

  private LocalDate hoje() {
    return LocalDate.now(clock);
  }
}
