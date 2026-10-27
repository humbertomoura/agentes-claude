# Biblioteca

API REST de uma biblioteca universitária: acervo, leitores e empréstimos. Java 21, Spring Boot 3.3.5, Maven, H2 em memória e Flyway.

## Comandos

- `./mvnw test`: roda os testes unitários (`*Test`) e de integração (`*IT`).
- `./mvnw spring-boot:run`: sobe a API em `localhost:8080`.
- `./mvnw spotless:apply`: formata o código. O `spotless:check` precisa passar.

## Camadas

`controller` → `service` → `repository` → `model`. Os controllers só convertem entre DTO e domínio, as regras de negócio ficam em `service` e `model`, e o `repository` só acessa dados. O esquema muda apenas por migrations do Flyway em `src/main/resources/db/migration`; o Hibernate só valida (`ddl-auto=validate`).

## Convenções

- Pacote `br.edu.ifrs.biblioteca`; código e mensagens de erro em português.
- O "hoje" vem sempre do `Clock` injetado, nunca de `LocalDate.now()` solto. Os testes fixam a data em 23/10/2026.
- Erros de validação viram `IllegalArgumentException` (400) e transições inválidas, `IllegalStateException` (409).
- Dinheiro usa `BigDecimal`, nunca `double`.
- Commits no padrão Conventional Commits (`feat`, `fix`, `test`, `docs`, `refactor`, `chore`).

## Não faça

- Não altere migrations já aplicadas; crie uma nova.
- Não apague nem enfraqueça testes para fazer a suíte passar.
- Não faça `git push`; quem publica é uma pessoa.
