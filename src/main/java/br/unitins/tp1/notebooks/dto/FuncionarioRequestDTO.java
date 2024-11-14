package br.unitins.tp1.notebooks.dto;


public record FuncionarioRequestDTO(

    String username,
    String nome,
    String email,
    String senha,
    String matricula,
    String cargo
) {}
