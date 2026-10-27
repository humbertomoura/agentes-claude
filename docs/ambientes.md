# Ambientes: web, VS Code e IntelliJ

O agente é o mesmo nos três ambientes. O que está versionado no repositório (`CLAUDE.md`, `.claude/settings.json`, `.claude/skills/`, `.claude/agents/`) vale em todos. Confira o modo de permissão ativo antes de cada exercício: desde 14 de agosto de 2026, as sessões novas dos planos Pro, Max e Team começam no modo `auto`.

## Claude Code na web (claude.ai/code)

1. Acesse claude.ai/code, entre com a conta da Anthropic e instale o Claude GitHub App, com acesso ao repositório `agentes-claude`.
2. Crie um ambiente: nome, nível de rede (o padrão, **Trusted**, libera registros de pacotes comuns), variáveis em formato `.env` e, se quiser, um script de setup. A máquina já traz OpenJDK 21 com Maven e Gradle.
3. Escolha o repositório e a branch, defina o modo de permissão e descreva a tarefa.
4. Revise o diff e crie o pull request.

Na web não há `/clear`: abra uma sessão nova. Como a sessão clona o repositório do GitHub, faça push antes de pedir algo que dependa de commits locais.

## VS Code

1. Instale a extensão **Claude Code** (Cmd/Ctrl+Shift+X) e entre com a sua conta.
2. Abra a pasta raiz do repositório e clique em **✱ Claude Code** na barra de status.
3. Configurações que interessam (Cmd/Ctrl+,): `initialPermissionMode` (sugestão: `plan`), `preferredLocation` (`sidebar`) e `respectGitIgnore` (verdadeiro).
4. Option+K (Mac) ou Alt+K insere uma referência `@arquivo#linhas` com a seleção do editor.

## IntelliJ IDEA

1. Instale o CLI e confira com `claude --version`; depois instale o plugin **Claude Code [Beta]** e reinicie o IDE por completo.
2. Abra o projeto pela raiz; Cmd+Esc (Mac) ou Ctrl+Esc abre o Claude Code.
3. Em `/config`, defina o diff tool como `auto` para ver os diffs no IDE.
4. Se o Esc não interromper o Claude, desmarque *Move focus to the editor with Escape* em Settings, Tools, Terminal.

Com permissões de edição automática, o Claude pode alterar arquivos de configuração do IDE que o próprio IDE executa. Nos exercícios, prefira aprovar as edições manualmente.

O guia completo, com o "se algo der errado" de cada ambiente, está no Apêndice A do livro.
