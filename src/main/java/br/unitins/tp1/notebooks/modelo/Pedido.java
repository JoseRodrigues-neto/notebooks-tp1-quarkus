package br.unitins.tp1.notebooks.modelo;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido extends DefaultEntity {
    
    @ManyToOne
      @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens; 
    
   
    private LocalDate dataPedido;
    
    private Double valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    public Pedido(){}

    public Pedido(Cliente cliente, List<ItemPedido> itens) {
        this.cliente = cliente;
        this.itens = itens;
        this.dataPedido = LocalDate.now();
        this.valorTotal = valorTotal;
        this.status = StatusPedido.PENDENTE;
    }


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Double getValorTotal () {
        return valorTotal;
    }

    public void setValorTotal (Double total) {
        this.valorTotal = total;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

     
    
}
