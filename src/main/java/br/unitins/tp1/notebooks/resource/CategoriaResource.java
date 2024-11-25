package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.CategoriaRequestDTO;
import br.unitins.tp1.notebooks.dto.CategoriaResponseDTO;
import br.unitins.tp1.notebooks.modelo.Categoria;
import br.unitins.tp1.notebooks.service.CategoriaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/categorias")
@RolesAllowed("Adm")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService categoriaService;

    @POST    
    @RolesAllowed("Adm")
    public Response create(@Valid CategoriaRequestDTO dto) {
        Categoria categoria = categoriaService.create(dto);
        CategoriaResponseDTO response = CategoriaResponseDTO.valueOf(categoria); // Converte para DTO
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaService.update(id, dto);
        CategoriaResponseDTO response = CategoriaResponseDTO.valueOf(categoria); // Converte para DTO
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Categoria categoria = categoriaService.findById(id);
        if (categoria == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        CategoriaResponseDTO response = CategoriaResponseDTO.valueOf(categoria); 
        return Response.ok(response).build();
    }

    @GET
    public Response findAll() {
        List<CategoriaResponseDTO> categorias = categoriaService.findAll();
        return Response.ok(categorias).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        List<CategoriaResponseDTO> categorias = categoriaService.findByNome(nome);
        return Response.ok(categorias).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        categoriaService.delete(id);
        return Response.noContent().build();
    }
}
