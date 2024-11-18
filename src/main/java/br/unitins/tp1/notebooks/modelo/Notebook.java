package br.unitins.tp1.notebooks.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

@Entity
public class Notebook extends DefaultEntity {

    private String modelo;
    private Double preco;
    private Integer garantia;

    @ManyToOne
    private Fabricante fabricante; 

    @ManyToOne
    private Especificacao especificacao;

    @Enumerated(EnumType.STRING)
    private Cor cor;

    @ManyToOne
    private Categoria categoria;

    public Notebook() {}

    public Notebook(String modelo, Double preco, Integer garantia, Fabricante fabricante,
                    Especificacao especificacao, Cor cor, Categoria categoria) {
        this.modelo = modelo;
        this.preco = preco;
        this.garantia = garantia;
        this.fabricante = fabricante;
        this.especificacao = especificacao;
        this.cor = cor;
        this.categoria = categoria;
    }

   
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getGarantia() {
        return garantia;
    }

    public void setGarantia(Integer garantia) {
        this.garantia = garantia;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public Especificacao getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(Especificacao especificacao) {
        this.especificacao = especificacao;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
