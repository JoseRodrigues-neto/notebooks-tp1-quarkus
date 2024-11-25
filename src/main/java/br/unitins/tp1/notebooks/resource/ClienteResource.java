package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.ClienteRequestDTO;
import br.unitins.tp1.notebooks.dto.ClienteResponseDTO;
import br.unitins.tp1.notebooks.form.ClienteImageForm;
import br.unitins.tp1.notebooks.modelo.Cliente;
import br.unitins.tp1.notebooks.service.ClienteService;
import br.unitins.tp1.notebooks.service.FileService;
import jakarta.validation.Valid;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

import java.io.IOException;
import java.util.List;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource { 

    @Inject
    ClienteService clienteService;

     @Inject
    public FileService ClienteFileService;
 
    @GET
    public List<ClienteResponseDTO> listAll() {
        return clienteService.listAll();
    }

    @GET
    @Path("/{id}")
    public ClienteResponseDTO findById(@PathParam("id") Long id) {
        return clienteService.findById(id);
    }

 @POST
public Response create(@Valid ClienteRequestDTO clienteDTO) {
    Cliente createdCliente = clienteService.create(clienteDTO);
    ClienteResponseDTO clienteResponseDTO = ClienteResponseDTO.valueOf(createdCliente);
    return Response.status(Response.Status.CREATED)
                   .entity(clienteResponseDTO)
                   .build();
}

    @GET
    @Path("/search/{nome}")
    public List<ClienteResponseDTO> findByNome(@PathParam("nome") String nome) {
        return clienteService.findByNome(nome);
    }

  @PUT
@Path("/{id}")
public Response update(@PathParam("id") Long id, ClienteRequestDTO clienteDTO) {
    clienteService.update(id, clienteDTO);
    return Response.noContent().build(); 
}


    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        clienteService.delete(id);
        return Response.noContent().build();
    }
     
    @PATCH
    @Path("/{id}/upload/imagem")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImage(@PathParam("id") Long id, @MultipartForm ClienteImageForm form) {

        try {
            String nomeImagem = ClienteFileService.save(form.getNomeImagem(), form.getImagem());

            clienteService.updateNomeImagem(id, nomeImagem);

        } catch (IOException e) {
           Response.status(500).build();
        }
        return Response.noContent().build();
    }


    @GET
    @Path("/download/imagem/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadImage(@PathParam("nomeImagem") String nomeImagem) {
        ResponseBuilder response = 
            Response.ok(ClienteFileService.find(nomeImagem));
            response.header("Content-Disposition", "attachment; filename="+nomeImagem);
            return response.build();
    }



}
