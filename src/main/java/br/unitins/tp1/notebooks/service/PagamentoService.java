package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.CartaoDTO;
import br.unitins.tp1.notebooks.modelo.FormaPagamento;
import br.unitins.tp1.notebooks.modelo.PagamentoBoleto;
import br.unitins.tp1.notebooks.modelo.PagamentoCartao;
import br.unitins.tp1.notebooks.modelo.PagamentoPix;
import br.unitins.tp1.notebooks.modelo.Pedido;
import jakarta.validation.Valid;

 
public interface PagamentoService {
    
     
      FormaPagamento cadastraCartao(CartaoDTO pagamentoCartaoDTO);
      void pagamentoCartao(Long pedidoId);
      void pagamentoPix(Long pedidoId);
      void pagamentoBoleto(Long pedidoId);
}
