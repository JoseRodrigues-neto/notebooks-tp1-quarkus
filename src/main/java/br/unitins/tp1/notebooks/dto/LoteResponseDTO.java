package br.unitins.tp1.notebooks.dto;

import java.time.LocalDate;

import br.unitins.tp1.notebooks.modelo.Lote;

public record LoteResponseDTO(
    Long id,        
//    NotebookResponseDTO notebook,  
    String Modelo,
    int quantidade,     
    LocalDate dataEntrada 
) {
        public static LoteResponseDTO valueOf(Lote lote) {
        return new LoteResponseDTO(
            lote.getId(),
    //        NotebookResponseDTO.valueOf(lote.getNotebook()),
            lote.getNotebook().getModelo(),
            lote.getQuantidade(),
            lote.getDataEntrada()
        );
    }
}
