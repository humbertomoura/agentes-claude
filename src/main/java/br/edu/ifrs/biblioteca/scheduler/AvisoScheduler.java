package br.edu.ifrs.biblioteca.scheduler;

import br.edu.ifrs.biblioteca.service.AvisoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Dispara a geração diária de avisos. A lógica fica no service; aqui só há o horário, que pode ser
 * trocado por {@code biblioteca.avisos.cron}.
 */
@Component
public class AvisoScheduler {

  private static final Logger LOG = LoggerFactory.getLogger(AvisoScheduler.class);

  private final AvisoService service;

  public AvisoScheduler(AvisoService service) {
    this.service = service;
  }

  @Scheduled(cron = "${biblioteca.avisos.cron:0 0 8 * * *}", zone = "America/Sao_Paulo")
  public void gerarAvisosDiarios() {
    int criados = service.gerarAvisosDoDia();
    LOG.info("Avisos de devolução gerados: {}", criados);
  }
}
