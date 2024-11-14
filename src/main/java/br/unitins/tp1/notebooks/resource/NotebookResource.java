package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.NotebookRequestDTO;
import br.unitins.tp1.notebooks.dto.NotebookResponseDTO;
import br.unitins.tp1.notebooks.service.NotebookService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/notebooks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotebookResource {

    @Inject
    NotebookService notebookService;

    @POST
     @RolesAllowed("Adm")
    public NotebookResponseDTO create(NotebookRequestDTO dto) {
        return notebookService.create(dto);
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Adm")
    public NotebookResponseDTO update(@PathParam("id") Long id, NotebookRequestDTO dto) {
        return notebookService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public void delete(@PathParam("id") Long id) {
        notebookService.delete(id);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("Adm")
    public NotebookResponseDTO findById(@PathParam("id") Long id) {
        return notebookService.findById(id);
    }

    @GET
    public List<NotebookResponseDTO> findAll() {
        return notebookService.findAll();
    }

    @GET
    @Path("/search/{modelo}")
    public List<NotebookResponseDTO> findByModelo(@QueryParam("modelo") String modelo) {
        return notebookService.findByModelo(modelo);
    }
}
