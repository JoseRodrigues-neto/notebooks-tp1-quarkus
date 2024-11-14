package br.unitins.tp1.notebooks.dto;

import java.time.LocalDate;

public record ClienteRequestDTO(
    String cpf, 
    String username,
    String nome,
    String email,
    String senha,
    String telefone, 
    String endereco, 
    LocalDate dataNascimento
) {}
