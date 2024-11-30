package br.unitins.tp1.notebooks.dto;
import  br.unitins.tp1.notebooks.modelo.UsuarioBasico;

public record UsuarioBasicoResponseDTO(
    String nome,
    String email,
    String perfil
) {
    public static UsuarioBasicoResponseDTO from(UsuarioBasico usuario) {
        return new UsuarioBasicoResponseDTO(
    
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPerfil().getLabel()  // Retorna o label do enum Perfil (Basico, User, Adm)
        );
    }
}
