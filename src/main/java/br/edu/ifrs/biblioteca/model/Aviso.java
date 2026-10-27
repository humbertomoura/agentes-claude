package br.edu.ifrs.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

/** Aviso de que um empréstimo está perto de vencer. Há no máximo um por empréstimo e por dia. */
@Entity
@Table(name = "aviso")
public class Aviso {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "emprestimo_id", nullable = false)
  private Long emprestimoId;

  @Column(name = "data_aviso", nullable = false)
  private LocalDate dataAviso;

  @Column(name = "dias_para_vencer", nullable = false)
  private int diasParaVencer;

  protected Aviso() {}

  public Aviso(Long emprestimoId, LocalDate dataAviso, int diasParaVencer) {
    this.emprestimoId = emprestimoId;
    this.dataAviso = dataAviso;
    this.diasParaVencer = diasParaVencer;
  }

  public Long getId() {
    return id;
  }

  public Long getEmprestimoId() {
    return emprestimoId;
  }

  public LocalDate getDataAviso() {
    return dataAviso;
  }

  public int getDiasParaVencer() {
    return diasParaVencer;
  }
}
