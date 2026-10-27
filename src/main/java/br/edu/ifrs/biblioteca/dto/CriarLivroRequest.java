package br.edu.ifrs.biblioteca.dto;

public record CriarLivroRequest(String titulo, String autor, String isbn, Integer copias) {}
