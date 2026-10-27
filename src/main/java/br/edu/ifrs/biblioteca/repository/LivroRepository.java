package br.edu.ifrs.biblioteca.repository;

import br.edu.ifrs.biblioteca.model.Livro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {

  List<Livro> findAllByOrderByIdAsc();

  boolean existsByIsbn(String isbn);
}
