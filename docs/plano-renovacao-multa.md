# Plano: renovação e multa por atraso

## Contexto

Hoje todo empréstimo vence 14 dias depois de feito, e não há como renovar nem cobrar multa. Esta feature acrescenta as duas coisas, sem mudar o que a listagem atual devolve.

## Critérios de aceite

1. **Renovação.** `PATCH /emprestimos/{id}/renovar` soma 14 dias ao vencimento e conta uma renovação. O limite é de 2 renovações; a terceira devolve 409.
2. **Bloqueio por atraso.** Um empréstimo atrasado não pode ser renovado (409). No dia do vencimento, ainda pode.
3. **Multa.** R$ 0,50 por dia de atraso, com teto de R$ 20,00, calculada em `BigDecimal` e exposta no campo `multa`. A devolução atrasada registra a multa cobrada.
4. **Filtro.** `GET /emprestimos?atrasado=true` lista só os empréstimos abertos e vencidos.
5. **Empréstimos antigos.** Os que já existem ganham vencimento 14 dias após a data do empréstimo, por migration com preenchimento dos dados.

## Mudanças previstas

- Migration `V5`: colunas `vencimento` (preenchida a partir de `emprestado_em`), `renovacoes` (padrão 0) e `multa_cobrada` (padrão 0).
- `Emprestimo`: campos novos e o método `renovar(hoje)`.
- `EmprestimoService`: `renovar`, cálculo da multa e o filtro de atrasados.
- `EmprestimoResponse`: campos `vencimento`, `renovacoes` e `multa`.
- Testes novos para cada critério, inclusive as bordas (renovar no dia do vencimento, multa no teto).

## Decisão em aberto

Nenhuma: o prazo de renovação (14 dias) e o teto da multa (R$ 20,00) estão fixados nos critérios acima.
