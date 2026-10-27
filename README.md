# Biblioteca

API REST de uma biblioteca universitária: acervo, leitores e empréstimos. É o projeto do livro **Aprendendo Claude Code: desenvolvimento de software com IA na prática**, construído fase a fase, cada uma em uma branch.

Java 21, Spring Boot 3.3.5, Maven, H2 em memória e Flyway.

**Data fixa.** A API usa a data do sistema. Para reproduzir as saídas do livro, inicie com `./mvnw spring-boot:run -Dspring-boot.run.arguments=--biblioteca.data-fixa=2026-10-27`.

## Como rodar

```bash
./mvnw test                 # testes unitários (*Test) e de integração (*IT)
./mvnw spring-boot:run      # sobe a API em http://localhost:8080
```

No Windows, use `mvnw.cmd` no lugar de `./mvnw`.

## Endpoints

| Método | Caminho | O que faz |
|---|---|---|
| `GET` | `/livros`, `/livros/{id}` | Lista o acervo ou busca um livro, com as cópias disponíveis |
| `POST` | `/livros` | Cadastra um livro (`titulo`, `autor`, `isbn`, `copias`) |
| `GET` | `/leitores`, `/leitores/{id}` | Lista ou busca leitores |
| `POST` | `/leitores` | Cadastra um leitor (`nome`, `email`) |
| `GET` | `/emprestimos`, `/emprestimos/{id}` | Lista (com `?status=` ou `?atrasado=true`) ou busca empréstimos |
| `POST` | `/emprestimos` | Empresta um livro a um leitor (`livroId`, `leitorId`) |
| `PATCH` | `/emprestimos/{id}/renovar` | Soma 14 dias ao vencimento, no máximo 2 vezes, e só se não estiver atrasado |
| `PATCH` | `/emprestimos/{id}/devolver` | Registra a devolução e a multa cobrada, se houve atraso |
| `PATCH` | `/emprestimos/{id}/extravio` | Registra o extravio |
| `GET` | `/avisos` | Lista os avisos de devolução próxima, do mais recente ao mais antigo |

## Branches

| Branch | O que tem |
|---|---|
| `main` | A base: acervo, leitores e empréstimos, com a regra de atraso no service |
| `modulo-2` | + visão geral do projeto, conferida no código |
| `modulo-3` | + `CLAUDE.md` e o plano da feature de renovação e multa |
| `modulo-4` | + renovação e multa por atraso |
| `modulo-5` | + a regra de atraso e de multa movida para a entidade |
| `modulo-6-bug` | O bug plantado, para a caça ao defeito |
| `modulo-6` | + a correção, os testes de borda e a lição no `CLAUDE.md` |
| `modulo-7` | + skill, subagentes, hook e permissões do agente |
| `modulo-8` | + segurança, CI no GitHub Actions e entrega (tag `v1.0.0`) |
| `desafio-avisos` | Solução de referência do projeto completo (Capítulo 12): avisos de devolução |
| `desafio-iniciar` | Solução de referência do exercício extra do Capítulo 9: testes da regra de extravio |

## Configuração do agente (`.claude/`)

A partir da `modulo-7`, o projeto traz a configuração do Claude Code:

- `settings.json`: permissões (`allow` para testes e consultas ao Git, `deny` para `.env`, `rm -rf` e `git push`) e um hook que formata o código depois de cada edição.
- `skills/revisao-codigo/`: skill de revisão com checklist e um script que mede a cobertura de linhas (JaCoCo).
- `agents/revisor.md`: subagente somente leitura, que revisa sem editar.
- `agents/testador.md`: subagente que escreve e roda testes e nunca altera um teste existente.

## Projeto concluído

A versão `v1.0.0` é o resultado das oito fases do livro. As contagens de testes são da solução de referência:

| Fase | O que mudou | Testes |
|---|---|---|
| 0 · Preparar o ambiente | projeto clonado e rodando | 22 |
| 1 · Explorar e documentar | `docs/visao-geral.md` | 22 |
| 2 · Contexto e plano | `CLAUDE.md` e `docs/plano-renovacao-multa.md` | 22 |
| 3 · Implementar | renovação, multa, filtro de atrasados e migration V5 | 34 |
| 4 · Versionar e refatorar | `Emprestimo.estaAtrasado()` e `multa()` na entidade | 38 |
| 5 · Caçar o bug | comparação de datas corrigida, testes de borda | 41 |
| 6 · Configurar o agente | `.claude/` com hook, skill e subagentes | 41 |
| 7 · Proteger e entregar | restrições de comando, CI com testes e formatação, tag | 41 |

## Segurança

- `.claude/settings.json` nega leitura de `.env` e os comandos `rm -rf`, `git push`, `curl` e `wget`. Regras de comando restringem formas conhecidas de acesso, mas não isolam a rede: para isso, use sandbox ou um ambiente sem rede.
- O `CLAUDE.md` orienta o agente a tratar conteúdo externo como dado.
- O CI roda testes e `spotless:check` em todo push e pull request; o merge é sempre uma decisão humana.

## Avisos de devolução (desafio)

A branch `desafio-avisos` traz a solução de referência do desafio do capítulo 12: um job diário que registra um aviso para cada empréstimo aberto que vence em até dois dias. A especificação está em [docs/especificacao-avisos.md](docs/especificacao-avisos.md) e a decisão em [docs/adr/0001-avisos-de-devolucao.md](docs/adr/0001-avisos-de-devolucao.md).

Para ver o job rodar sem esperar as 8h, use o perfil de demonstração: `./mvnw spring-boot:run -Dspring-boot.run.profiles=demo`. Ele fixa a data em 2 de novembro de 2026 e dispara o job a cada 15 segundos; em seguida, consulte `GET /avisos`.
