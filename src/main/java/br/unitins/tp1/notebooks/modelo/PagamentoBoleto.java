package br.unitins.tp1.notebooks.modelo;

import java.time.LocalDate;

public class PagamentoBoleto extends FormaPagamento {
    
    Integer codigoBarras;
    LocalDate dataVencimento;

    public PagamentoBoleto(double valor) {
        super(valor);
    }

    public Integer getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(Integer codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
   
    
}
