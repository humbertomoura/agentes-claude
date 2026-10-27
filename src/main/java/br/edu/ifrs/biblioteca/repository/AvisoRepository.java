package br.edu.ifrs.biblioteca.repository;

import br.edu.ifrs.biblioteca.model.Aviso;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisoRepository extends JpaRepository<Aviso, Long> {

  boolean existsByEmprestimoIdAndDataAviso(Long emprestimoId, LocalDate dataAviso);

  List<Aviso> findAllByOrderByDataAvisoDescIdDesc();
}
