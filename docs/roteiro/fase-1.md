# Fase 1 · Explorar e documentar

**Objetivo.** Entender o código com o agente e registrar o que foi conferido.

**Branch de partida:** `main`. **Branch de chegada:** `modulo-2`.

## Passos

1. Entre em modo de plano para explorar com segurança.
2. Peça um mapa: "Descreva a arquitetura em camadas e liste os 5 arquivos mais importantes."
3. Siga um fluxo: "Trace POST /emprestimos até o banco, citando arquivo e linha."
4. Descubra a regra: "Como um empréstimo é marcado como atrasado?"
5. Volte ao modo manual e peça: "Escreva docs/visao-geral.md com arquitetura, fluxo e regra de atraso."
6. Confira cada afirmação abrindo os arquivos citados e faça o commit.

## Saída esperada

```text
$ git log --oneline -1
docs: visão geral do projeto conferida no código   # mensagem da referência; a sua pode diferir
```

## Pronto quando

cada afirmação foi conferida no arquivo citado, `docs/visao-geral.md` está commitado e os testes continuam passando.

## Arquivos tocados

`+ docs/visao-geral.md`

## Conceitos aplicados

exploração em modo de plano, verificação das respostas do agente.

## Se você se perder

`git switch modulo-2`.
