package br.edu.ifrs.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "livro")
public class Livro {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 120)
  private String titulo;

  @Column(nullable = false, length = 120)
  private String autor;

  @Column(nullable = false, length = 20, unique = true)
  private String isbn;

  @Column(nullable = false)
  private int copias;

  protected Livro() {}

  public Livro(String titulo, String autor, String isbn, int copias) {
    this.titulo = titulo;
    this.autor = autor;
    this.isbn = isbn;
    this.copias = copias;
  }

  public Long getId() {
    return id;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getAutor() {
    return autor;
  }

  public String getIsbn() {
    return isbn;
  }

  public int getCopias() {
    return copias;
  }
}
