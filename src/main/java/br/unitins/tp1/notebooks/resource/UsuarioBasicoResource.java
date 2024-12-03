package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.UsuarioBasicoRequestDTO;
import br.unitins.tp1.notebooks.modelo.UsuarioBasico;
import br.unitins.tp1.notebooks.service.UsuarioBasicoServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/usuarios-basicos")
public class UsuarioBasicoResource {

    @Inject
    UsuarioBasicoServiceImpl usuarioBasicoService;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Response createUsuarioBasico(UsuarioBasicoRequestDTO usuarioBasicoRequestDTO) {
   
        UsuarioBasico usuarioBasico = usuarioBasicoService.create(usuarioBasicoRequestDTO);

        return Response.status(Response.Status.CREATED).entity(usuarioBasico).build();
    }
}
