INSERT INTO livro (titulo, autor, isbn, copias) VALUES
  ('Estruturas de Dados em Java', 'Ana Ribeiro', '9780000000011', 2),
  ('Banco de Dados Relacional', 'Carlos Menezes', '9780000000028', 1),
  ('Engenharia de Software Moderna', 'Beatriz Lima', '9780000000035', 3);

INSERT INTO leitor (nome, email) VALUES
  ('Maria Souza', 'maria@exemplo.edu.br'),
  ('João Pereira', 'joao@exemplo.edu.br'),
  ('Lúcia Andrade', 'lucia@exemplo.edu.br');

INSERT INTO emprestimo (livro_id, leitor_id, status, emprestado_em) VALUES
  (1, 1, 'ABERTO', DATE '2026-10-20'),
  (2, 2, 'ABERTO', DATE '2026-10-05'),
  (3, 3, 'ABERTO', DATE '2026-09-28');

INSERT INTO emprestimo (livro_id, leitor_id, status, emprestado_em, devolvido_em) VALUES
  (1, 3, 'DEVOLVIDO', DATE '2026-09-15', DATE '2026-09-20');
