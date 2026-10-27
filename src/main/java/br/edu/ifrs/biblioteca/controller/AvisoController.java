package br.edu.ifrs.biblioteca.controller;

import br.edu.ifrs.biblioteca.dto.AvisoResponse;
import br.edu.ifrs.biblioteca.service.AvisoService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avisos")
public class AvisoController {

  private final AvisoService service;

  public AvisoController(AvisoService service) {
    this.service = service;
  }

  @GetMapping
  public List<AvisoResponse> listar() {
    return service.listar().stream().map(AvisoResponse::de).toList();
  }
}
