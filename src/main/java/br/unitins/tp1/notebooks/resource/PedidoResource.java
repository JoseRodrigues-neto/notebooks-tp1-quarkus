package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.PedidoRequestDTO;
import br.unitins.tp1.notebooks.dto.PedidoResponseDTO;
import br.unitins.tp1.notebooks.dto.PedidoResponseExpandidoDTO;
import br.unitins.tp1.notebooks.modelo.Pedido;

import br.unitins.tp1.notebooks.service.PedidoService;

import jakarta.annotation.security.RolesAllowed;
import org.jboss.logging.Logger;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService pedidoService;

    private static final Logger LOG = Logger.getLogger(PedidoResource.class);

    @POST
    @RolesAllowed("User")
    public Response create(PedidoRequestDTO pedidoDTO) {
       LOG.info("criando pedido");
            Pedido pedido = pedidoService.create(pedidoDTO);
            return Response.status(Response.Status.CREATED)
                    .entity(PedidoResponseDTO.valueOf(pedido))
                    .build();  
    }

    @GET
    @RolesAllowed("Adm")
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        LOG.info("buscando por pedido com o Id: "+ id);
            Pedido pedido = pedidoService.findById(id);
            return Response.ok(PedidoResponseDTO.valueOf(pedido))
                    .build();
      
    }

    @GET
    @RolesAllowed("User")
    @Path("/pedido-detalhado/{id}")
    public Response findByIdPedidoDetalhado(@PathParam("id") Long id) {
        LOG.info("buscando por pedido com o Id: "+ id);
            Pedido pedido = pedidoService.findById(id);
            return Response.ok(PedidoResponseExpandidoDTO.valueOf(pedido))
                    .build();
      
    }

    @PATCH
    @RolesAllowed("Adm")
    @Path("/{id}/status")
    public Response atualizarStatusPedido(@PathParam("id") Long pedidoId, @QueryParam("statusId") Long statusId) {
        LOG.info("Atualizando estatos do pedido com o Id: "+ pedidoId);
            Pedido pedidoAtualizado = pedidoService.alterarStatusPedido(pedidoId, statusId);
            return Response.ok(Map.of(
                    "mensagem", "Status do pedido atualizado com sucesso.",
                    "statusPedido", pedidoAtualizado.getStatus().getDescricao())).build();
   
    }

    @GET
    @RolesAllowed("Adm")
    public List<PedidoResponseDTO> listAll() {
        LOG.info("buscando todos os pedidos");
        return pedidoService.listAll()
                .stream()
                .map(PedidoResponseDTO::valueOf)
                .collect(Collectors.toList());
    }  

 
    @GET
    @Path("/search/meu")
    @RolesAllowed("User")
    @Produces("application/json")
    public Response findMyPedidos() {
        LOG.info("buscando por meus pedidos");

        return Response.ok(pedidoService.findMyPedidos()).build();
    }

    @PUT
@Path("/cancelar/{id}")
@RolesAllowed("User")
@Produces(MediaType.APPLICATION_JSON)
public Response cancelarPedido(@PathParam("id") Long pedidoId) {
    LOG.info("Cancelando pedido com o Id: "+ pedidoId);
    Pedido pedidoCancelado = pedidoService.cancelarPedido(pedidoId);
    return Response.ok("Pedido com o Id: "+ pedidoId + ", Status: "+pedidoCancelado.getStatus()).build();
}


}
