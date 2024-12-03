package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.FabricanteRequestDTO;
import br.unitins.tp1.notebooks.dto.FabricanteResponseDTO;
import br.unitins.tp1.notebooks.modelo.Fabricante;
import br.unitins.tp1.notebooks.service.FabricanteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import org.jboss.logging.Logger;

@Path("/fabricantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResource {

    @Inject
    FabricanteService service;

    private static final Logger LOG = Logger.getLogger(FabricanteResource.class);

    @GET
    public List<FabricanteResponseDTO> listAll() {
        LOG.info("Listando todos os fabricantes.");
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        LOG.info("Buscando fabricante com ID: " + id);
        Fabricante fabricante = service.findById(id);
        return Response.ok(FabricanteResponseDTO.valueOf(fabricante)).build();
    }

    @GET
    @Path("/search/{nome}")
    public List<FabricanteResponseDTO> findByNome(@QueryParam("nome") String nome) {
        LOG.info("Buscando fabricante pelo nome: " + nome);
        return service.findByNome(nome);
    }

    @POST
    @RolesAllowed("Adm")
    public Response create(@Valid FabricanteRequestDTO fabricante) {
        LOG.info("Criando novo fabrincate");
        service.create(fabricante);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT          
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response update(@PathParam("id") Long id, FabricanteRequestDTO fabricante) {
        LOG.info("atualizando fabricante com Id: " + id);
        service.update(id, fabricante);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response delete(@PathParam("id") Long id) {
        LOG.info("Deletando fabricante com Id: " + id);
        service.delete(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/nome")
    @RolesAllowed("Adm")
    public Response updateNome(@PathParam("id") Long id, @NotBlank @QueryParam("novoNome") String novoNome) {
        LOG.info("Ataulizando nome do fabricante com Id: " + id);
        service.updateNome(id, novoNome);
        return Response.ok("Nome atualizado com sucesso.").build();

    }

    @PATCH
    @Path("/{id}/pais-origem")
    @RolesAllowed("Adm")
    public Response updatePaisOrigem(@PathParam("id") Long id,
            @NotBlank @QueryParam("novoPaisOrigem") String novoPaisOrigem) {
        LOG.info("Ataulizando pais de origem do fabricante com Id: " + id);

        service.updatePaisOrigem(id, novoPaisOrigem);
        return Response.ok("País de origem atualizado com sucesso.").build();

    }
}
