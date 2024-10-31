package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.NotebookRequestDTO;
import br.unitins.tp1.notebooks.dto.NotebookResponseDTO;
import br.unitins.tp1.notebooks.service.NotebookService;
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
    public NotebookResponseDTO create(NotebookRequestDTO dto) {
        return notebookService.create(dto);
    }

    @PUT
    @Path("/{id}")
    public NotebookResponseDTO update(@PathParam("id") Long id, NotebookRequestDTO dto) {
        return notebookService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        notebookService.delete(id);
    }

    @GET
    @Path("/{id}")
    public NotebookResponseDTO findById(@PathParam("id") Long id) {
        return notebookService.findById(id);
    }

    @GET
    public List<NotebookResponseDTO> findAll() {
        return notebookService.findAll();
    }
}
