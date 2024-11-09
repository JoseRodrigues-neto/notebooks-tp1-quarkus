package br.unitins.tp1.notebooks.dto;

import br.unitins.tp1.notebooks.modelo.Usuario;

public record UsuarioResponseDTO(
     Long id,
     String nome,
     String email
) {

    public static UsuarioResponseDTO valueOf(Usuario usuario) {
       
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail()
        );
    }
}
