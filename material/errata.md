# Errata · Aprendendo Claude Code

Correções da edição impressa e da edição Kindle. Esta página é atualizada a cada correção. Ainda não há correções registradas para a primeira edição.

## Como reportar um erro

Abra uma *issue* no repositório [agentes-claude](https://github.com/humbertomoura/agentes-claude/issues) com:

- a edição (impressa ou Kindle) e a data de compra, se souber;
- o capítulo ou apêndice, a seção e, se possível, a página;
- o texto como está e como deveria estar;
- para um comando que não funcionou: o sistema operacional, a versão do Java (`java -version`) e a versão do Claude Code (`claude --version`).

## Correções

| Edição | Onde | Está escrito | Deve ser | Registrada em |
|---|---|---|---|---|
| | | | | |

## O que não é erro

- **Números que mudam com a versão.** Os valores do `/context`, os nomes de menus e os atalhos dependem do modelo e da versão do Claude Code, e o próprio livro avisa disso. Compare os seus cenários entre si.
- **Contagens de testes.** São a saída da solução de referência (22, 34, 38, 41, 46 e 50 nas branches indicadas), medidas com o JDK 21. Com outra versão de Java, os testes podem falhar por razões que não são do código (veja o Apêndice A.0).
- **Hashes de commit.** Os hashes citados valem para as branches publicadas no repositório. Se você reescrever o histórico, eles mudam.
