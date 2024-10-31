package br.unitins.tp1.notebooks.dto;

public record NotebookResponseDTO(
        Long id,
        String modelo,
        Double preco,
        Integer garantia,
        String fabricante,
        String especificacao,
        String cor,
        String categoria
) {
}
