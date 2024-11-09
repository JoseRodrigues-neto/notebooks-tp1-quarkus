package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.ClienteRequestDTO;
import br.unitins.tp1.notebooks.dto.ClienteResponseDTO;
import br.unitins.tp1.notebooks.service.ClienteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource { 

    @Inject
    ClienteService clienteService;
 
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
    public Response create(ClienteRequestDTO clienteDTO) {
        ClienteResponseDTO createdCliente = clienteService.create(clienteDTO);
        return Response.status(Response.Status.CREATED).entity(createdCliente).build();
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
}
