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

import org.jboss.logging.Logger;

@Path("/especificacoes")
@RolesAllowed("Adm")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EspecificacaoResource {

    @Inject
    EspecificacaoService especificacaoService;
 
        private static final Logger LOG = Logger.getLogger(EspecificacaoResource.class);

    @POST
    public Response create(@Valid EspecificacaoRequestDTO dto) {
        LOG.info("criando especificação");
        Especificacao especificacao = especificacaoService.create(dto);
        return Response.status(Response.Status.CREATED)
                       .entity(especificacao)
                       .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, EspecificacaoRequestDTO dto) {
        LOG.info("Atualizando especificação de Id: "+ id);
        Especificacao especificacao = especificacaoService.update(id, dto);
    
        return Response.ok(especificacao).build();
    }

    @DELETE
    @Path("/{id}") 
    public Response delete(@PathParam("id") Long id) {
        LOG.info("deletando especificação"+ id);
        especificacaoService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        LOG.info("buscando especificação de Id: "+id);
        Especificacao especificacao = especificacaoService.findById(id);
       
        return Response.ok(especificacao).build();
    }

    @GET
    public List<EspecificacaoResponseDTO> findAll() {
        LOG.info("Buscando todas as especificações");
        return especificacaoService.findAll();
    }

    @GET
    @Path("/search/processador/{nome}")
    public List<EspecificacaoResponseDTO> findByProcessador(@PathParam("nome") String nome) {
        LOG.info("buscando por nome do processador: "+nome);
        return especificacaoService.findByProcessador(nome);
    }


    @PATCH
    @Path("/{id}/memoriaRam")
    public Response updateMemoriaRam(@PathParam("id") Long id, @QueryParam("novoValor") String novaMemoriaRam) {
        LOG.info("atualizando memoria ram da especificação com o Id: "+ id);
        Especificacao especificacao = especificacaoService.updateMemoriaRam(id, novaMemoriaRam);
     
        return Response.ok(especificacao).build();
    }

    @PATCH
    @Path("/{id}/armazenamento")
    public Response updateArmazenamento(@PathParam("id") Long id, @QueryParam("novoValor") String novoArmazenamento) {
        LOG.info("atualizando armazenamento da especificação com o Id: "+ id);
        Especificacao especificacao = especificacaoService.updateArmazenamento(id, novoArmazenamento);
 
        return Response.ok(especificacao).build();
    }

 
}
