package br.unitins.tp1.notebooks.modelo;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Lote extends DefaultEntity {
   
    @ManyToOne 
    @JoinColumn(name = "notebook_id", nullable = false) 
    private Notebook notebook;

     @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private LocalDate dataEntrada;
   

    public Lote (){}
    
    public Lote(Notebook notebook, int quantidade) {
        this.notebook = notebook; 
        this.quantidade = quantidade;
        this.dataEntrada = LocalDate.now();
    }


    public Notebook getNotebook() {
        return notebook;
    }

    public void setNotebook(Notebook notebook) {
        this.notebook = notebook;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    
}
