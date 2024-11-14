package br.unitins.tp1.notebooks.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
 
@Entity
public class Usuario extends DefaultEntity {
    
    
    @Column(nullable = false, unique = true)
    private String username;

    private String nome;  
    private String email;  
    private String senha;  
    private Perfil perfil;
 
    public Usuario() {
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public Perfil getPerfil() {
        return perfil;
    }


    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    
}
