package br.unitins.tp1.notebooks.dto;

import  br.unitins.tp1.notebooks.modelo.Usuario;
import  br.unitins.tp1.notebooks.modelo.Perfil;

public record UsuarioResponseDTO(
    Long id,
    String username,
    String nome,
    String email,
    Perfil perfil
) {
    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        return new UsuarioResponseDTO(
           usuario.getId(),
            usuario.getUsername(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPerfil()
        );
    }
}
