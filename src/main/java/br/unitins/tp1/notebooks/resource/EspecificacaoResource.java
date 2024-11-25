package br.unitins.tp1.notebooks.resource;


import br.unitins.tp1.notebooks.dto.EspecificacaoRequestDTO;
import br.unitins.tp1.notebooks.dto.EspecificacaoResponseDTO;
import br.unitins.tp1.notebooks.modelo.Especificacao;

import br.unitins.tp1.notebooks.service.EspecificacaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/especificacoes")
@RolesAllowed("Adm")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EspecificacaoResource {

    @Inject
    EspecificacaoService especificacaoService;

    @POST
    public Response create(@Valid EspecificacaoRequestDTO dto) {
        Especificacao especificacao = especificacaoService.create(dto);
        return Response.status(Response.Status.CREATED)
                       .entity(especificacao)
                       .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, EspecificacaoRequestDTO dto) {
        Especificacao especificacao = especificacaoService.update(id, dto);
        if (especificacao == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Especificação não encontrada com o ID fornecido.")
                           .build();
        }
        return Response.ok(especificacao).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        especificacaoService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Especificacao especificacao = especificacaoService.findById(id);
        if (especificacao == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Especificação não encontrada com o ID fornecido.")
                           .build();
        }
        return Response.ok(especificacao).build();
    }

    @GET
    public List<EspecificacaoResponseDTO> findAll() {
        return especificacaoService.findAll();
    }

    @GET
    @Path("/search/processador/{nome}")
    public List<EspecificacaoResponseDTO> findByProcessador(@PathParam("nome") String nome) {
        return especificacaoService.findByProcessador(nome);
    }
}
