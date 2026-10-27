package br.edu.ifrs.biblioteca.controller;

import br.edu.ifrs.biblioteca.dto.CriarLivroRequest;
import br.edu.ifrs.biblioteca.dto.LivroResponse;
import br.edu.ifrs.biblioteca.model.Livro;
import br.edu.ifrs.biblioteca.service.LivroService;
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
@RequestMapping("/livros")
public class LivroController {

  private final LivroService service;

  public LivroController(LivroService service) {
    this.service = service;
  }

  @GetMapping
  public List<LivroResponse> listar() {
    return service.listar().stream().map(this::paraResposta).toList();
  }

  @GetMapping("/{id}")
  public LivroResponse buscar(@PathVariable Long id) {
    return paraResposta(service.buscar(id));
  }

  @PostMapping
  public ResponseEntity<LivroResponse> criar(@RequestBody CriarLivroRequest req) {
    Livro l = service.criar(req);
    return ResponseEntity.created(URI.create("/livros/" + l.getId())).body(paraResposta(l));
  }

  private LivroResponse paraResposta(Livro l) {
    return LivroResponse.de(l, service.disponiveis(l));
  }
}
