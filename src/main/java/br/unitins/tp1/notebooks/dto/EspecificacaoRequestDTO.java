package br.unitins.tp1.notebooks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EspecificacaoRequestDTO(
    @NotBlank(message = "O processador não pode estar vazio.")
    @Size(max = 100, message = "O nome do processador deve ter no máximo 100 caracteres.")
    String processador,

    @NotBlank(message = "A memória RAM não pode estar vazia.")
    String memoriaRam,

    @NotBlank(message = "O armazenamento não pode estar vazio.")
    String armazenamento,

    @NotBlank(message = "A descrição da tela não pode estar vazia.")
    @Size(max = 50, message = "A descrição da tela deve ter no máximo 50 caracteres.")
    String tela,

    @NotBlank(message = "A especificação da bateria não pode estar vazia.")
    String bateria,

    @NotNull(message = "O peso não pode ser nulo.")
    @Positive(message = "O peso deve ser um valor positivo.")
    Double peso
) {}
