package br.unitins.tp1.notebooks.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Lote extends DefaultEntity {
   
    @ManyToOne 
    private Notebook notebook;
    private int quantidade;
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
