package br.unitins.tp1.notebooks.dto;

import br.unitins.tp1.notebooks.modelo.Categoria;

public record CategoriaResponseDTO(Long id, String nome, String descricao) {

    public static CategoriaResponseDTO valueOf(Categoria categoria) {
        return new CategoriaResponseDTO(
            categoria.getId(),
            categoria.getNome(),
            categoria.getDescricao()
        );
    }
}
