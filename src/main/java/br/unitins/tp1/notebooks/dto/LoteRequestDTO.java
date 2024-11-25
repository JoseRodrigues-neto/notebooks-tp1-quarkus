package br.unitins.tp1.notebooks.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public record LoteRequestDTO(

    @NotNull(message = "O ID do notebook não pode ser nulo.")
    Long notebookId,   

    @Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
    int quantidade,    

    @NotNull(message = "A data de entrada não pode ser nula.")
    @PastOrPresent(message = "A data de entrada não pode ser futura.")
    LocalDate dataEntrada 
) {}
