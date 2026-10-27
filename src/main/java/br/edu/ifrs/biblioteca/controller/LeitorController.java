package br.edu.ifrs.biblioteca.controller;

import br.edu.ifrs.biblioteca.dto.CriarLeitorRequest;
import br.edu.ifrs.biblioteca.dto.LeitorResponse;
import br.edu.ifrs.biblioteca.model.Leitor;
import br.edu.ifrs.biblioteca.service.LeitorService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leitores")
public class LeitorController {

  private final LeitorService service;

  public LeitorController(LeitorService service) {
    this.service = service;
  }

  @GetMapping
  public List<LeitorResponse> listar() {
    return service.listar().stream().map(LeitorResponse::de).toList();
  }

  @GetMapping("/{id}")
  public LeitorResponse buscar(@PathVariable Long id) {
    return LeitorResponse.de(service.buscar(id));
  }

  @PostMapping
  public ResponseEntity<LeitorResponse> criar(@RequestBody CriarLeitorRequest req) {
    Leitor l = service.criar(req);
    return ResponseEntity.created(URI.create("/leitores/" + l.getId())).body(LeitorResponse.de(l));
  }
}
