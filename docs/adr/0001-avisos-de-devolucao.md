# 1. Avisos de devolução com um agendador diário

Status: aceita

## Contexto

Leitores esquecem as datas de devolução. Precisamos avisar sobre os empréstimos que vencem em breve,
sem criar infraestrutura nova (fila, serviço externo).

## Decisão

Um job diário, com `@Scheduled` do Spring, consulta os empréstimos abertos com vencimento de hoje até
daqui a dois dias e registra um aviso no banco, na tabela `aviso`. O aviso é único por empréstimo e
por dia: uma restrição de unicidade no banco e uma checagem no service tornam o job idempotente. Os
avisos ficam disponíveis em `GET /avisos`.

## Consequências

+ Simples: não exige fila nem serviço externo.
+ Rodar o job duas vezes no mesmo dia é seguro, porque ele não cria avisos repetidos.
- O aviso pode chegar com até 24 horas de atraso em relação a uma renovação.
- Com mais de uma instância da aplicação, o job roda em cada uma. A restrição de unicidade evita
  avisos duplicados, mas o trabalho é repetido; evitar isso exigiria uma trava.
- Registrar o aviso não é notificar ninguém: não há envio de e-mail ou mensagem. Notificar é outra
  decisão, a registrar em outro ADR.
