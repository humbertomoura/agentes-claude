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
import java.time.LocalDate;

@Entity
@Table(name = "emprestimo")
public class Emprestimo {

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

  protected Emprestimo() {}

  public Emprestimo(Livro livro, Leitor leitor, LocalDate emprestadoEm) {
    this.livro = livro;
    this.leitor = leitor;
    this.emprestadoEm = emprestadoEm;
    this.status = StatusEmprestimo.ABERTO;
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
}
