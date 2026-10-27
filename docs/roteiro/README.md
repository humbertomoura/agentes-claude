# Roteiro por fase

O projeto é construído em oito fases. Cada fase termina em uma branch de referência; quem se perder faz `git switch` para a branch de chegada da fase anterior e continua dali. As contagens de testes são da solução de referência, medidas com o JDK 21 em 26 de outubro de 2026.

| Fase | O que se faz | De → para | Testes |
|---|---|---|---|
| [0](fase-0.md) | Preparar o ambiente | `main` | 22 |
| [1](fase-1.md) | Explorar e documentar | `main` → `modulo-2` | 22 |
| [2](fase-2.md) | Contexto e plano | `modulo-2` → `modulo-3` | 22 |
| [3](fase-3.md) | Implementar renovação e multa | `modulo-3` → `modulo-4` | 34 |
| [4](fase-4.md) | Versionar e refatorar | `modulo-4` → `modulo-5` | 38 |
| [5](fase-5.md) | Caçar o bug plantado | `modulo-6-bug` → `modulo-6` | 38 com 1 falha, depois 41 |
| [6](fase-6.md) | Configurar o agente | `modulo-6` → `modulo-7` | 41 |
| [7](fase-7.md) | Proteger e entregar | `modulo-7` → `modulo-8` (`v1.0.0`) | 41 |

Depois da fase 7, o desafio do capítulo 12 está em `desafio-avisos` (50 testes) e o exercício extra do capítulo 9, em `desafio-iniciar` (46 testes). Os exercícios correspondentes estão em [../exercicios.md](../exercicios.md).

No Windows, use `mvnw.cmd` no lugar de `./mvnw`. Leia o diff de tudo o que o agente fizer antes de aceitar.
