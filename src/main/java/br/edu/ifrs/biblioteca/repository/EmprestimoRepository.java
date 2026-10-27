package br.edu.ifrs.biblioteca.repository;

import br.edu.ifrs.biblioteca.model.Emprestimo;
import br.edu.ifrs.biblioteca.model.StatusEmprestimo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

  List<Emprestimo> findAllByOrderByIdAsc();

  List<Emprestimo> findByStatusOrderByIdAsc(StatusEmprestimo status);

  List<Emprestimo> findByStatusAndVencimentoBeforeOrderByIdAsc(
      StatusEmprestimo status, LocalDate data);

  long countByLivroIdAndStatusNot(Long livroId, StatusEmprestimo status);
}
