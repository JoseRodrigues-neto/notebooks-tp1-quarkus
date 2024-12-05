package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.AlteraSenhaDTO;
import br.unitins.tp1.notebooks.dto.AlteraUserNameDTO;
import br.unitins.tp1.notebooks.dto.FuncionarioRequestDTO;
import br.unitins.tp1.notebooks.dto.FuncionarioResponseDTO;
import br.unitins.tp1.notebooks.modelo.Funcionario;
import br.unitins.tp1.notebooks.modelo.Usuario;
import br.unitins.tp1.notebooks.service.FuncionarioService;
import br.unitins.tp1.notebooks.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import org.jboss.logging.Logger;

@Path("/funcionarios")
@RolesAllowed("Adm")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    @Inject
    FuncionarioService funcionarioService;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(FuncionarioResource.class);

    @GET
    @RolesAllowed("Adm")
    public List<FuncionarioResponseDTO> listAll() {
        LOG.info("Listando todos os funcionários.");
        return funcionarioService.listAll();
    }

    @GET
    @RolesAllowed("Adm")
    @Path("/search/{nome}")
    public List<FuncionarioResponseDTO> findByName(@PathParam("nome") String nome) {
        LOG.info("Buscando funcionários com o nome: " + nome);
        return funcionarioService.findByName(nome);
    }

    @GET
    @RolesAllowed("Adm")
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        LOG.info("Buscando funcionário com ID: " + id);
        Funcionario funcionario = funcionarioService.findById(id);
        return Response.ok(FuncionarioResponseDTO.valueOf(funcionario)).build();
    }

    @POST
    @RolesAllowed("Adm")
    public Response create(@Valid FuncionarioRequestDTO funcionarioDTO) {
        LOG.info("Foi criado um novo funcionário.");
        Funcionario funcionario = funcionarioService.create(funcionarioDTO);
        return Response.status(Response.Status.CREATED)
                .entity(FuncionarioResponseDTO.valueOf(funcionario))
                .build();
    }

    @PUT
    @RolesAllowed("Adm")
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, FuncionarioRequestDTO funcionarioDTO) {
        LOG.info("Atualizando funcionário com ID: " + id);

        funcionarioService.update(id, funcionarioDTO);
        return Response.ok()
                .entity("Funcionário com ID " + id + " atualizado com sucesso.")
                .build();
    }

    @DELETE
    @RolesAllowed("Adm")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOG.info("Excluindo funcionário com ID: " + id);
        funcionarioService.delete(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/altera_Senha")
    @RolesAllowed("Adm")
    public Response alteraSenha(AlteraSenhaDTO alteraSenhaDTO) {
        LOG.info("Alterando username do usuário.");
        usuarioService.alteraSenha(alteraSenhaDTO);
        return Response.ok("Senha alterada com sucesso!").build();
    }

    @PATCH
    @Path("/atualizar-username")
    @RolesAllowed("Adm")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alteraUsername(AlteraUserNameDTO alteraUsernameDTO) {
        LOG.info("Alterando email do usuário.");

        Usuario usuarioAtualizado = usuarioService.alteraUsername(alteraUsernameDTO);
        return Response.ok(usuarioAtualizado).entity("Username atualizado com sucesso!").build();

    }

    @PATCH
    @Path("/atualizar-email")
    @RolesAllowed("Adm")
    public Response alteraEmail(String alteraEmail) {
        LOG.info("Alterando email do usuário.");
        usuarioService.alteraEmail(alteraEmail);
        return Response.ok("Email atualizado com sucesso!").build();

    }

    @PATCH
    @Path("/atualizar-nome")
    @RolesAllowed("Adm")
    public Response atualizarNome(@QueryParam("novoNome") String novoNome) {
        LOG.info("Atualizando nome do usuário.");
        usuarioService.AlteraNome(novoNome);
        return Response.ok("Nome atualizado com sucesso!").build();

    }

}
