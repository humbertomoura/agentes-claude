package br.edu.ifrs.biblioteca.service;

import br.edu.ifrs.biblioteca.dto.CriarLivroRequest;
import br.edu.ifrs.biblioteca.model.Livro;
import br.edu.ifrs.biblioteca.model.StatusEmprestimo;
import br.edu.ifrs.biblioteca.repository.EmprestimoRepository;
import br.edu.ifrs.biblioteca.repository.LivroRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LivroService {

  private final LivroRepository livros;
  private final EmprestimoRepository emprestimos;

  public LivroService(LivroRepository livros, EmprestimoRepository emprestimos) {
    this.livros = livros;
    this.emprestimos = emprestimos;
  }

  public Livro criar(CriarLivroRequest req) {
    if (req.titulo() == null || req.titulo().isBlank()) {
      throw new IllegalArgumentException("O título é obrigatório");
    }
    if (req.autor() == null || req.autor().isBlank()) {
      throw new IllegalArgumentException("O autor é obrigatório");
    }
    if (req.isbn() == null || req.isbn().isBlank()) {
      throw new IllegalArgumentException("O ISBN é obrigatório");
    }
    int copias = req.copias() == null ? 1 : req.copias();
    if (copias < 1) {
      throw new IllegalArgumentException("O livro precisa de ao menos uma cópia");
    }
    String isbn = req.isbn().trim();
    if (livros.existsByIsbn(isbn)) {
      throw new IllegalStateException("ISBN já cadastrado");
    }
    return livros.save(new Livro(req.titulo().trim(), req.autor().trim(), isbn, copias));
  }

  @Transactional(readOnly = true)
  public List<Livro> listar() {
    return livros.findAllByOrderByIdAsc();
  }

  @Transactional(readOnly = true)
  public Livro buscar(Long id) {
    return livros.findById(id).orElseThrow(() -> new NaoEncontradoException("Livro", id));
  }

  /** Cópias que ainda podem ser emprestadas: as devolvidas voltam, as extraviadas não. */
  @Transactional(readOnly = true)
  public long disponiveis(Livro livro) {
    long indisponiveis =
        emprestimos.countByLivroIdAndStatusNot(livro.getId(), StatusEmprestimo.DEVOLVIDO);
    return livro.getCopias() - indisponiveis;
  }
}
