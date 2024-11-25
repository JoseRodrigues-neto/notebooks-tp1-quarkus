package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.modelo.Pedido;
import br.unitins.tp1.notebooks.modelo.StatusPedido;
import br.unitins.tp1.notebooks.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import br.unitins.tp1.notebooks.modelo.FormaPagamento;

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService {
     
       @Inject
    PedidoRepository pedidoRepository;


    @Override
    public String processarPagamento(Pedido pedido, FormaPagamento formaPagamento) {
        String mensagem;

        switch (formaPagamento) {
            case PIX:
                mensagem = "Pagamento via PIX processado com sucesso.";
                break;
            case CARTAO_CREDITO:
                mensagem = "Pagamento via Cartão de Crédito processado com sucesso.";
                break;
            case CARTAO_DEBITO:
                mensagem = "Pagamento via Cartão de Débito processado com sucesso.";
                break;
            case BOLETO:
                mensagem = "Boleto gerado com sucesso.";
                break;
            default:
                throw new IllegalArgumentException("Forma de pagamento não suportada.");
        }

   

        return mensagem;
    }
}
