package br.unitins.tp1.notebooks.dto;

import br.unitins.tp1.notebooks.modelo.Notebook;

public record NotebookResponseDTO(
        Long id,
        String modelo,
        Double preco,
        Integer garantia,
        FabricanteResponseDTO  fabricante,
        EspecificacaoResponseDTO especificacao,
        String cor,               
        CategoriaResponseDTO categoria,
        String nomeImagem
) {
 
    public static NotebookResponseDTO valueOf(Notebook notebook) {
        return new NotebookResponseDTO(
            notebook.getId(),
            notebook.getModelo(),
            notebook.getPreco(),
            notebook.getGarantia(),
            notebook.getFabricante() != null ? FabricanteResponseDTO.valueOf(notebook.getFabricante()) : null,
            notebook.getEspecificacao() != null ? EspecificacaoResponseDTO.valueOf(notebook.getEspecificacao()) : null,
            notebook.getCor() != null ? notebook.getCor().name() : null,  
            notebook.getCategoria() != null ? CategoriaResponseDTO.valueOf(notebook.getCategoria()) : null,
            notebook.getNomeImagem()

        );
    }
}
