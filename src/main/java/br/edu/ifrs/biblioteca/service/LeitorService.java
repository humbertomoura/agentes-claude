package br.edu.ifrs.biblioteca.service;

import br.edu.ifrs.biblioteca.dto.CriarLeitorRequest;
import br.edu.ifrs.biblioteca.model.Leitor;
import br.edu.ifrs.biblioteca.repository.LeitorRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LeitorService {

  private final LeitorRepository leitores;

  public LeitorService(LeitorRepository leitores) {
    this.leitores = leitores;
  }

  public Leitor criar(CriarLeitorRequest req) {
    if (req.nome() == null || req.nome().isBlank()) {
      throw new IllegalArgumentException("O nome é obrigatório");
    }
    if (req.email() == null || !req.email().contains("@")) {
      throw new IllegalArgumentException("O e-mail é inválido");
    }
    String email = req.email().trim().toLowerCase();
    if (leitores.existsByEmail(email)) {
      throw new IllegalStateException("E-mail já cadastrado");
    }
    return leitores.save(new Leitor(req.nome().trim(), email));
  }

  @Transactional(readOnly = true)
  public List<Leitor> listar() {
    return leitores.findAllByOrderByIdAsc();
  }

  @Transactional(readOnly = true)
  public Leitor buscar(Long id) {
    return leitores.findById(id).orElseThrow(() -> new NaoEncontradoException("Leitor", id));
  }
}
