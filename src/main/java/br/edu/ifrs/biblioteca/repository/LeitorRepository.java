package br.edu.ifrs.biblioteca.repository;

import br.edu.ifrs.biblioteca.model.Leitor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeitorRepository extends JpaRepository<Leitor, Long> {

  List<Leitor> findAllByOrderByIdAsc();

  boolean existsByEmail(String email);
}
