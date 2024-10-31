package br.unitins.tp1.notebooks.dto;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;

 
    public UsuarioResponseDTO() {}
 
    public UsuarioResponseDTO(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public static UsuarioResponseDTO valueOf(UsuarioRequestDTO usuarioDTO) {
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setNome(usuarioDTO.getNome());
        response.setEmail(usuarioDTO.getEmail());
 
        return response;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
