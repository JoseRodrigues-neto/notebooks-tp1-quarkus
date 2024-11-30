package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.PedidoRequestDTO;
import br.unitins.tp1.notebooks.dto.PedidoResponseDTO;
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
  
    private static final Logger LOGGER = Logger.getLogger(PedidoResource.class);
    
    @POST
    public Response create(PedidoRequestDTO pedidoDTO) {
        try {
            Pedido pedido = pedidoService.create(pedidoDTO);
            return Response.status(Response.Status.CREATED)
                    .entity(PedidoResponseDTO.valueOf(pedido))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            Pedido pedido = pedidoService.findById(id);
            return Response.ok(PedidoResponseDTO.valueOf(pedido))
                    .build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }

    @PATCH
    @Path("/{id}/status")
    public Response atualizarStatusPedido(@PathParam("id") Long pedidoId, @QueryParam("statusId") Long statusId) {
        try {
            Pedido pedidoAtualizado = pedidoService.alterarStatusPedido(pedidoId, statusId);
            return Response.ok(Map.of(
                "mensagem", "Status do pedido atualizado com sucesso.",
                "statusPedido", pedidoAtualizado.getStatus().getDescricao()
            )).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }

    @GET
    public List<PedidoResponseDTO> listAll() {
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
        LOGGER.info("Finding my pedidos");

        return Response.ok(pedidoService.findMyPedidos()).build();
    }

}



