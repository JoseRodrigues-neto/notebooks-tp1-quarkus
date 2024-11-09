// package br.unitins.tp1.notebooks.resource;

// import br.unitins.tp1.notebooks.dto.UsuarioRequestDTO;
// import br.unitins.tp1.notebooks.dto.UsuarioResponseDTO;
// import br.unitins.tp1.notebooks.service.UsuarioService;
// import jakarta.inject.Inject;
// import jakarta.validation.Valid;
// import jakarta.ws.rs.*;
// import jakarta.ws.rs.core.MediaType;
// import jakarta.ws.rs.core.Response;
// import java.util.List;

// @Path("/usuarios")
// @Consumes(MediaType.APPLICATION_JSON)
// @Produces(MediaType.APPLICATION_JSON)
// public class UsuarioResource {

//     @Inject
//     UsuarioService usuarioService;

//     @GET
//     public List<UsuarioResponseDTO> listAll() {
//         return usuarioService.listAll();  
//     }

//     @GET
//     @Path("/{id}")
//     public UsuarioResponseDTO findById(@PathParam("id") Long id) {
//         return usuarioService.findById(id);
//     }

//     @POST
//     public Response create(@Valid UsuarioRequestDTO usuarioRequestDTO) {
//         UsuarioResponseDTO createdUsuario = usuarioService.create(usuarioRequestDTO);
//         return Response.status(Response.Status.CREATED).entity(createdUsuario).build();
//     }

//     @PUT
//     @Path("/{id}")
//     public Response update(@PathParam("id") Long id, @Valid UsuarioRequestDTO usuarioDTO) {
//         usuarioService.update(id, usuarioDTO);
//         return Response.noContent().build();
//     }

//     @DELETE
//     @Path("/{id}")
//     public Response delete(@PathParam("id") Long id) {
//         usuarioService.delete(id);
//         return Response.noContent().build();
//     }
// }
