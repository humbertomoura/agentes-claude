# Guia de debugging

Siga estes passos, nesta ordem, ao investigar uma falha. Não pule para a correção.

1. **Snapshot**: confirme que não há alterações pendentes (`git status`). Se houver, faça commit ou stash antes.
2. **Reproduzir**: encontre um comando que mostre a falha sempre (um teste, um `curl`). Se não existir, escreva um teste que falha.
3. **Hipóteses**: leia o código envolvido e o histórico (`git log`, `git blame`). Liste as causas possíveis antes de escolher uma.
4. **Causa raiz**: prove a hipótese (teste, log ou execução). Aponte arquivo, linha e, se possível, o commit que introduziu o problema.
5. **Corrigir e provar**: faça a menor correção possível. O teste que falhava deve passar e nenhum outro pode quebrar. **Nunca altere um teste para fazê-lo passar** sem confirmar com o desenvolvedor.
6. **Registrar**: adicione ao `CLAUDE.md` uma lição curta que evite o mesmo erro no futuro.

Ao final, apresente: causa raiz, correção, evidência (saída dos testes) e a lição registrada.
