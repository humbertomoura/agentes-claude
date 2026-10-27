---
name: revisao-codigo
description: Revisa o diff da branch atual contra a base da feature, que no livro é a modulo-3, usando o checklist da equipe. Use ao revisar PRs, antes de commitar ou quando pedirem "revise minhas mudanças".
---

# Revisão de código

1. Descubra a base da revisão (no livro, `modulo-3`; na dúvida, pergunte). Rode `git diff <base>...HEAD --stat` e depois `git diff <base>...HEAD`. Se houver mudanças ainda não commitadas, rode também `git diff` e `git diff --staged`.
2. Aplique cada item de [checklist.md](checklist.md) às mudanças, arquivo por arquivo.
3. Rode `.claude/skills/revisao-codigo/scripts/cobertura.sh` e informe a cobertura de linhas.
4. Liste os problemas por gravidade (**bloqueante**, **importante**, **sugestão**), sempre com arquivo e linha.
5. Termine com um veredito: aprovar, aprovar com ressalvas ou pedir mudanças.

Não edite arquivos durante a revisão. Se não houver problemas em um item do checklist, diga isso explicitamente.
