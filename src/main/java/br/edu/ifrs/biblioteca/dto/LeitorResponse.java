package br.edu.ifrs.biblioteca.dto;

import br.edu.ifrs.biblioteca.model.Leitor;

public record LeitorResponse(Long id, String nome, String email) {

  public static LeitorResponse de(Leitor l) {
    return new LeitorResponse(l.getId(), l.getNome(), l.getEmail());
  }
}
