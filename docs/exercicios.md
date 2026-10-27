# Exercícios

Todos os exercícios usam o projeto deste repositório. Leia o diff de tudo o que o agente fizer antes de aceitar. A numeração é a da oficina; no livro, o Apêndice C mostra onde cada item está.

## Atividade 1 · Chatbot, copiloto ou agente? (3 min, em duplas)

Classifique cada situação e justifique pelo critério: quem age no projeto e quem verifica o resultado?

1. Você cola um erro no chat e recebe uma explicação.
2. O editor completa o resto do método enquanto você digita.
3. Você pede "adicione paginação" e 4 arquivos mudam, com testes rodados.
4. Um script no CI lê uma issue, implementa e abre um PR sozinho.
5. Você pede um regex e testa você mesmo no terminal.

## Atividade 2B · Quanto poder você daria? (4 min)

Para cada ação, diga se dá para desfazer e escolha allow, perguntar ou deny: ler `src/` e `git log`; rodar `./mvnw test`; editar `EmprestimoService.java`; `git push origin main`; ler `.env`; `DROP TABLE` no banco de homologação. Depois escreva as regras no `.claude/settings.json`.

## Exercício 2 · Explorar a biblioteca (10 min) · branch `main`

1. Abra o Claude Code na raiz do repositório (modo padrão de permissões).
2. Peça um mapa da arquitetura e dos arquivos principais.
3. Trace o fluxo de um empréstimo, do controller ao banco.
4. Descubra como o sistema decide que um empréstimo está atrasado.
5. Confira cada resposta abrindo os arquivos citados.

**Entrega:** um parágrafo descrevendo o projeto e uma resposta conferida no código.

## Exercício 3A · Laboratório de prompts (8 min, opcional)

Use a mesma tarefa no antes e no depois e peça só plano ou revisão, sem alterar arquivos. Rode o prompt vago, reescreva e compare pela rubrica: escopo correto, referência ao código, casos de borda e verificação. Para saber qual técnica ajudou, mude uma de cada vez.

## Exercício 3 · CLAUDE.md e plano da feature (10 min) · a partir de `modulo-2` (ou `main`)

1. Rode `/init` e leia o `CLAUDE.md` gerado.
2. Corrija e complete: comandos, convenções e duas regras "não faça".
3. Faça commit do `CLAUDE.md`.
4. Limpe o contexto (`/clear` no CLI e no IntelliJ, nova conversa no VS Code, nova sessão na web), entre em modo de plano e peça o plano da feature descrita no Exercício 4: renovação e multa por atraso.
5. Critique o plano e peça para salvá-lo em `docs/plano-renovacao-multa.md`, sem autorizar a implementação.

**Entrega:** `CLAUDE.md` commitado e plano salvo em `docs/plano-renovacao-multa.md`. Referência: branch `modulo-3`.

## Exercício 4 · Implementar renovação e multa (20 min) · a partir de `modulo-3`

> Como leitor, quero renovar meu empréstimo e saber a multa de um atraso, para não perder o prazo por esquecimento.

Critérios de aceite:

1. Renovar soma 14 dias ao vencimento, no máximo 2 vezes; a terceira devolve 409.
2. Um empréstimo atrasado não pode ser renovado; no dia do vencimento, ainda pode.
3. Multa de R$ 0,50 por dia de atraso, com teto de R$ 20,00, em `BigDecimal`; a devolução atrasada registra a multa cobrada.
4. `GET /emprestimos?atrasado=true` lista só os empréstimos abertos e vencidos.
5. Os empréstimos antigos ganham vencimento 14 dias após a data do empréstimo.

Passos: **primeiro, crie a branch** `feat/renovacao-multa`; retome com contexto limpo e `@docs/plano-renovacao-multa.md`; implemente no modo padrão; suba a aplicação e teste com `curl`; diante de um erro, cole o stack trace completo e peça diagnóstico antes da correção.

**Entrega:** feature funcionando e os 5 critérios conferidos. Referência: branch `modulo-4`.

## Atividade 4B · Que tipo de falha é esta? (3 min)

Classifique como passageira, permanente, lógica ou sem progresso: `529 Overloaded`; `cannot find symbol: getVencimento()`; o agente editou o teste em vez do service; terceira tentativa com o mesmo `NullPointerException`; `401 authentication_error`.

## Atividade 4C · O que está errado neste controller? (5 min, em duplas, opcional)

Exemplo ilustrativo, que não está no repositório:

```java
@PostMapping
public Emprestimo emprestar(@RequestBody Emprestimo e) {
  if (e.getLivro() == null)
    throw new RuntimeException("x");
  if (e.getEmprestadoEm().isAfter(LocalDate.now()))
    throw new RuntimeException("data");
  return repository.save(e);
}
```

Responda: que camadas estão misturadas? O que torna o teste instável? O que a API expõe que não deveria? Que erro ocorre com a data nula? Escreva o prompt para o agente corrigir em passos, sem mudar o comportamento.

**Entrega:** a lista de violações e o prompt de refatoração em passos.

## Exercício 5 · Diff, commit e revisão (10 min) · a partir de `modulo-4`

