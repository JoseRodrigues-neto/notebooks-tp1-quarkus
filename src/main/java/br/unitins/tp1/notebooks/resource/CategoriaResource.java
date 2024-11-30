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

import org.jboss.logging.Logger;

@Path("/categorias")
@RolesAllowed("Adm")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService categoriaService;

    private static final Logger LOG = Logger.getLogger(FuncionarioResource.class);

    @POST
    @RolesAllowed("Adm")
    public Response create(@Valid CategoriaRequestDTO dto) {
        LOG.info("criando categoria");
        Categoria categoria = categoriaService.create(dto);
        CategoriaResponseDTO response = CategoriaResponseDTO.valueOf(categoria); 
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CategoriaRequestDTO dto) {
        LOG.info("atualizando categoria");
        Categoria categoria = categoriaService.update(id, dto);
        CategoriaResponseDTO response = CategoriaResponseDTO.valueOf(categoria); 
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        LOG.info("buscando categoria com o Id: "+id);
        Categoria categoria = categoriaService.findById(id);
    
        CategoriaResponseDTO response = CategoriaResponseDTO.valueOf(categoria);
        return Response.ok(response).build();
    }

    @GET
    public Response findAll() {
        LOG.info("buscando todas as categorias");
        List<CategoriaResponseDTO> categorias = categoriaService.findAll();
        return Response.ok(categorias).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        LOG.info("buscando categoria com o nome: "+nome);
        List<CategoriaResponseDTO> categorias = categoriaService.findByNome(nome);
        return Response.ok(categorias).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOG.info("deletando categoria com o Id: "+id);
        categoriaService.delete(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/descricao")
    public Response updateDescricao(@PathParam("id") Long id, @QueryParam("descricao") String descricao) {
        LOG.info("atualizando categoria com o Id: "+id);
        Categoria categoria = categoriaService.updateDescricao(id, descricao);
        CategoriaResponseDTO response = CategoriaResponseDTO.valueOf(categoria);
        return Response.ok(response).build();

    }
}
