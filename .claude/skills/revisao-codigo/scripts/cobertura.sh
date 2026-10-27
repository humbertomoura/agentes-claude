#!/usr/bin/env bash
# Roda os testes e imprime a cobertura de linhas por classe (JaCoCo).
set -euo pipefail
cd "$(git rev-parse --show-toplevel)"
./mvnw -q test > /dev/null 2>&1 || { echo "Testes falharam: rode ./mvnw test para ver os detalhes" >&2; exit 1; }
csv=target/site/jacoco/jacoco.csv
if [[ ! -f "$csv" ]]; then
  echo "Relatório JaCoCo não encontrado em $csv" >&2
  exit 1
fi
awk -F, 'NR>1 { miss+=$8; cov+=$9; printf "%-28s %5.1f%%\n", $3, ($8+$9) ? 100*$9/($8+$9) : 0 }
         END  { printf "%-28s %5.1f%%\n", "TOTAL", 100*cov/(miss+cov) }' "$csv"
