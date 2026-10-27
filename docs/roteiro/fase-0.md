# Fase 0 · Preparar o ambiente

**Objetivo.** Ter o projeto rodando e o agente aberto na raiz do repositório.

**Branch de partida:** `main`. **Branch de chegada:** `main`.

## Passos

1. Escolha o ambiente (web, VS Code ou IntelliJ) e traga o repositório `agentes-claude`.
2. Abra pela raiz e rode `./mvnw test`.
3. Abra o agente e peça: "Explique em 3 frases o que este projeto faz."
4. Suba a API com `./mvnw spring-boot:run` e consulte `curl localhost:8080/livros`.

## Saída esperada

```text
$ ./mvnw test
Tests run: 22, Failures: 0
BUILD SUCCESS

$ curl localhost:8080/livros
[{"id":1,"titulo":"Estruturas de Dados em Java",...,"copias":2,"disponiveis":1},...]
```

## Pronto quando

os 22 testes passam, a API responde e o agente está aberto na raiz.

## Arquivos tocados

nenhum.

## Conceitos aplicados

agente, ferramentas, loop, janela de contexto.

## Se você se perder

`git switch main`.
