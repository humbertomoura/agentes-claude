# Modelo · Plano de uma funcionalidade

Capítulos 5 e 6. O plano é o resultado do modo de plano: você o lê, critica e salva antes de pedir qualquer implementação. Salve em `docs/plano-<nome>.md`.

O exemplo real está no repositório: `docs/plano-renovacao-multa.md`.

## Modelo

```markdown
# Plano: <nome da funcionalidade>

## Contexto

<Como é hoje e o que esta funcionalidade acrescenta, em poucas linhas.
Diga o que NÃO muda: "sem mudar o que a listagem atual devolve".>

## Critérios de aceite

1. **<Nome>.** <Comportamento observável, com os números: limites, prazos, valores.>
2. **<Nome>.** <…>
3. **Dados antigos.** <O que acontece com o que já existe no banco.>

## Mudanças previstas

- Migration `V<N>`: <colunas, valores padrão e como os dados antigos são preenchidos>.
- `<Entidade>`: <campos novos e métodos>.
- `<Service>`: <regras novas>.
- `<Response>`: <campos expostos>.
- Testes novos para cada critério, inclusive as bordas (<liste as bordas>).

## Decisão em aberto

<Pergunta que o plano não responde, ou "Nenhuma", dizendo por quê.>
```

## Antes de aprovar o plano

Faça as perguntas que um plano costuma esconder:

- O que acontece com os dados que já existem?
- Algo muda no comportamento atual, na listagem ou na API?
- Cada critério tem um teste, e cada borda também?
- O plano toca arquivos que a tarefa não pede?

Só depois de aprovado e salvo, com um commit, peça a implementação, um passo por vez.
