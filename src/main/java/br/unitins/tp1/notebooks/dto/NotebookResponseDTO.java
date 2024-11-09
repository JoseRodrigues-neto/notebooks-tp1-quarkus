package br.unitins.tp1.notebooks.dto;

import br.unitins.tp1.notebooks.modelo.Notebook;

public record NotebookResponseDTO(
        Long id,
        String modelo,
        Double preco,
        Integer garantia,
        FabricanteResponseDTO  fabricante,
        EspecificacaoResponseDTO especificacao,
        String cor,               // Usando String para representar o nome da cor
        CategoriaResponseDTO categoria
) {
    // Método estático valueOf que cria um NotebookResponseDTO a partir de um Notebook
    public static NotebookResponseDTO valueOf(Notebook notebook) {
        return new NotebookResponseDTO(
            notebook.getId(),
            notebook.getModelo(),
            notebook.getPreco(),
            notebook.getGarantia(),
            notebook.getFabricante() != null ? FabricanteResponseDTO.valueOf(notebook.getFabricante()) : null,
            notebook.getEspecificacao() != null ? EspecificacaoResponseDTO.valueOf(notebook.getEspecificacao()) : null,
            notebook.getCor() != null ? notebook.getCor().name() : null, // Obtendo o nome da cor como String
            notebook.getCategoria() != null ? CategoriaResponseDTO.valueOf(notebook.getCategoria()) : null
        );
    }
}
