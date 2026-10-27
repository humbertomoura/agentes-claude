package br.edu.ifrs.biblioteca.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.edu.ifrs.biblioteca.dto.EmprestarRequest;
import br.edu.ifrs.biblioteca.model.Emprestimo;
import br.edu.ifrs.biblioteca.model.Leitor;
import br.edu.ifrs.biblioteca.model.Livro;
import br.edu.ifrs.biblioteca.model.StatusEmprestimo;
import br.edu.ifrs.biblioteca.repository.EmprestimoRepository;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmprestimoServiceTest {

  static final LocalDate HOJE = LocalDate.of(2026, 10, 23);

  EmprestimoRepository repository;
  LivroService livros;
  LeitorService leitores;
  EmprestimoService service;
  Livro livro = new Livro("Estruturas de Dados em Java", "Ana Ribeiro", "9780000000011", 2);
  Leitor leitor = new Leitor("Maria Souza", "maria@exemplo.edu.br");

  @BeforeEach
  void setUp() {
    repository = mock(EmprestimoRepository.class);
    livros = mock(LivroService.class);
    leitores = mock(LeitorService.class);
    when(repository.save(any(Emprestimo.class))).thenAnswer(inv -> inv.getArgument(0));
    when(livros.buscar(1L)).thenReturn(livro);
    when(leitores.buscar(2L)).thenReturn(leitor);
    Clock clock = Clock.fixed(HOJE.atStartOfDay(ZoneId.of("UTC")).toInstant(), ZoneId.of("UTC"));
    service = new EmprestimoService(repository, livros, leitores, clock);
  }

  @Test
  void deveEmprestarLivroDisponivel() {
    when(livros.disponiveis(livro)).thenReturn(1L);

    Emprestimo e = service.emprestar(new EmprestarRequest(1L, 2L));

    assertThat(e.getStatus()).isEqualTo(StatusEmprestimo.ABERTO);
    assertThat(e.getEmprestadoEm()).isEqualTo(HOJE);
  }

  @Test
  void naoDeveEmprestarSemExemplarDisponivel() {
    when(livros.disponiveis(livro)).thenReturn(0L);

    assertThatThrownBy(() -> service.emprestar(new EmprestarRequest(1L, 2L)))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("exemplar");
  }

  @Test
  void deveRejeitarPedidoSemLeitor() {
    assertThatThrownBy(() -> service.emprestar(new EmprestarRequest(1L, null)))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void deveDevolver() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(5));
    when(repository.findById(1L)).thenReturn(Optional.of(e));

    service.devolver(1L);

    assertThat(e.getStatus()).isEqualTo(StatusEmprestimo.DEVOLVIDO);
    assertThat(e.getDevolvidoEm()).isEqualTo(HOJE);
  }

  @Test
  void naoDeveDevolverDuasVezes() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(5));
    e.devolver(HOJE.minusDays(1));
    when(repository.findById(1L)).thenReturn(Optional.of(e));

    assertThatThrownBy(() -> service.devolver(1L)).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void emprestimoNoDiaDoVencimentoNaoTemAtraso() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(14));

    assertThat(service.calcularAtraso(e)).isZero();
    assertThat(service.estaAtrasado(e)).isFalse();
  }

  @Test
  void emprestimoVencidoTemDiasDeAtraso() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(20));

    assertThat(service.calcularAtraso(e)).isEqualTo(6);
    assertThat(service.estaAtrasado(e)).isTrue();
  }

  @Test
  void emprestimoDevolvidoNaoTemAtraso() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(30));
    e.devolver(HOJE.minusDays(2));

    assertThat(service.calcularAtraso(e)).isZero();
  }

  @Test
  void deveRenovarSomandoQuatorzeDiasAoVencimento() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(10));
    when(repository.findById(1L)).thenReturn(Optional.of(e));

    service.renovar(1L);

    assertThat(e.getVencimento()).isEqualTo(HOJE.plusDays(18));
    assertThat(e.getRenovacoes()).isEqualTo(1);
  }

  @Test
  void naoDeveRenovarMaisDeDuasVezes() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(2));
    when(repository.findById(1L)).thenReturn(Optional.of(e));
    service.renovar(1L);
    service.renovar(1L);

    assertThatThrownBy(() -> service.renovar(1L))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("renovações");
  }

  @Test
  void naoDeveRenovarEmprestimoAtrasado() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(20));
    when(repository.findById(1L)).thenReturn(Optional.of(e));

    assertThatThrownBy(() -> service.renovar(1L))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("atrasado");
  }

  @Test
  void multaEMeioRealPorDiaDeAtraso() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(20));

    assertThat(service.calcularMulta(e)).isEqualByComparingTo(new BigDecimal("3.00"));
  }

  @Test
  void multaNaoPassaDoTeto() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(200));

    assertThat(service.calcularMulta(e)).isEqualByComparingTo(new BigDecimal("20.00"));
  }

  @Test
  void emprestimoEmDiaNaoTemMulta() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(14));

    assertThat(service.calcularMulta(e)).isEqualByComparingTo(BigDecimal.ZERO);
  }

  @Test
  void devolucaoAtrasadaRegistraAMultaCobrada() {
    Emprestimo e = new Emprestimo(livro, leitor, HOJE.minusDays(20));
    when(repository.findById(1L)).thenReturn(Optional.of(e));

    service.devolver(1L);

    assertThat(e.getMultaCobrada()).isEqualByComparingTo(new BigDecimal("3.00"));
    assertThat(service.calcularMulta(e)).isEqualByComparingTo(new BigDecimal("3.00"));
  }
}
