package br.unitins.tp1.notebooks.dto;

public record EspecificacaoResponseDTO(
    Long id,
    String processador,
    String memoriaRam,
    String armazenamento,
    String tela,
    String bateria,
    Double peso
) {}
