package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.service.PagamentoService;
import br.unitins.tp1.notebooks.service.PedidoService;
import br.unitins.tp1.notebooks.modelo.Pedido;
import br.unitins.tp1.notebooks.modelo.FormaPagamento;

import java.util.Map;

import br.unitins.tp1.notebooks.dto.PagamentoRequestDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;

@Path("/pagamentos")
public class PagamentoResource {

    @Inject
    PagamentoService pagamentoService;

    @Inject
    PedidoService pedidoService;

   
    @POST
    @Path("/pix")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pagarPix(PagamentoRequestDTO pagamentoRequest) {
        return processarPagamento(pagamentoRequest, FormaPagamento.PIX);
    }

    @POST
    @Path("/cartao-credito")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pagarCartaoCredito(PagamentoRequestDTO pagamentoRequest) {
        return processarPagamento(pagamentoRequest, FormaPagamento.CARTAO_CREDITO);
    }

   
    @POST
    @Path("/cartao-debito")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pagarCartaoDebito(PagamentoRequestDTO pagamentoRequest) {
        return processarPagamento(pagamentoRequest, FormaPagamento.CARTAO_DEBITO);
    }

  
    @POST
    @Path("/boleto")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pagarBoleto(PagamentoRequestDTO pagamentoRequest) {
        return processarPagamento(pagamentoRequest, FormaPagamento.BOLETO);
    }

   
    private Response processarPagamento(PagamentoRequestDTO pagamentoRequest, FormaPagamento formaPagamento) {
        try {
            // Recupera o pedido a partir do ID fornecido no DTO
            Pedido pedido = pedidoService.findById(pagamentoRequest.pedidoId());
            // Processa o pagamento através do serviço de pagamento
            String mensagem = pagamentoService.processarPagamento(pedido, formaPagamento);

            // Retorna a resposta com sucesso
            return Response.ok(Map.of("mensagem", mensagem)).build();
        } catch (Exception e) {
            // Se ocorrer algum erro (pedido não encontrado, etc.), retorna erro 400
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }
}
