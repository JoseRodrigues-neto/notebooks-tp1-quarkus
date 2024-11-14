package br.unitins.tp1.notebooks.modelo;

import jakarta.persistence.Entity;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
 

@Entity
public class Funcionario extends DefaultEntity {
 
    @Column(nullable = false, unique = true)
    private String matricula;

    private String cargo;

    @OneToOne
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
