package br.unitins.tp1.notebooks.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class UsuarioBasico extends DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera um ID automaticamente no banco de dados
    private Long id;

    private String nome;
    private String email;

    @Enumerated(EnumType.STRING) // Garantir que o valor da enum será armazenado como uma string no banco
    private Perfil perfil;

    // Construtor padrão é necessário para o JPA
    public UsuarioBasico() {
    }

    // Construtor com nome e email
    public UsuarioBasico(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.perfil = Perfil.USER_BASIC; // Ou o perfil que você quiser atribuir
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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
