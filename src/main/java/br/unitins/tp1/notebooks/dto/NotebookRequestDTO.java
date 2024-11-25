package br.unitins.tp1.notebooks.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
 
import jakarta.validation.constraints.Size;

public record NotebookRequestDTO(

    @NotNull(message = "O modelo não pode ser nulo.")
    @Size(min = 2, max = 50, message = "O modelo deve ter entre 2 e 50 caracteres.")
    String modelo,

    @NotNull(message = "O preço não pode ser nulo.")
    @Min(value = 0, message = "O preço deve ser maior ou igual a 0.")
    Double preco,

    @NotNull(message = "A garantia não pode ser nula.")
    @Min(value = 0, message = "A garantia deve ser maior ou igual a 0.")
    Integer garantia,

    @NotNull(message = "O ID do fabricante não pode ser nulo.")
    Long fabricanteId,

    @NotNull(message = "O ID da especificação não pode ser nulo.")
    Long especificacaoId,

    @NotNull(message = "A cor não pode ser nula.")
    String cor,

    @NotNull(message = "O ID da categoria não pode ser nulo.")
    Long categoriaId

) {}
