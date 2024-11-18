package br.unitins.tp1.notebooks.dto;

import java.time.LocalDate;

public record LoteRequestDTO(
    Long notebookId,   
    int quantidade,    
    LocalDate dataEntrada 
) {}
