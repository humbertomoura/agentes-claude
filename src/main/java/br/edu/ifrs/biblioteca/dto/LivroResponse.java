package br.edu.ifrs.biblioteca.dto;

import br.edu.ifrs.biblioteca.model.Livro;

public record LivroResponse(
    Long id, String titulo, String autor, String isbn, int copias, long disponiveis) {

  public static LivroResponse de(Livro l, long disponiveis) {
    return new LivroResponse(
        l.getId(), l.getTitulo(), l.getAutor(), l.getIsbn(), l.getCopias(), disponiveis);
  }
}
