package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.NotebookRequestDTO;
import br.unitins.tp1.notebooks.dto.NotebookResponseDTO;
import br.unitins.tp1.notebooks.service.NotebookService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/notebooks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotebookResource {

    @Inject
    NotebookService notebookService;

    @POST
    @RolesAllowed("Adm")
    public Response create(@Valid NotebookRequestDTO dto) {
        try {
            NotebookResponseDTO notebook = NotebookResponseDTO.valueOf(notebookService.create(dto));
            return Response.status(Response.Status.CREATED).entity(notebook).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao criar notebook: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response update(@PathParam("id") Long id, NotebookRequestDTO dto) {
        try {
            NotebookResponseDTO notebook = NotebookResponseDTO.valueOf(notebookService.update(id, dto));
            return Response.ok(notebook).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Notebook não encontrado: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao atualizar notebook: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response delete(@PathParam("id") Long id) {
        try {
            notebookService.delete(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Notebook não encontrado: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")   
    public Response findById(@PathParam("id") Long id) {
        try {
            NotebookResponseDTO notebook = NotebookResponseDTO.valueOf(notebookService.findById(id));
            return Response.ok(notebook).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Notebook não encontrado: " + e.getMessage())
                    .build();
        }
    }

    @GET
    public Response findAll() {
        List<NotebookResponseDTO> notebooks = notebookService.findAll();
        return Response.ok(notebooks).build();
    }

    @GET
    @Path("/search")
    public Response findByModelo(@QueryParam("modelo") String modelo) {
        if (modelo == null || modelo.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("O parâmetro 'modelo' não pode ser vazio.")
                    .build();
        }
        List<NotebookResponseDTO> notebooks = notebookService.findByModelo(modelo);
        return Response.ok(notebooks).build();
    }
}
