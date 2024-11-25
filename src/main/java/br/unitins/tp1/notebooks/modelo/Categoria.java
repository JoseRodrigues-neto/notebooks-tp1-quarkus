package br.unitins.tp1.notebooks.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Categoria extends DefaultEntity{

     @Column(length = 60, nullable = false)
    private String nome;
    @Column(length = 200, nullable = false)
    private String descricao;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
