# Modelo · Descrição de pull request

Capítulos 8 e 12. O agente rascunha bem uma descrição de PR, e você a ajusta. A última seção é a mais valiosa: diga ao revisor onde você tem dúvida. Um PR que esconde as dúvidas só adia a descoberta delas.

## Modelo

```markdown
## O que muda

<A mudança, em uma ou duas frases.>

## Por quê

<O plano ou a especificação, e os critérios de aceite cobertos: "docs/plano-….md; critérios 1 a 5".>

## Como foi verificado

- [ ] ./mvnw test
- [ ] <Critérios conferidos à mão, com curl, inclusive o caso antigo>
- [ ] <./mvnw spotless:check, se o CI roda>

## Pontos de atenção para a revisão

<Onde você tem dúvida. Por exemplo: "A regra de atraso está em dois lugares: Emprestimo.renovar e EmprestimoService.calcularAtraso.">
```

## Dicas

- Escreva a seção "Pontos de atenção" **por último**, depois de reler o diff como se fosse o revisor.
- Ela não pode ficar vazia. Se você realmente não tem dúvida nenhuma, releia o diff.
- Cada commit do PR deve passar nos testes sozinho, e a mensagem segue o Conventional Commits (`feat`, `fix`, `refactor`, `test`, `docs`).
- O `git push` e o merge são seus. O agente ajuda a redigir a descrição.
