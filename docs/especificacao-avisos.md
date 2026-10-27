# Especificação: avisos de devolução

## Contexto

Quem pega um livro emprestado precisa ser lembrado de devolvê-lo antes do vencimento. Esta
funcionalidade registra, uma vez por dia, um aviso para cada empréstimo que vence em breve. A decisão
de projeto está em [docs/adr/0001-avisos-de-devolucao.md](adr/0001-avisos-de-devolucao.md).

## Critérios de aceite

1. Dado um empréstimo aberto com vencimento hoje, amanhã ou daqui a dois dias, quando o job do dia
   roda, então existe um aviso desse empréstimo com a data de hoje e com 0, 1 ou 2 dias para vencer.
2. Dado um empréstimo com vencimento anterior a hoje ou daqui a três dias ou mais, então não há
   aviso.
3. Dado um empréstimo devolvido ou extraviado, então não há aviso.
4. Dado que o job já rodou hoje, quando ele roda de novo, então nenhum aviso novo é criado.
5. `GET /avisos` devolve os avisos, do mais recente ao mais antigo, com `id`, `emprestimoId`,
   `dataAviso` e `diasParaVencer`.
6. Um empréstimo renovado vale pelo novo vencimento: se ele sai da janela de dois dias, deixa de
   receber avisos.
7. A data de hoje vem do `Clock` injetado. O agendador só dispara o service, às 8h de São Paulo.

## Fora do escopo

- Enviar e-mail ou mensagem.
- Avisar sobre empréstimos já atrasados.
- Configurar a antecedência (fixa em dois dias).
- Coordenar várias instâncias da aplicação.

## Como verificar

- `./mvnw test`: `AvisoServiceTest` (unitário, com data fixa) e `AvisoControllerIT` (integração).
- Casos de borda cobertos: vencimento hoje, daqui a dois dias, daqui a três dias, ontem, empréstimo
  devolvido, empréstimo extraviado, execução repetida e empréstimo renovado.
