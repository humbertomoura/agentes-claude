# Fase 3 · Implementar renovação e multa

**Objetivo.** Implementar a feature em várias camadas, executando e corrigindo em ciclos.

**Branch de partida:** `modulo-3`. **Branch de chegada:** `modulo-4`.

## Passos

1. Crie a branch de trabalho antes de qualquer edição: `git switch -c feat/renovacao-multa`.
2. Retome o plano com o contexto limpo: "Implemente @docs/plano-renovacao-multa.md, um passo por vez."
3. Revise camada a camada: migration `V5`, `Emprestimo`, repository, service, controller e DTO.
4. Peça os testes dos cinco critérios e rode `./mvnw test`.
5. Suba a aplicação e confira com `curl`. Diante de um erro, cole o stack trace e peça a causa antes da correção.
6. Ainda sem commit: o diff será analisado na fase 4.

## Saída esperada

```text
$ ./mvnw test
Tests run: 34, Failures: 0

$ curl -X POST localhost:8080/emprestimos -H 'Content-Type: application/json' \
    -d '{"livroId":3,"leitorId":2}'
$ curl -X PATCH localhost:8080/emprestimos/5/renovar
{"id":5,...,"status":"ABERTO","renovacoes":1,...}
```

## Pronto quando

os testes passam (34 na referência), os cinco critérios foram conferidos e renovar um empréstimo atrasado devolve 409.

## Arquivos tocados

`~ EmprestimoController`, `~ EmprestimoResponse`, `~ Emprestimo`, `~ EmprestimoRepository`, `~ EmprestimoService`, `+ V5__renovacao_e_multa.sql`, os dois arquivos de teste de empréstimo e o `README.md` (nove arquivos, 211 linhas a mais e 13 a menos).

## Conceitos aplicados

implementação em camadas, migrations com preenchimento, ciclo executar-e-corrigir.

## Se você se perder

`git switch modulo-4`.
