package br.edu.ifrs.biblioteca.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EmprestimoTest {

  static final LocalDate HOJE = LocalDate.of(2026, 10, 23);

  Livro livro = new Livro("Estruturas de Dados em Java", "Ana Ribeiro", "9780000000011", 2);
  Leitor leitor = new Leitor("Maria Souza", "maria@exemplo.edu.br");

  @Test
  void emprestimoVencidoEstaAtrasado() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(20));

    assertThat(e.estaAtrasado(HOJE)).isTrue();
    assertThat(e.diasDeAtraso(HOJE)).isEqualTo(6);
  }

  @Test
  void emprestimoDentroDoPrazoNaoEstaAtrasado() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(5));

    assertThat(e.estaAtrasado(HOJE)).isFalse();
    assertThat(e.diasDeAtraso(HOJE)).isZero();
  }

  @Test
  void emprestimoDevolvidoNaoEstaAtrasado() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(30));
    e.devolver(HOJE.minusDays(2));

    assertThat(e.estaAtrasado(HOJE)).isFalse();
  }

  @Test
  void multaEMeioRealPorDiaDeAtraso() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(20));

    assertThat(e.multa(HOJE)).isEqualByComparingTo(new BigDecimal("3.00"));
  }

  @Test
  void emprestimoNoDiaDoVencimentoNaoEstaAtrasado() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(14));

    assertThat(e.estaAtrasado(HOJE)).isFalse();
    assertThat(e.diasDeAtraso(HOJE)).isZero();
  }

  @Test
  void deveRenovarNoDiaDoVencimento() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(14));

    e.renovar(HOJE);

    assertThat(e.getVencimento()).isEqualTo(HOJE.plusDays(14));
    assertThat(e.getRenovacoes()).isEqualTo(1);
  }

  @Test
  void naoDeveRenovarNoDiaSeguinteAoVencimento() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(15));

    assertThatThrownBy(() -> e.renovar(HOJE)).isInstanceOf(IllegalStateException.class);
  }
}
