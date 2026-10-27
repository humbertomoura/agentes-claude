package br.edu.ifrs.biblioteca.config;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Relógio injetável: permite fixar a data "de hoje" nos testes. Para reproduzir os exemplos do
 * livro, defina {@code biblioteca.data-fixa} (por exemplo, {@code 2026-10-27}); sem ela, vale a
 * data do sistema.
 */
@Configuration
public class ClockConfig {

  private static final ZoneId ZONA = ZoneId.of("America/Sao_Paulo");

  @Bean
  public Clock clock(@Value("${biblioteca.data-fixa:}") String dataFixa) {
    if (dataFixa.isBlank()) {
      return Clock.system(ZONA);
    }
    return Clock.fixed(LocalDate.parse(dataFixa).atStartOfDay(ZONA).toInstant(), ZONA);
  }
}