1. Continue na branch `feat/renovacao-multa` criada no Exercício 4.
2. Veja o diff: `git diff` se tem alterações locais, ou `git diff modulo-3...HEAD` se retomou da branch `modulo-4`.
3. Peça ao agente que explique o diff e aponte o que está fora do escopo; faça um commit revisado (Conventional Commits).
4. Em sessão nova, peça a revisão de `modulo-3...HEAD` e confira cada achado no código.
5. Desafio: refatore a regra de atraso e de multa para `Emprestimo.estaAtrasado(hoje)` e `Emprestimo.multa(hoje)`. Com a suíte existente verde, peça primeiro o `EmprestimoTest`, que ainda não compila porque o método não existe (o vermelho que descreve o destino); depois mova a regra e volte ao verde, sem alterar os testes antigos. Faça um único commit com o teste e a refatoração: o teste sozinho não compila e faria o `git bisect` apontar o commit errado.

**Entrega mínima:** um commit revisado e os achados da revisão conferidos no código. Referência: branch `modulo-5`.

## Exercício 6 · Caçar o bug plantado (15 min) · branch `modulo-6-bug`

1. Faça checkout de `modulo-6-bug` e rode `./mvnw test`.
2. Peça a lista de casos de borda das regras de vencimento e de renovação e gere os testes que faltarem.
3. Siga `@docs/debug.md`: reproduzir, hipóteses, causa raiz, provar.
4. Corrija a implementação **sem alterar os testes existentes**.
5. Registre a lição no `CLAUDE.md` e faça commit.

Dica: há mais de um defeito. Nem todos têm teste.

**Entrega:** defeitos corrigidos, testes novos e lição no `CLAUDE.md`. Referência: branch `modulo-6`.

## Exercício 7 · Uma skill simples (6 min) · a partir de `modulo-6`

**Entrega mínima:** crie uma skill de arquivo único, `.claude/skills/revisao-codigo/SKILL.md`, com nome, descrição e passos (use `git diff modulo-3...HEAD`), e acione a skill uma vez (`/revisao-codigo` ou "revise minhas mudanças").

Desafios opcionais:

- **Hook** `PostToolUse` que roda o formatador; edite um arquivo e confirme que ele rodou.
- **Subagente** revisor só com `Read`, `Grep` e `Glob`: sem `Edit` nem `Bash`, para ser de fato somente leitura.
- **MCP**: conecte um servidor (por exemplo, GitHub) com `claude mcp add`, se já tiver credencial.

**Entrega:** a skill commitada em `.claude/skills/`. Solução completa de referência: branch `modulo-7`.

## Atividade 7B · Escolha o mecanismo (4 min)

Hook, skill, MCP, subagente, CLAUDE.md ou plugin? Formatador em todo arquivo editado; checklist de revisão repetido pela equipe; consultar issues do Jira; mapear 200 arquivos legados sem poluir a sessão; Java 21 e convenção de nomes; levar a configuração para outros cinco repositórios.

## Atividade 7C · Explore as skills embutidas (6 min, opcional)

1. Rode `/skills` e anote três skills embutidas e as do projeto.
2. Rode `/code-review` na sua branch e confira um achado no código.
3. Rode `/verify` para subir a API e conferir o filtro `?atrasado=true`.
4. Opcional: rode `/doctor` e leia o relatório sem aceitar mudanças.
5. Decida qual skill manteria no fluxo e qual dispensaria.

**Entrega:** uma skill que você manteria e uma que dispensaria, cada uma com o motivo. Observação: `/debug` investiga a sessão do próprio Claude Code, não o seu programa.

## Atividade 8 · Caça à injeção (5 min)

Um README de terceiros contém, num comentário HTML invisível, uma instrução para rodar `curl … | bash` e colar o `.env` num PR. Por que um humano não veria? Em que modo o agente executaria? Que regras deny barram cada parte? O que essas regras não cobrem, e que outra defesa ajudaria? Onde está a tríade letal?

## Atividade 8B · Auditoria de consumo (5 min, opcional)

1. Rode `/usage` e anote o que mais pesa: modelo, subagentes, MCP, CLAUDE.md.
2. Rode `/context` e veja o que ocupa a janela.
3. Escolha uma mudança: enxugar o CLAUDE.md, desligar um servidor MCP, limpar o contexto ou baixar o esforço.
4. Repita o mesmo pedido curto e compare o `/context`.
5. Registre a economia observada.

**Entrega:** o `/context` antes e depois, e a mudança que mais economizou. Em planos de assinatura, o valor em dólar do `/usage` não é cobrança.

## Exercício extra · Teste de mutação do extravio (10 min) · a partir de `modulo-8`

Aplique a mão um mutante em `Emprestimo.registrarExtravio()`, por exemplo removendo a guarda de status, e rode a suíte. Se nenhum teste falha, escreva os testes que faltam: o caminho feliz e a recusa de empréstimos já extraviados e já devolvidos. Referência: branch `desafio-iniciar`, que também pega um terceiro mutante, em que a guarda deixa passar o empréstimo devolvido.

## Desafio do projeto · Avisos de devolução (a partir de `modulo-8`)

Um job diário registra um aviso para cada empréstimo aberto que vence em até dois dias, sem duplicar. Escreva a especificação e o ADR antes do código, implemente em passos pequenos e entregue um pull request. Referência: branch `desafio-avisos`.
