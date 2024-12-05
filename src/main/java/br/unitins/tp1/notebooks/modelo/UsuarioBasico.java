package br.unitins.tp1.notebooks.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "UsuarioBasico") 
public class UsuarioBasico extends DefaultEntity {

    private String nome;

    
    private String email;

    @Enumerated(EnumType.STRING)  
    private Perfil perfil;

 
    public UsuarioBasico() {
    }

  
    public UsuarioBasico(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.perfil = Perfil.USER_BASIC;  
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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
