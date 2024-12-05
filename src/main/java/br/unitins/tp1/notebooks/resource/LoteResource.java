package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.LoteRequestDTO;
import br.unitins.tp1.notebooks.dto.LoteResponseDTO;
import br.unitins.tp1.notebooks.service.LoteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import java.util.List;

@Path("/lotes")
@RolesAllowed("Adm")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoteResource {

    @Inject
    private LoteService loteService;

    private static final Logger LOG = Logger.getLogger(LoteResource.class);

    @GET
    @RolesAllowed("Adm")
    public List<LoteResponseDTO> listAll() {
        LOG.info("Listando todos os lotes.");
        return loteService.findAll();
    }

    @GET
    @RolesAllowed("Adm")
    @Path("/{id}")
    public LoteResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando lote com ID: %d" + id);
        return LoteResponseDTO.valueOf(loteService.findById(id));
    }

    @POST
    @RolesAllowed("Adm")
    public Response create(@Valid LoteRequestDTO dto) {
        LOG.info("Criando um novo lote.");
        LoteResponseDTO createdLote = LoteResponseDTO.valueOf(loteService.create(dto));
        return Response.status(Response.Status.CREATED).entity(createdLote).build();
    }

    @PUT
    @RolesAllowed("Adm")
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid LoteRequestDTO dto) {
        LOG.info("Atualizando lote com ID: %d" + id);
        LoteResponseDTO updatedLote = LoteResponseDTO.valueOf(loteService.update(id, dto));
        return Response.ok(updatedLote).build();
    }

    @PATCH
    @RolesAllowed("Adm")
    @Path("/{id}/quantidade")
    public Response updateQuantity(@PathParam("id") Long id, @QueryParam("quantidade") int quantidade) {
        LOG.info("Atualizando quantidade do lote com ID:" + id);
        LoteResponseDTO updatedLote = LoteResponseDTO.valueOf(loteService.atualizarQuantidade(id, quantidade));
        return Response.ok(updatedLote).build();
    }

    @DELETE
    @RolesAllowed("Adm")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOG.info("Deletando lote com ID: " + id);
        loteService.delete(id);
        return Response.noContent().build();
    }
}
