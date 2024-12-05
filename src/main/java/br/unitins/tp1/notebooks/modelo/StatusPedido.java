package br.unitins.tp1.notebooks.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusPedido {

    PENDENTE(1, "Pedido pendente"),
    PAGO(2, "Pedido pago"),
    CANCELADO(3, "Pedido cancelado"),
    ENVIADO(4,"produto enviado"),
    ENTREGUE(5, "Pedido entregue");
    

    private final int codigo;
    private final String descricao;

    StatusPedido(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    
    public static StatusPedido valueOf(int codigo) {
        for (StatusPedido status : StatusPedido.values()) {
            if (status.getCodigo() == codigo) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido para StatusPedido: " + codigo);
    }

    @Override
    public String toString() {
        return descricao;
    }

  
      public static StatusPedido fromId(Long codigo) {
        if (codigo == null) return null;
        for (StatusPedido status : values()) {
            if (status.codigo == codigo.intValue()) { 
                return status;
            }
        }
        return null; 
    }
}
