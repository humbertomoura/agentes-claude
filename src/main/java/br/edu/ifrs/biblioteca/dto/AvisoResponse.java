package br.edu.ifrs.biblioteca.dto;

import br.edu.ifrs.biblioteca.model.Aviso;
import java.time.LocalDate;

public record AvisoResponse(Long id, Long emprestimoId, LocalDate dataAviso, int diasParaVencer) {

  public static AvisoResponse de(Aviso a) {
    return new AvisoResponse(
        a.getId(), a.getEmprestimoId(), a.getDataAviso(), a.getDiasParaVencer());
  }
}
