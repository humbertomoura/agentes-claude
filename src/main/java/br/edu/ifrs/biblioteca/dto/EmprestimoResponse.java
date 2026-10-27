package br.edu.ifrs.biblioteca.dto;

import br.edu.ifrs.biblioteca.model.Emprestimo;
import br.edu.ifrs.biblioteca.model.StatusEmprestimo;
import java.time.LocalDate;

public record EmprestimoResponse(
    Long id,
    Long livroId,
    Long leitorId,
    StatusEmprestimo status,
    LocalDate emprestadoEm,
    LocalDate devolvidoEm,
    boolean atrasado,
    long diasDeAtraso) {

  public static EmprestimoResponse de(Emprestimo e, long diasDeAtraso) {
    return new EmprestimoResponse(
        e.getId(),
        e.getLivro().getId(),
        e.getLeitor().getId(),
        e.getStatus(),
        e.getEmprestadoEm(),
        e.getDevolvidoEm(),
        diasDeAtraso > 0,
        diasDeAtraso);
  }
}
