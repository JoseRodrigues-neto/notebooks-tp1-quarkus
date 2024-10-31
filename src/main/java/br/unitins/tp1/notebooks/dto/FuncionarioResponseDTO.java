package br.unitins.tp1.notebooks.dto;

public record FuncionarioResponseDTO(
    Long id,
    String nome,
    String email,
    String matricula,
    String cargo
) {}
