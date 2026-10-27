# Checklist de revisão

- **Correção**: faz o que o requisito pede? Casos de borda (nulos, listas vazias, datas de ontem, hoje e amanhã, o dia exato do vencimento)?
- **Testes**: há testes para o comportamento novo? Algum teste existente foi alterado ou relaxado?
- **Segurança**: entradas validadas? Algum segredo, token ou dado pessoal exposto?
- **Escopo**: mudou só o necessário? Há arquivos reformatados ou alterados sem relação com a tarefa?
- **Legibilidade**: nomes claros, sem duplicação, sem comentários óbvios?
- **Padrões**: segue o CLAUDE.md (controller só delega, DTOs, Clock injetado, dinheiro em BigDecimal, migrations novas)?
- **Dependências**: alguma dependência nova no pom.xml? Ela existe e é necessária?
