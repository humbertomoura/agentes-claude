# Fase 2 · Contexto e plano

**Objetivo.** Dar ao agente a memória do projeto e um plano revisado da feature.

**Branch de partida:** `modulo-2`. **Branch de chegada:** `modulo-3`.

## Passos

1. Rode `/init` e leia o `CLAUDE.md` proposto.
2. Corrija e complete: comandos, camadas, `Clock` injetado, dinheiro em `BigDecimal`, convenções e duas regras "não faça".
3. Faça o commit do `CLAUDE.md`.
4. Limpe o contexto, entre em modo de plano e peça o plano da feature descrita na fase 3, com `<contexto>`, `<tarefa>`, `<restricoes>` e `<pronto_quando>`.
5. Critique o plano: "O que acontece com os empréstimos já cadastrados? Algo muda na listagem atual?"
6. Salve o plano aprovado em `docs/plano-renovacao-multa.md` e faça o commit, sem implementar.

## Saída esperada

```text
$ git log --oneline -1
docs: adiciona CLAUDE.md e plano da feature de renovação e multa
```

Seguindo os passos, a história tem dois commits; a branch `modulo-3` reúne tudo em um só.

## Pronto quando

o `CLAUDE.md` está revisado e enxuto, o plano não muda a listagem atual e os empréstimos antigos ganham vencimento 14 dias após a data do empréstimo.

## Arquivos tocados

`+ CLAUDE.md`, `+ docs/plano-renovacao-multa.md`

## Conceitos aplicados

memória de projeto, engenharia de contexto, planejamento antes de codar.

## Se você se perder

`git switch modulo-3`.
