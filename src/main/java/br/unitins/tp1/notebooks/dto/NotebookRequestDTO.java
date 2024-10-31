package br.unitins.tp1.notebooks.dto;

public record NotebookRequestDTO(
        String modelo,
        Double preco,
        Integer garantia,
        Long fabricanteId,
        Long especificacaoId,
        String cor,
        Long categoriaId
) {
}
