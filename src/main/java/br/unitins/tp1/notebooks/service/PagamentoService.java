package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.CartaoDTO;
import br.unitins.tp1.notebooks.modelo.FormaPagamento;
 
public interface PagamentoService {
    
      FormaPagamento cadastraCartao(CartaoDTO pagamentoCartaoDTO);
      void pagamentoCartao(Long pedidoId);
      void pagamentoPix(Long pedidoId);
      void pagamentoBoleto(Long pedidoId);
}
