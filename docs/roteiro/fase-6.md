# Fase 6 · Configurar o agente

**Objetivo.** Entrega mínima: criar e acionar uma skill de revisão. O restante é a solução completa de referência.

**Branch de partida:** `modulo-6`. **Branch de chegada:** `modulo-7`.

## Passos

1. Crie `.claude/skills/revisao-codigo/SKILL.md` com nome, descrição e passos.
2. Acione e confira: `/revisao-codigo`, ou "revise minhas mudanças", e veja a skill ser carregada.
3. Desafio A: um hook `PostToolUse` em `Edit|Write` com o formatador. Edite um arquivo e confirme o resultado.
4. Desafio B: um subagente revisor só com `Read`, `Grep` e `Glob`, sem `Bash`.
5. Referência: permissões `allow` para testes e `deny` para `.env`, `rm -rf` e `git push`.
6. Faça o commit: `chore(claude): skill de revisão, subagentes, hook e permissões`.

## Saída esperada

```text
$ ls .claude/skills/revisao-codigo
SKILL.md  checklist.md  scripts/

$ .claude/skills/revisao-codigo/scripts/cobertura.sh | tail -1
TOTAL                        82.2%
```

## Pronto quando

a skill foi criada e acionada e o hook ou o subagente foi verificado na prática. A cobertura de linhas da referência é de 82,2% (JaCoCo).

## Arquivos tocados

`+ .claude/` (settings, dois subagentes e a skill com checklist e script), `~ pom.xml` (JaCoCo) e `~ README.md`.

## Conceitos aplicados

skills, hooks, subagentes e permissões.

## Se você se perder

`git switch modulo-7`.
