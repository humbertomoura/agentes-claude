package br.edu.ifrs.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "emprestimo")
public class Emprestimo {

  /** Todo empréstimo, e cada renovação, vale 14 dias. */
  public static final int PRAZO_DIAS = 14;

  public static final int MAX_RENOVACOES = 2;

  /** Multa por dia de atraso, com um teto por empréstimo. */
  public static final BigDecimal MULTA_POR_DIA = new BigDecimal("0.50");

  public static final BigDecimal MULTA_MAXIMA = new BigDecimal("20.00");

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "livro_id")
  private Livro livro;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "leitor_id")
  private Leitor leitor;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private StatusEmprestimo status;

  @Column(name = "emprestado_em", nullable = false)
  private LocalDate emprestadoEm;

  @Column(name = "devolvido_em")
  private LocalDate devolvidoEm;

  @Column(nullable = false)
  private LocalDate vencimento;

  @Column(nullable = false)
  private int renovacoes;

  @Column(name = "multa_cobrada", nullable = false)
  private BigDecimal multaCobrada = BigDecimal.ZERO;

  protected Emprestimo() {}

  public Emprestimo(Livro livro, Leitor leitor, LocalDate emprestadoEm) {
    this.livro = livro;
    this.leitor = leitor;
    this.emprestadoEm = emprestadoEm;
    this.vencimento = emprestadoEm.plusDays(PRAZO_DIAS);
    this.status = StatusEmprestimo.ABERTO;
  }

  public void renovar(LocalDate hoje) {
    if (status != StatusEmprestimo.ABERTO) {
      throw new IllegalStateException("Só é possível renovar um empréstimo aberto");
    }
    if (estaAtrasado(hoje)) {
      throw new IllegalStateException("Empréstimo atrasado não pode ser renovado");
    }
    if (renovacoes >= MAX_RENOVACOES) {
      throw new IllegalStateException("Limite de renovações atingido");
    }
    vencimento = vencimento.plusDays(PRAZO_DIAS);
    renovacoes++;
  }

  /** Um empréstimo aberto está atrasado depois do vencimento; no dia do vencimento ainda não. */
  public boolean estaAtrasado(LocalDate hoje) {
    return status == StatusEmprestimo.ABERTO && hoje.isAfter(vencimento);
  }

  public long diasDeAtraso(LocalDate hoje) {
    if (!estaAtrasado(hoje)) {
      return 0;
    }
    return ChronoUnit.DAYS.between(vencimento, hoje);
  }

  /** Multa em aberto de um empréstimo atrasado, ou a que foi cobrada na devolução. */
  public BigDecimal multa(LocalDate hoje) {
    if (status != StatusEmprestimo.ABERTO) {
      return multaCobrada;
    }
    BigDecimal multa = MULTA_POR_DIA.multiply(BigDecimal.valueOf(diasDeAtraso(hoje)));
    return multa.min(MULTA_MAXIMA).setScale(2);
  }

  public void registrarMulta(BigDecimal multa) {
    this.multaCobrada = multa;
  }

  public void devolver(LocalDate hoje) {
    if (status != StatusEmprestimo.ABERTO) {
      throw new IllegalStateException("Só é possível devolver um empréstimo aberto");
    }
    status = StatusEmprestimo.DEVOLVIDO;
    devolvidoEm = hoje;
  }

  public void registrarExtravio() {
    if (status != StatusEmprestimo.ABERTO) {
      throw new IllegalStateException("Só é possível registrar extravio de um empréstimo aberto");
    }
    status = StatusEmprestimo.EXTRAVIADO;
  }

  public Long getId() {
    return id;
  }

  public Livro getLivro() {
    return livro;
  }

  public Leitor getLeitor() {
    return leitor;
  }

  public StatusEmprestimo getStatus() {
    return status;
  }

  public LocalDate getEmprestadoEm() {
    return emprestadoEm;
  }

  public LocalDate getDevolvidoEm() {
    return devolvidoEm;
  }

  public LocalDate getVencimento() {
    return vencimento;
  }

  public int getRenovacoes() {
    return renovacoes;
  }

  public BigDecimal getMultaCobrada() {
    return multaCobrada;
  }
}
