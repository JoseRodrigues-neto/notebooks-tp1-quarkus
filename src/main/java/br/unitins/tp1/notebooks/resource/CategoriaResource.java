package br.unitins.tp1.notebooks.resource;
 

import br.unitins.tp1.notebooks.dto.CategoriaRequestDTO;
import br.unitins.tp1.notebooks.dto.CategoriaResponseDTO;
import br.unitins.tp1.notebooks.service.CategoriaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService categoriaService;

    @POST
    public CategoriaResponseDTO create(CategoriaRequestDTO categoriaRequestDTO) {
        return categoriaService.create(categoriaRequestDTO);
    }

    @PUT
    @Path("/{id}")
    public CategoriaResponseDTO update(@PathParam("id") Long id, CategoriaRequestDTO categoriaRequestDTO) {
        return categoriaService.update(id, categoriaRequestDTO);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        categoriaService.delete(id);
    }

    @GET
    @Path("/{id}")
    public CategoriaResponseDTO findById(@PathParam("id") Long id) {
        return categoriaService.findById(id);
    }

    @GET
    public List<CategoriaResponseDTO> findAll() {
        return categoriaService.findAll();
    }
}
