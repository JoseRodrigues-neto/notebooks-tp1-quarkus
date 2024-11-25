package br.unitins.tp1.notebooks.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FormaPagamento {

    PIX(1, "Pix"),
    CARTAO_CREDITO(2, "Cartão de Crédito"),
    CARTAO_DEBITO(3, "Cartão de Débito"),
    BOLETO(4, "Boleto");

    private final Integer id;
    private final String label;

    // Construtor
    FormaPagamento(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    // Método estático para converter id em FormaPagamento
    public static FormaPagamento valueOf(Integer id) {
        if (id == null)
            return null;
        for (FormaPagamento forma : FormaPagamento.values()) {
            if (forma.getId().equals(id))
                return forma;
        }
        throw new IllegalArgumentException("Id inválido");
    }

    // Método estático para converter String para FormaPagamento
    public static FormaPagamento fromString(String label) {
        if (label == null)
            return null;
        for (FormaPagamento forma : FormaPagamento.values()) {
            if (forma.getLabel().equalsIgnoreCase(label)) {
                return forma;
            }
        }
        throw new IllegalArgumentException("Forma de pagamento inválida");
    }
}
