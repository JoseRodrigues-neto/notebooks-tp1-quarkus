package br.unitins.tp1.notebooks.dto;

import br.unitins.tp1.notebooks.modelo.Cliente;
import java.time.LocalDate;

public record ClienteResponseDTO(
    Long id, 
    String cpf, 
    String nome, 
    String email,
    String telefone, 
    String endereco, 
    LocalDate dataNascimento
) {

    public static ClienteResponseDTO valueOf(Cliente cliente) {
        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getCpf(),
            cliente.getUsuario().getNome(),
            cliente.getUsuario().getEmail(),
            cliente.getTelefone(),
            cliente.getEndereco(),
            cliente.getDataNascimento()
        );
    }
}
