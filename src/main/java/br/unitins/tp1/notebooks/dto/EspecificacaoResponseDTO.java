package br.unitins.tp1.notebooks.dto;

import br.unitins.tp1.notebooks.modelo.Especificacao;

public record EspecificacaoResponseDTO(
    Long id,
    String processador,
    String memoriaRam,
    String armazenamento,
    String tela,
    String bateria,
    Double peso
) {
  
    public static EspecificacaoResponseDTO valueOf(Especificacao especificacao) {
        return new EspecificacaoResponseDTO(
            especificacao.getId(),
            especificacao.getProcessador(),
            especificacao.getMemoriaRam(),
            especificacao.getArmazenamento(),
            especificacao.getTela(),
            especificacao.getBateria(),
            especificacao.getPeso()
        );
    }
}
