package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.AlteraSenhaDTO;
import br.unitins.tp1.notebooks.dto.ClienteRequestDTO;
import br.unitins.tp1.notebooks.dto.ClienteResponseDTO;
import br.unitins.tp1.notebooks.form.ClienteImageForm;
import br.unitins.tp1.notebooks.dto.AlteraUserNameDTO;
import br.unitins.tp1.notebooks.modelo.Cliente;
import br.unitins.tp1.notebooks.modelo.Usuario;
import br.unitins.tp1.notebooks.service.ClienteService;
import br.unitins.tp1.notebooks.service.FileService;
import br.unitins.tp1.notebooks.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

import java.io.IOException;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(ClienteResource.class);

    @Inject
    @Named("clienteFileService")
    public FileService clienteFileService;

    @GET
    @RolesAllowed("Adm")
    public List<ClienteResponseDTO> listAll() {
        LOG.info("Executando listAll() - Listando todos os clientes.");
        LOG.debug("Iniciando listagem de todos os clientes.");
        return clienteService.listAll();

    }

    @GET
    @RolesAllowed("Adm")
    @Path("/{id}")
    public ClienteResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Execucao do metodo findById. Id: " + id);
        LOG.debug("Buscando clientes por ID");
        return clienteService.findById(id);
    }

    @POST
    @RolesAllowed({"User","Basico","Adm"})
    public Response create(@Valid ClienteRequestDTO clienteDTO) {
        LOG.debug("Criando um novo cliente os dados cadastrados");
        LOG.info("cadastro de cliente.");
        Cliente createdCliente = clienteService.create(clienteDTO);
        ClienteResponseDTO clienteResponseDTO = ClienteResponseDTO.valueOf(createdCliente);
        return Response.status(Response.Status.CREATED)
                .entity(clienteResponseDTO)
                .build();

    }

    @GET
    @RolesAllowed("Adm")
    @Path("/search/{nome}")
    public List<ClienteResponseDTO> findByNome(@PathParam("nome") String nome) {
        LOG.info("Buscando clientes com o nome: " + nome);
        return clienteService.findByNome(nome);
    }

    @PUT
    @RolesAllowed({"User","Adm"})
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ClienteRequestDTO clienteDTO) {
        LOG.info("Atualizando cliente com ID: " + id);

        clienteService.update(id, clienteDTO);
        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed({"User","Adm"})
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOG.info("Deletando cliente com ID: " + id);
        LOG.debug("Deletando cliente");
        clienteService.delete(id);
        return Response.noContent().build();

    }

    @PATCH
    @RolesAllowed("User")
    @Path("/{id}/upload/imagem")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImage(@PathParam("id") Long id, @MultipartForm ClienteImageForm form) {
        LOG.info("Recebendo imagem para upload do cliente com ID: " + id);
        LOG.debug("recebendo mensagem");
        try {
            String nomeImagem = clienteFileService.save(form.getNomeImagem(), form.getImagem());

            clienteService.updateNomeImagem(id, nomeImagem);

        } catch (IOException e) {
            Response.status(500).build();
        }
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed("User")
    @Path("/download/imagem/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadImage(@PathParam("nomeImagem") String nomeImagem) {
        LOG.debug("Iniciando download da imagem: " + nomeImagem);
        ResponseBuilder response = Response.ok(clienteFileService.find(nomeImagem));
        response.header("Content-Disposition", "attachment; filename=" + nomeImagem);
        LOG.debug("Download da imagem '" + nomeImagem + "' concluído.");
        return response.build();

    }

    @GET
    @Path("/meu-usuario")
    @RolesAllowed("User") 
    public ClienteResponseDTO getAuthenticatedUser() {
        LOG.info("Buscando os dados do usuário autenticado.");
        return clienteService.findByMim();
    }

    @PATCH
    @Path("/altera-Senha")
    @RolesAllowed("User")
    public Response alteraSenha(AlteraSenhaDTO alteraSenhaDTO) {
        LOG.debug("Alterando senha do usuário");

        usuarioService.alteraSenha(alteraSenhaDTO);
        return Response.ok("Senha alterada com sucesso!").build();
    }

    @PATCH
    @Path("/atualizar-username")
    @RolesAllowed("User")
    public Response alteraUsername(AlteraUserNameDTO alteraUsernameDTO) {
        LOG.debug("Alterando username do usuário");

        try {
            Usuario usuarioAtualizado = usuarioService.alteraUsername(alteraUsernameDTO);
            return Response.ok(usuarioAtualizado).entity("Username atualizado com sucesso!").build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao atualizar o username")
                    .build();
        }

    }

    @PATCH
    @Path("/atualizar-email")
    @RolesAllowed("User") // Garante que apenas usuários autenticados possam fazer essa alteração
    public Response alteraEmail(@QueryParam("novoNome") String alteraEmail) {
        try {
            usuarioService.alteraEmail(alteraEmail);
            return Response.ok("Email atualizado com sucesso!").build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao atualizar o email").build();
        }
    }

    @PATCH
    @Path("/atualizar-nome")
    @RolesAllowed("User")
    public Response atualizarNome(@QueryParam("novoNome") String novoNome) {
        try {
            usuarioService.AlteraNome(novoNome); // Chama o método do serviço para atualizar o nome
            return Response.ok("Nome atualizado com sucesso!").build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

@PATCH
@Path("/{id}/atualizar-endereco")
@RolesAllowed("User")
public Response atualizarEndereco(@QueryParam("novoEndereco") String novoEndereco) {
    
        clienteService.atualizarEndereco(novoEndereco);
        return Response.ok("Endereço atualizado com sucesso!").build();
   
}


@PATCH
@Path("/{id}/atualizar-telefone")
@RolesAllowed("User")
public Response atualizarTelefone(@QueryParam("novoTelefone") String novoTelefone) {

        clienteService.atualizarTelefone(novoTelefone);
        return Response.ok("Telefone atualizado com sucesso!").build();
  
}

}
