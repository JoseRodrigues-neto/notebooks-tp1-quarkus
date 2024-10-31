package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.FuncionarioRequestDTO;
import br.unitins.tp1.notebooks.dto.FuncionarioResponseDTO;
import br.unitins.tp1.notebooks.service.FuncionarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/funcionarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    @Inject
    FuncionarioService funcionarioService;

    @GET
    public List<FuncionarioResponseDTO> listAll() {
        return funcionarioService.listAll();
    }

    @GET
    @Path("/{id}")
    public FuncionarioResponseDTO findById(@PathParam("id") Long id) {
        return funcionarioService.findById(id);
    }

    @POST
    public Response create(FuncionarioRequestDTO funcionarioDTO) {
        FuncionarioResponseDTO createdFuncionario = funcionarioService.create(funcionarioDTO);
        return Response.status(Response.Status.CREATED).entity(createdFuncionario).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, FuncionarioRequestDTO funcionarioDTO) {
        funcionarioService.update(id, funcionarioDTO);
        return Response.ok().build(); 
    }
    
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        funcionarioService.delete(id);
        return Response.noContent().build();
    }
}
