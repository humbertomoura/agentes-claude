# Prompts do livro

Os pedidos abaixo aparecem no livro e no roteiro do repositório (`docs/roteiro/`). Estão na ordem das fases. Onde o roteiro descreve o passo sem dar o texto exato do pedido, o passo está indicado como instrução, e a redação é sua.

## Esqueletos para pedidos grandes

Capítulos 4 e 5. As quatro tags dão a um pedido grande um contexto, uma tarefa, limites e um critério de pronto.

```text
<contexto>…</contexto>
<tarefa>…</tarefa>
<restricoes>…</restricoes>
<pronto_quando>…</pronto_quando>
```

Preenchido, com o pedido da fase 2:

```text
<contexto>API REST de biblioteca, Spring Boot 3. Siga o @CLAUDE.md.</contexto>
<tarefa>Planeje a renovação de empréstimos (até 2 vezes, 14 dias cada) e a multa por atraso, com filtro GET /emprestimos?atrasado=true.</tarefa>
<restricoes>Não mude a listagem atual. Empréstimo antigo mantém a regra de 14 dias.</restricoes>
<pronto_quando>Plano com arquivos, migration, testes e riscos.</pronto_quando>
```

Para pedir a causa de um erro, o dado vem primeiro e a instrução por último, citando a tag pelo nome:

```text
<erro>…stack trace completo…</erro>
<contexto>Teste de integração que passou a falhar depois da migration V5.</contexto>
Explique a causa do erro em <erro> antes de propor qualquer correção.
```

## Fase 0 · Preparar o ambiente

```text
Explique em 3 frases o que este projeto faz.
```

## Fase 1 · Explorar e documentar

Em modo de plano, para explorar sem alterar arquivos.

```text
Descreva a arquitetura em camadas e liste os 5 arquivos mais importantes.
```

```text
Trace POST /emprestimos até o banco, citando arquivo e linha.
```

```text
Como um empréstimo é marcado como atrasado?
```

De volta ao modo manual:

```text
Escreva docs/visao-geral.md com arquitetura, fluxo e regra de atraso.
```

Depois, confira cada afirmação abrindo os arquivos citados.

## Fase 2 · Contexto e plano

Rode `/init`, leia o `CLAUDE.md` proposto e corrija. Depois, com o contexto limpo e em modo de plano, use o pedido com as quatro tags da seção "Esqueletos", acima. Para criticar o plano:

```text
O que acontece com os empréstimos já cadastrados? Algo muda na listagem atual?
```

## Fase 3 · Implementar renovação e multa

Com o contexto limpo, na branch de trabalho:

```text
Implemente @docs/plano-renovacao-multa.md, um passo por vez.
```

Os testes dos cinco critérios e o ciclo de executar e corrigir são instruções do roteiro, sem texto fixo. Diante de um erro, cole o stack trace e use o modelo "causa do erro", acima.

## Fase 4 · Versionar e refatorar

```text
Explique o diff e aponte o que está fora do escopo.
```

```text
Proponha a mensagem de commit para o diff preparado (git diff --cached), no padrão Conventional Commits, com escopo emprestimos. Mostre-a antes de commitar.
```

O teste que ainda não compila (o vermelho):

```text
Escreva EmprestimoTest: vencido, no prazo, devolvido e multa, chamando Emprestimo.estaAtrasado(hoje).
```

A refatoração, com a suíte existente verde:

```text
Mova a regra de atraso e de multa para Emprestimo, sem mudar comportamento.
```

A revisão, numa sessão nova:

```text
Revise modulo-3...HEAD por gravidade, com arquivo e linha.
```

## Fase 5 · Caçar o bug plantado

```text
Siga @docs/debug.md
```

Os casos de borda e a demonstração da causa raiz são instruções do roteiro: peça os casos de borda das regras de vencimento e de renovação sem escrever código, e prove cada hipótese com um teste antes de corrigir.

## Fase 6 · Configurar o agente

Para acionar a skill de revisão:

```text
/revisao-codigo
```

ou

```text
revise minhas mudanças
```

## Fase 7 · Proteger e entregar

A revisão final, antes do pull request:

```text
/revisao-codigo
```

O `git push`, o merge e a tag são seus: o `settings.json` do projeto nega o push ao agente de propósito.

## Exercício 5.3 · Medir o efeito do contexto

O pedido fixo dos três cenários:

```text
Descreva como um empréstimo é marcado como atrasado, citando arquivo e linha.
```

```text
Quais arquivos você leu?
```

O mapa da arquitetura, para medir o custo de explorar. Peça a resposta em texto, na própria conversa; sem isso, o agente pode gerar um arquivo ou uma página, e o `/context` mede outra coisa.

```text
Faça um mapa da arquitetura deste projeto, em texto, na própria resposta. Não crie arquivos nem artifacts.
```

## Fontes

Pedidos de pedido grande, causa de erro e mensagem de commit: capítulos 4, 5 e 8. Pedidos por fase: `docs/roteiro/fase-0.md` a `fase-7.md`, no repositório. Exercício 5.3: capítulo 5.
