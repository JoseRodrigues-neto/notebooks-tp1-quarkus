package br.unitins.tp1.notebooks.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Cor {

    PRETO(1, "Preto"),
    BRANCO(2, "Branco"),
    CINZA(3, "Cinza"),
    AZUL(4, "Azul"),
    VERMELHO(5, "Vermelho"),
    PRATA(6, "Prata");

    private final Integer id;
    private final String label;

 
    Cor(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

  
    public static Cor valueOf(Integer id) {
        if (id == null)
            return null;
        for (Cor cor : Cor.values()) {
            if (cor.getId().equals(id))
                return cor;
        }
        throw new IllegalArgumentException("Id inválido");
    }

}
