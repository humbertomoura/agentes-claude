CREATE TABLE emprestimo (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    livro_id     BIGINT      NOT NULL REFERENCES livro (id),
    leitor_id    BIGINT      NOT NULL REFERENCES leitor (id),
    status       VARCHAR(20) NOT NULL,
    emprestado_em DATE       NOT NULL
);
