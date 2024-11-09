package br.unitins.tp1.notebooks.resource;

 

import br.unitins.tp1.notebooks.dto.EspecificacaoRequestDTO;
import br.unitins.tp1.notebooks.dto.EspecificacaoResponseDTO;
import br.unitins.tp1.notebooks.service.EspecificacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/especificacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EspecificacaoResource {

    @Inject
    EspecificacaoService especificacaoService;

    @POST
    public EspecificacaoResponseDTO create(EspecificacaoRequestDTO dto) {
        return especificacaoService.create(dto);
    }

    @PUT
    @Path("/{id}")
    public EspecificacaoResponseDTO update(@PathParam("id") Long id, EspecificacaoRequestDTO dto) {
        return especificacaoService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        especificacaoService.delete(id);
    }

    @GET
    @Path("/{id}")
    public EspecificacaoResponseDTO findById(@PathParam("id") Long id) {
        return especificacaoService.findById(id);
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
