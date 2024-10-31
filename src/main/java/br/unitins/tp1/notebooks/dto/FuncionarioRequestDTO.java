package br.unitins.tp1.notebooks.dto;

public record FuncionarioRequestDTO(
    String matricula,
    String cargo,
    UsuarioRequestDTO usuario
) {}
