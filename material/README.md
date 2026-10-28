# Material complementar · Aprendendo Claude Code

Arquivos para copiar e usar enquanto você segue o livro *Aprendendo Claude Code: desenvolvimento de software com IA na prática*, de Humberto Jorge de Moura Costa. Obra independente, sem vínculo com a Anthropic.

Versão de 28 de outubro de 2026. O texto dos prompts e dos modelos vem do livro e do repositório [agentes-claude](https://github.com/humbertomoura/agentes-claude); nada foi reescrito para este material.

## O que tem aqui

| Arquivo | Para que serve | Onde o livro trata |
|---|---|---|
| [prompts.md](prompts.md) | Os pedidos do livro, prontos para copiar, na ordem das fases | Capítulos 3 a 12 e Apêndice B |
| [modelos/especificacao.md](modelos/especificacao.md) | Especificação leve, com critérios de aceite | Capítulo 6 |
| [modelos/plano.md](modelos/plano.md) | Plano de uma funcionalidade, para salvar em `docs/` | Capítulos 5 e 6 |
| [modelos/adr.md](modelos/adr.md) | Registro de decisão de arquitetura | Capítulo 6 |
| [modelos/revisao.md](modelos/revisao.md) | Checklist e formato de uma revisão | Capítulo 8 |
| [modelos/descricao-pr.md](modelos/descricao-pr.md) | Descrição de pull request com os pontos de dúvida | Capítulos 8 e 12 |
| [modelos/retrospectiva.md](modelos/retrospectiva.md) | Retrospectiva de uma página | Capítulo 12 |
| [errata.md](errata.md) | Correções da edição impressa e como reportar | |

## Como usar

1. Copie o bloco de que precisa. Cada prompt está num bloco de código, sem texto em volta.
2. Troque o que estiver entre `<colchetes>` e o que o próprio modelo marca como exemplo.
3. Confira o resultado como o livro ensina. Um prompt pronto não dispensa a revisão.

## Antes de começar

- Os prompts assumem o projeto `agentes-claude` (a API de biblioteca em Java 21 e Spring Boot) e as branches de referência. Quem se perder faz `git switch` para a branch de chegada da fase anterior.
- Os comandos são de Bash. No Windows, use o Git Bash ou o WSL, como explica o Apêndice A.
- O texto de um prompt funciona em outros projetos, mas os nomes de arquivos, branches e classes são do projeto da biblioteca.
