# Modelo · Revisão de código

Capítulo 8. Vale para a sua revisão e para a de um agente revisor numa sessão limpa. O checklist é o da skill `revisao-codigo` do repositório (`.claude/skills/revisao-codigo/checklist.md`).

## Checklist

```markdown
- **Correção**: faz o que o requisito pede? Casos de borda (nulos, listas vazias, datas de ontem, hoje e amanhã, o dia exato do vencimento)?
- **Testes**: há testes para o comportamento novo? Algum teste existente foi alterado ou relaxado?
- **Segurança**: entradas validadas? Algum segredo, token ou dado pessoal exposto?
- **Escopo**: mudou só o necessário? Há arquivos reformatados ou alterados sem relação com a tarefa?
- **Legibilidade**: nomes claros, sem duplicação, sem comentários óbvios?
- **Padrões**: segue o CLAUDE.md (controller só delega, DTOs, Clock injetado, dinheiro em BigDecimal, migrations novas)?
- **Dependências**: alguma dependência nova no pom.xml? Ela existe e é necessária?
```

Ajuste a linha de **Padrões** às regras do seu `CLAUDE.md`.

## Formato do resultado

Peça ao revisor que:

- liste os problemas **por gravidade**, com **arquivo e linha**;
- diga claramente **o que está bom**;
- explique o que mudar, **sem reescrever o código**.

Um revisor que só lê (ferramentas `Read`, `Grep` e `Glob`, sem `Edit` nem `Bash`) não altera nada por engano. O subagente `revisor` do repositório faz exatamente isto.

## Um comando para os testes

Antes de aceitar uma entrega, olhe o diff dos testes. Se algum teste existente perdeu linhas, foi alterado ou relaxado:

```bash
git diff <base>...HEAD -- src/test | grep '^-' | grep -v '^---'
```

Sem saída, nenhuma linha foi removida dos testes. Isso não prova que está tudo certo: prova só que nenhum teste existente foi enfraquecido.

## Depois da revisão

Cada achado vira uma de três coisas: uma correção, uma pergunta para quem escreveu, ou uma decisão de aceitar o risco, registrada. Não deixe achado sem destino.
