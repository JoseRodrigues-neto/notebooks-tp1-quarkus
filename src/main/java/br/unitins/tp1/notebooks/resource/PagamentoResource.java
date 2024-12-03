package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.service.PagamentoService;
import br.unitins.tp1.notebooks.service.PedidoService;
import br.unitins.tp1.notebooks.modelo.Pedido;
import br.unitins.tp1.notebooks.dto.CartaoDTO;
import br.unitins.tp1.notebooks.modelo.FormaPagamento;
import br.unitins.tp1.notebooks.modelo.PagamentoBoleto;
import br.unitins.tp1.notebooks.modelo.PagamentoCartao;
import br.unitins.tp1.notebooks.modelo.PagamentoPix;

import java.time.YearMonth;
import java.util.Map;

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
    @Path("/cadastra-cartao")
    @Consumes("application/json")
    @Produces("application/json")
    public Response cadastraCartao(CartaoDTO pagamentoCartaoDTO) {
        // Chama o serviço para processar o pagamento
        pagamentoService.cadastraCartao(pagamentoCartaoDTO);

        // Retorna uma resposta de sucesso
        return Response.ok().build();
    }

    @POST
    @Path("/pagamento-cartao/{pedidoId}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response processarPagamentoCartao(@PathParam("pedidoId") Long pedidoId) {
        pagamentoService.pagamentoCartao(pedidoId);
        return Response.ok().build();
    }

    @POST
    @Path("/pagamento-pix/{pedidoId}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response processarPagamentoPix(@PathParam("pedidoId") Long pedidoId) {
        pagamentoService.pagamentoPix(pedidoId);
        return Response.ok().build();
    }

    @POST
    @Path("/pagamento-boleto/{pedidoId}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response processarPagamentoBoleto(@PathParam("pedidoId") Long pedidoId) {
        pagamentoService.pagamentoBoleto(pedidoId);
        return Response.ok().build();
    }
}
