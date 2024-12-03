package br.unitins.tp1.notebooks.modelo;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
 
@PrimaryKeyJoinColumn(name = "id")
@Entity
public class PagamentoCartao extends FormaPagamento {
    @Column(nullable = false)
    private  String tipo;  
    @Column(nullable = false)          
    private  String numeroCartao;
    @Column(nullable = false)   
    private  String nomeTitular;
    @Column(nullable = false)   
    private  LocalDate  validade;
    @Column(nullable = false)   
    private  Integer codigoSeguranca; 

    public PagamentoCartao(){}

    public PagamentoCartao(String tipo, String numeroCartao, String nomeTitular, LocalDate validade, Integer codigoSeguranca) {
        this.tipo = tipo;
        this.numeroCartao = numeroCartao;
        this.nomeTitular = nomeTitular;
        this.validade = validade;
        this.codigoSeguranca = codigoSeguranca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public LocalDate  getValidade() {
        return validade;
    }

    public void setValidade(LocalDate  validade) {
        this.validade = validade;
    }

    public Integer getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public void setCodigoSeguranca(Integer codigoSeguranca) {
        this.codigoSeguranca = codigoSeguranca;
    }
    
    
}
