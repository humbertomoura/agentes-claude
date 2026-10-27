package br.edu.ifrs.biblioteca.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.edu.ifrs.biblioteca.dto.CriarLivroRequest;
import br.edu.ifrs.biblioteca.model.Livro;
import br.edu.ifrs.biblioteca.model.StatusEmprestimo;
import br.edu.ifrs.biblioteca.repository.EmprestimoRepository;
import br.edu.ifrs.biblioteca.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LivroServiceTest {

  LivroRepository livros;
  EmprestimoRepository emprestimos;
  LivroService service;

  @BeforeEach
  void setUp() {
    livros = mock(LivroRepository.class);
    emprestimos = mock(EmprestimoRepository.class);
    when(livros.save(any(Livro.class))).thenAnswer(inv -> inv.getArgument(0));
    service = new LivroService(livros, emprestimos);
  }

  @Test
  void deveCriarLivroComUmaCopiaPorPadrao() {
    Livro l = service.criar(new CriarLivroRequest("  Clean Code  ", "Robert Martin", "111", null));

    assertThat(l.getTitulo()).isEqualTo("Clean Code");
    assertThat(l.getCopias()).isEqualTo(1);
  }

  @Test
  void deveRejeitarTituloVazio() {
    assertThatThrownBy(() -> service.criar(new CriarLivroRequest("   ", "Autor", "111", 1)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("título");
  }

  @Test
  void deveRejeitarIsbnDuplicado() {
    when(livros.existsByIsbn("111")).thenReturn(true);

    assertThatThrownBy(() -> service.criar(new CriarLivroRequest("Título", "Autor", "111", 1)))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void copiasDisponiveisDescontamEmprestimosNaoDevolvidos() {
    Livro l = new Livro("Título", "Autor", "111", 3);
    when(emprestimos.countByLivroIdAndStatusNot(null, StatusEmprestimo.DEVOLVIDO)).thenReturn(2L);

    assertThat(service.disponiveis(l)).isEqualTo(1);
  }
}
