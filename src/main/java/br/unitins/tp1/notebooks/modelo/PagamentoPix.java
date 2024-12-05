package br.unitins.tp1.notebooks.modelo;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@PrimaryKeyJoinColumn(name = "id")
@Entity
public class PagamentoPix extends FormaPagamento {

      @Column(nullable = false)
    private String chavePix;  

    @Column(nullable = false)
    private LocalDate data;  

    public PagamentoPix() {}

    public PagamentoPix(double valor) {
        super(valor);
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String codigoTransacao) {
        this.chavePix = codigoTransacao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

}
