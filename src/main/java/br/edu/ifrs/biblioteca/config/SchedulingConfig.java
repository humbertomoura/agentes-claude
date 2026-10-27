package br.edu.ifrs.biblioteca.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/** Liga o suporte a tarefas agendadas (@Scheduled). */
@Configuration
@EnableScheduling
public class SchedulingConfig {}
