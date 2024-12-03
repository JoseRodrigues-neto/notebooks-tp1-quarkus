package br.unitins.tp1.notebooks.dto;

import java.time.LocalDate;
 


public record CartaoDTO(
    String tipoCartao,    // "Crédito" ou "Débito"
    String numeroCartao,  // Número do cartão
    String nomeTitular,   // Nome do titular do cartão
    LocalDate   validade,      // Data de validade do cartão (MM/AA)
    Integer codigoSeguranca // Código de segurança (CVV)
) {}