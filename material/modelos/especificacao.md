# Modelo · Especificação leve

Capítulo 6. Uma especificação leve cabe numa página: o suficiente para que você e o agente concordem sobre o que significa "pronto" antes de escrever código. Salve em `docs/especificacao-<nome>.md`.

Um exemplo completo e real está no repositório, na branch `desafio-avisos`: `docs/especificacao-avisos.md`.

## Modelo

```markdown
# Especificação: <nome da funcionalidade>

## Contexto

<Por que isto existe, em duas ou três frases. Se houver uma decisão de projeto, aponte para o ADR:
[docs/adr/NNNN-titulo.md](adr/NNNN-titulo.md).>

## Critérios de aceite

1. Dado <situação inicial>, quando <ação ou evento>, então <resultado observável>.
2. Dado <…>, quando <…>, então <…>.
3. <Um critério de borda: o dia exato, a lista vazia, o limite.>
4. <Um critério sobre o que NÃO deve acontecer.>

## Fora do escopo

- <O que parece parte da tarefa, mas não é.>
- <Cada item evita um excesso do agente.>

## Como verificar

- `./mvnw test`: <quais testes cobrem a funcionalidade>.
- Casos de borda cobertos: <lista>.
- Verificação manual, se houver: <comando ou chamada com curl e a saída esperada>.
```

## Como preencher

- **Critérios.** Cada um deve poder virar um teste. Se você não consegue dizer como verificá-lo, ele ainda é uma intenção.
- **Bordas.** Inclua o dia exato do vencimento, a lista vazia, o limite de tentativas. É onde os defeitos moram.
- **Fora do escopo.** Escreva o que você não quer. O agente tende a completar o que parece natural.
- **Tamanho.** Uma correção pequena não precisa disto; use um pedido direto. A especificação vale para funcionalidades com várias camadas.
