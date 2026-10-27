# Fase 4 · Versionar e refatorar

**Objetivo.** Transformar o trabalho em commits limpos, refatorar com segurança e revisar.

**Branch de partida:** `modulo-4`. **Branch de chegada:** `modulo-5`.

## Passos

1. Veja o que mudou: `git diff` se há alterações locais, ou `git diff modulo-3...HEAD` se retomou da referência.
2. Peça ao agente: "Explique o diff e aponte o que está fora do escopo."
3. Faça o commit da feature: `feat(emprestimos): adiciona renovação e multa por atraso`.
4. Descreva o destino: "Escreva EmprestimoTest: vencido, no prazo, devolvido e multa, chamando Emprestimo.estaAtrasado(hoje)." O teste ainda não compila, porque o método não existe: é o vermelho.
5. Desafio: com a suíte existente verde, "Mova a regra de atraso e de multa para Emprestimo, sem mudar comportamento." A suíte inteira deve voltar ao verde, sem alterar os testes antigos.
6. Faça **um único commit** com o teste e a refatoração: o teste sozinho não compila, e um commit vermelho faria o `git bisect` apontar o commit errado.
7. Revise em uma sessão nova: "Revise modulo-3...HEAD por gravidade, com arquivo e linha."

## Saída esperada

```text
$ git log --oneline -2
refactor(emprestimos): move regra de atraso e multa para Emprestimo
feat(emprestimos): adiciona renovação e multa por atraso

$ ./mvnw test
Tests run: 38, Failures: 0
```

## Pronto quando

há um commit revisado no padrão Conventional Commits, os achados da revisão foram conferidos no código e o `EmprestimoTest` passou de vermelho a verde (38 na referência; acrescentar o caso do dia do vencimento aumenta o total).

## Arquivos tocados

`~ Emprestimo`, `~ EmprestimoService` e `+ EmprestimoTest` (três arquivos).

## Conceitos aplicados

Conventional Commits, refatoração com rede de testes, revisão em sessão limpa.

## Se você se perder

`git switch modulo-5`.
