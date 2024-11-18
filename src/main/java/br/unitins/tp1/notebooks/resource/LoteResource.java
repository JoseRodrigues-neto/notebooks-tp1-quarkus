package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.LoteRequestDTO;
import br.unitins.tp1.notebooks.dto.LoteResponseDTO;
import br.unitins.tp1.notebooks.service.LoteService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/lotes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoteResource {

    @Inject
    private LoteService loteService;

    @GET
    public List<LoteResponseDTO> listAll() {
        return loteService.findAll();
    }

    @GET
    @Path("/{id}")
    public LoteResponseDTO findById(@PathParam("id") Long id) {
        return loteService.findById(id);
    }

    @POST
    public Response create(@Valid LoteRequestDTO dto) {
        LoteResponseDTO createdLote = loteService.create(dto);
        return Response.status(Response.Status.CREATED).entity(createdLote).build();
    }

    @PUT
    @Path("/{id}")
    public LoteResponseDTO update(@PathParam("id") Long id, @Valid LoteRequestDTO dto) {
        return loteService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        loteService.delete(id);
        return Response.noContent().build();
    }
}
