package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.service.PagamentoService;
import br.unitins.tp1.notebooks.service.PedidoService;
import br.unitins.tp1.notebooks.dto.CartaoDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

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
    @RolesAllowed("User")
    public Response cadastraCartao(CartaoDTO pagamentoCartaoDTO) {

        pagamentoService.cadastraCartao(pagamentoCartaoDTO);
        return Response.ok().build();
    }

    @POST
    @Path("/pagamento-cartao/{pedidoId}")
    @Consumes("application/json")
    @Produces("application/json")
    @RolesAllowed("User")
    public Response processarPagamentoCartao(@PathParam("pedidoId") Long pedidoId) {
        pagamentoService.pagamentoCartao(pedidoId);
        return Response.ok().build();
    }

    @POST
    @Path("/pagamento-pix/{pedidoId}")
    @Consumes("application/json")
    @Produces("application/json")
    @RolesAllowed("User")
    public Response processarPagamentoPix(@PathParam("pedidoId") Long pedidoId) {
        pagamentoService.pagamentoPix(pedidoId);
        return Response.ok().build();
    }

    @POST
    @Path("/pagamento-boleto/{pedidoId}")
    @Consumes("application/json")
    @Produces("application/json")
    @RolesAllowed("User")
    public Response processarPagamentoBoleto(@PathParam("pedidoId") Long pedidoId) {
        pagamentoService.pagamentoBoleto(pedidoId);
        return Response.ok().build();
    }
}
