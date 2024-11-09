package br.unitins.tp1.notebooks.dto;

import java.time.LocalDate;

public record ClienteRequestDTO(
    String cpf, 
    String telefone, 
    String endereco, 
    LocalDate dataNascimento, 
    UsuarioRequestDTO usuario
) {
}
