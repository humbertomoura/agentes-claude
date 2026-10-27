package br.edu.ifrs.biblioteca.controller;

import br.edu.ifrs.biblioteca.dto.EmprestarRequest;
import br.edu.ifrs.biblioteca.dto.EmprestimoResponse;
import br.edu.ifrs.biblioteca.model.Emprestimo;
import br.edu.ifrs.biblioteca.model.StatusEmprestimo;
import br.edu.ifrs.biblioteca.service.EmprestimoService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

  private final EmprestimoService service;

  public EmprestimoController(EmprestimoService service) {
    this.service = service;
  }

  @GetMapping
  public List<EmprestimoResponse> listar(@RequestParam(required = false) StatusEmprestimo status) {
    return service.listar(status).stream().map(this::paraResposta).toList();
  }

  @GetMapping("/{id}")
  public EmprestimoResponse buscar(@PathVariable Long id) {
    return paraResposta(service.buscar(id));
  }

  @PostMapping
  public ResponseEntity<EmprestimoResponse> emprestar(@RequestBody EmprestarRequest req) {
    Emprestimo e = service.emprestar(req);
    return ResponseEntity.created(URI.create("/emprestimos/" + e.getId())).body(paraResposta(e));
  }

  @PatchMapping("/{id}/devolver")
  public EmprestimoResponse devolver(@PathVariable Long id) {
    return paraResposta(service.devolver(id));
  }

  @PatchMapping("/{id}/extravio")
  public EmprestimoResponse registrarExtravio(@PathVariable Long id) {
    return paraResposta(service.registrarExtravio(id));
  }

  private EmprestimoResponse paraResposta(Emprestimo e) {
    return EmprestimoResponse.de(e, service.calcularAtraso(e));
  }
}
