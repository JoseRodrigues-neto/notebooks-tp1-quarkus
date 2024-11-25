package br.unitins.tp1.notebooks.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemPedido extends DefaultEntity{

   @ManyToOne
   @JoinColumn(name = "notebook_id", nullable = false) 
    private Notebook notebook;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false) 
    private double preco;
   
    public ItemPedido(){}

    public ItemPedido(Notebook notebook, int quantidade,double preco) {
    
        this.notebook = notebook;
        this.quantidade = quantidade;
        this.preco = preco;
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


    public double getPreco() {
        return preco;
    }


    public void setPreco(double preco) {
        this.preco = preco;
    }


    }

