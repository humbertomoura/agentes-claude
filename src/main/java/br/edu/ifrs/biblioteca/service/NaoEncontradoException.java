package br.edu.ifrs.biblioteca.service;

public class NaoEncontradoException extends RuntimeException {

  public NaoEncontradoException(String recurso, Long id) {
    super(recurso + " " + id + " não encontrado");
  }
}
