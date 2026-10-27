# Fase 5 · Caçar o bug plantado

**Objetivo.** Diagnosticar com método, corrigir sem trapacear e registrar a lição.

**Branch de partida:** `modulo-6-bug`. **Branch de chegada:** `modulo-6`.

## Passos

1. Reproduza: `git switch modulo-6-bug && ./mvnw test`.
2. Peça os casos de borda das regras de vencimento e de renovação, sem escrever código.
3. Investigue o histórico: "Siga @docs/debug.md", com `git log` e `git blame` na regra de atraso. O `git bisect run ./mvnw -q test` entre `modulo-5` e `modulo-6-bug` aponta o commit.
4. Demonstre a causa raiz: prove cada hipótese com um teste antes de corrigir.
5. Escreva primeiro os testes de borda que faltam e veja-os falhar.
6. Corrija sem alterar os testes existentes, registre uma lição durável no `CLAUDE.md` e faça os commits de teste, correção e documentação.

## Saída esperada

```text
$ ./mvnw test   # antes
Tests run: 38, Failures: 1     # emprestimoNoDiaDoVencimentoNaoTemAtraso

$ ./mvnw test   # com os testes de borda, antes da correção
Tests run: 41, Failures: 2, Errors: 1

$ ./mvnw test   # depois
Tests run: 41, Failures: 0

$ git log --oneline -3
docs: registra lição sobre comparação de datas no CLAUDE.md
fix(emprestimos): corrige comparação de datas no atraso e na renovação
test(emprestimos): casos de borda do dia do vencimento
```

## Pronto quando

os testes passam (41 na referência), nenhum teste existente foi alterado e a lição está no `CLAUDE.md`. Há mais de um defeito, e só um deles tem teste: renovar no dia do vencimento é recusado, e nada o pegava.

## Arquivos tocados

`~ Emprestimo`, `~ EmprestimoTest` e `~ CLAUDE.md`.

## Conceitos aplicados

depuração com método, `git bisect`, testes de borda, lição registrada.

## Se você se perder

`git switch modulo-6`.
