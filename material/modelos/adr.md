# Modelo · ADR (registro de decisão de arquitetura)

Capítulo 6. Um ADR registra uma decisão difícil de reverter e o motivo dela, para que ninguém (nem o agente) a desfaça sem saber por que existe. Salve em `docs/adr/NNNN-titulo-curto.md`, com o número em sequência.

O exemplo real está no repositório, na branch `desafio-avisos`: `docs/adr/0001-avisos-de-devolucao.md`.

## Modelo

```markdown
# <N>. <Decisão, em uma frase>

Status: <proposta | aceita | substituída por NNNN>

## Contexto

<O problema e as restrições, em poucas linhas.
Diga o que você NÃO quer criar: "sem fila nem serviço externo".>

## Decisão

<O que será feito, de forma concreta: a tecnologia, onde ela vive, a garantia que ela dá.>

## Consequências

+ <O que fica melhor ou mais simples.>
+ <Outra vantagem, se houver.>
- <O que fica pior ou fica por fazer. Pelo menos uma.>
- <Outro custo, limite ou risco aceito.>
```

## Como preencher

- **Consequências negativas.** Um ADR sem nenhuma é um ADR incompleto: toda decisão tem custo. Se você não encontra um, procure de novo.
- **Coerência.** Releia o ADR depois de implementar. Ele precisa descrever o que o código faz, e não o que você planejava fazer.
- **Quando criar.** Se a decisão é difícil de reverter e tem um motivo que vale preservar. Para o resto, o `CLAUDE.md` ou o próprio código bastam.
- **Uma decisão por arquivo.** Se surgir outra decisão (por exemplo, notificar por e-mail), é outro ADR.
