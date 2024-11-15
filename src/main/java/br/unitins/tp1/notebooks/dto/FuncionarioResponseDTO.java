package br.unitins.tp1.notebooks.dto;

import  br.unitins.tp1.notebooks.modelo.Funcionario;

public record FuncionarioResponseDTO(
    Long id,
    String username,
    String nome,
    String email,
    String matricula,
    String cargo
 
) {
    public static FuncionarioResponseDTO valueOf(Funcionario funcionario) {
        return new FuncionarioResponseDTO(
            funcionario.getId(),
            funcionario.getUsuario().getUsername(),
            funcionario.getUsuario().getNome(),
            funcionario.getUsuario().getEmail(),
             funcionario.getMatricula(),
             funcionario.getCargo()
        );
    }
}
