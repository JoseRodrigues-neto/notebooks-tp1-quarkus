package br.unitins.tp1.notebooks.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@PrimaryKeyJoinColumn(name = "id")
@Entity
public class PagamentoPix extends FormaPagamento {

      @Column(nullable = false)
    private String codigoTransacao;  // Identificador único gerado para a transação PIX

    @Column(nullable = false)
    private String qrCode;  

    public PagamentoPix() {}

    public PagamentoPix(double valor) {
        super(valor);
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public void setCodigoTransacao(String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
   
    
}
