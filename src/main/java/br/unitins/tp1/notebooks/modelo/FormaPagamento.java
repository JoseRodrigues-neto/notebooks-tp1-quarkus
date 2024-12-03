package br.unitins.tp1.notebooks.modelo;

 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

 
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class FormaPagamento extends DefaultEntity {

    @Column(nullable = false)
    private double valor;


    public FormaPagamento(){}

    public FormaPagamento(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
