# Visão geral da biblioteca

API REST em Java 21 e Spring Boot 3.3.5 para o acervo, os leitores e os empréstimos de uma biblioteca universitária. Os dados ficam em um banco H2 em memória, e o esquema é versionado pelo Flyway.

## Arquitetura em camadas

```
Cliente HTTP
   │  JSON
   ▼
controller/   LivroController, LeitorController, EmprestimoController
              ApiExceptionHandler: NaoEncontradoException → 404,
              IllegalArgumentException → 400, IllegalStateException → 409
   │  usa dto/ (requests e responses)
   ▼
service/      LivroService, LeitorService, EmprestimoService (@Transactional)
   │          EmprestimoService recebe um Clock
   ▼
repository/   Spring Data JPA: LivroRepository, LeitorRepository, EmprestimoRepository
model/        Livro, Leitor, Emprestimo (@Entity) e StatusEmprestimo (enum)
   ▼
H2 em memória (jdbc:h2:mem:biblioteca) — esquema criado pelo Flyway, V1 a V4
```

## Arquivos principais

| Camada | Arquivo | Papel |
|---|---|---|
| Entrada | `BibliotecaApplication.java` | Classe `@SpringBootApplication` |
| Config | `config/ClockConfig.java` | Bean `Clock`, que permite fixar "hoje" nos testes |
| Web | `controller/EmprestimoController.java` | `GET /emprestimos` (com `?status=`), `GET /emprestimos/{id}`, `POST`, `PATCH .../devolver` e `PATCH .../extravio` |
| Web | `controller/LivroController.java` e `LeitorController.java` | `GET`, `GET /{id}` e `POST` |
| Web | `controller/ApiExceptionHandler.java` | Converte exceções em respostas HTTP |
| Negócio | `service/EmprestimoService.java` | Casos de uso e regra de atraso |
| Negócio | `service/LivroService.java` | Validação do cadastro e cópias disponíveis |
| Domínio | `model/Emprestimo.java` | `devolver(hoje)` e `registrarExtravio()`, só a partir de `ABERTO` |
| Dados | `repository/` | Consultas por status e contagem de empréstimos por livro |
| Banco | `db/migration/` | V1 livro e leitor, V2 empréstimo, V3 `devolvido_em`, V4 dados de exemplo |

## Fluxo de um empréstimo

`POST /emprestimos` → `EmprestimoController.emprestar` → `EmprestimoService.emprestar`, que busca o livro e o leitor, confere se há cópia disponível (`LivroService.disponiveis`) e salva um `Emprestimo` com status `ABERTO` e a data de hoje.

## Regra de negócio: atraso

Está em `EmprestimoService.calcularAtraso()`: um empréstimo `ABERTO` vence 14 dias depois de `emprestadoEm` (`PRAZO_PADRAO_DIAS`). Os dias de atraso são a diferença entre hoje e o vencimento, e nunca ficam abaixo de zero. No dia do vencimento, o empréstimo ainda está em dia. Empréstimos devolvidos ou extraviados não têm atraso.

## Cópias disponíveis

Um livro tem `copias` exemplares. As disponíveis são as cópias menos os empréstimos que não foram devolvidos, ou seja, `ABERTO` e `EXTRAVIADO`: a cópia devolvida volta ao acervo, e a extraviada não.

## Testes

- `EmprestimoServiceTest` e `LivroServiceTest`: unitários, com repositórios simulados (Mockito) e `Clock` fixo em 23/10/2026.
- `EmprestimoControllerIT` e `LivroControllerIT`: integração, com o contexto completo, `MockMvc` e o H2 com os dados da V4.
- `./mvnw test` roda os dois tipos (`*Test` e `*IT`).
