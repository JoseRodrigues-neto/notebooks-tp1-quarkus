package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.FuncionarioRequestDTO;
import br.unitins.tp1.notebooks.dto.FuncionarioResponseDTO;
import br.unitins.tp1.notebooks.modelo.Funcionario;
import br.unitins.tp1.notebooks.service.FuncionarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/funcionarios")
@RolesAllowed("Adm")
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
    @Path("/search/{nome}")
    public List<FuncionarioResponseDTO> findByName(@PathParam("nome") String nome) {
        return funcionarioService.findByName(nome);
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Funcionario funcionario = funcionarioService.findById(id);
        if (funcionario == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Funcionário com ID " + id + " não encontrado.")
                    .build();
        }
        return Response.ok(FuncionarioResponseDTO.valueOf(funcionario)).build();
    }

    @POST
    public Response create(@Valid FuncionarioRequestDTO funcionarioDTO) {
        Funcionario funcionario = funcionarioService.create(funcionarioDTO);
        return Response.status(Response.Status.CREATED)
                .entity(FuncionarioResponseDTO.valueOf(funcionario))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, FuncionarioRequestDTO funcionarioDTO) {
        Funcionario funcionario = funcionarioService.findById(id);
        if (funcionario == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Funcionário com ID " + id + " não encontrado.")
                    .build();
        }
        funcionarioService.update(id, funcionarioDTO);
        return Response.ok()
                .entity("Funcionário com ID " + id + " atualizado com sucesso.")
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Funcionario funcionario = funcionarioService.findById(id);
        if (funcionario == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Funcionário com ID " + id + " não encontrado.")
                    .build();
        }
        funcionarioService.delete(id);
        return Response.noContent().build();
    }
}
