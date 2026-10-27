ALTER TABLE emprestimo ADD COLUMN vencimento DATE;
UPDATE emprestimo SET vencimento = DATEADD('DAY', 14, emprestado_em);
ALTER TABLE emprestimo ALTER COLUMN vencimento SET NOT NULL;

ALTER TABLE emprestimo ADD COLUMN renovacoes INT DEFAULT 0 NOT NULL;
ALTER TABLE emprestimo ADD COLUMN multa_cobrada DECIMAL(10, 2) DEFAULT 0 NOT NULL;
