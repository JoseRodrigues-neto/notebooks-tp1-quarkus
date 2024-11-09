package br.unitins.tp1.notebooks.dto;

import br.unitins.tp1.notebooks.modelo.Fabricante;

public record FabricanteResponseDTO(
    Long id,
    String nome,
    String paisOrigem
) {
    public static FabricanteResponseDTO valueOf(Fabricante fabricante) {
        return new FabricanteResponseDTO(
            fabricante.getId(),
            fabricante.getNome(),
            fabricante.getPaisOrigem()
        );
    }
}
