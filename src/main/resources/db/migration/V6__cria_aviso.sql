CREATE TABLE aviso (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    emprestimo_id    BIGINT NOT NULL,
    data_aviso       DATE   NOT NULL,
    dias_para_vencer INT    NOT NULL,
    CONSTRAINT uq_aviso_emprestimo_dia UNIQUE (emprestimo_id, data_aviso),
    CONSTRAINT fk_aviso_emprestimo FOREIGN KEY (emprestimo_id) REFERENCES emprestimo (id) ON DELETE CASCADE
);
