package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.FabricanteRequestDTO;
import br.unitins.tp1.notebooks.dto.FabricanteResponseDTO;
import br.unitins.tp1.notebooks.service.FabricanteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/fabricantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResource {

    @Inject
    FabricanteService service;

    @GET
    public List<FabricanteResponseDTO> listAll() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    public FabricanteResponseDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    public List<FabricanteResponseDTO> findByNome(@QueryParam("nome") String nome) {
        return service.findByNome(nome);
    }

    @POST
    @RolesAllowed("Adm")
    public Response create(@Valid FabricanteRequestDTO fabricante) {
        service.create(fabricante);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response update (@PathParam("id") Long id, FabricanteRequestDTO fabricante) {
        service.update(id, fabricante);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
