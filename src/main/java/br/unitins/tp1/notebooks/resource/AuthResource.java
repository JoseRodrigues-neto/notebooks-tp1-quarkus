package br.unitins.tp1.notebooks.resource;

import org.jboss.logging.Logger;

import br.unitins.tp1.notebooks.dto.AuthRequestDTO;
import br.unitins.tp1.notebooks.dto.UsuarioBasicoRequestDTO;
import br.unitins.tp1.notebooks.dto.UsuarioResponseDTO;
import br.unitins.tp1.notebooks.modelo.Usuario;
import br.unitins.tp1.notebooks.service.HashService;
import br.unitins.tp1.notebooks.service.JwtService;
import br.unitins.tp1.notebooks.service.UsuarioService;
import br.unitins.tp1.notebooks.service.UsuarioBasicoService;
import br.unitins.tp1.notebooks.validation.ValidationException;
import jakarta.inject.Inject;
 
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
 
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    HashService hashService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    UsuarioBasicoService usuarioBasicoService;

    @Inject
    JwtService jwtService;

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthRequestDTO authDTO) {
        LOG.info("loggin no sistema usuario: " + authDTO.username());

   
        String hash = hashService.getHashSenha(authDTO.senha());

        Usuario usuario = usuarioService.findByUsernameAndSenha(authDTO.username(), hash);

        if (usuario == null) {
            throw new  ValidationException("UserName ou senha", "Credenciais incorretas");
        }

        UsuarioResponseDTO usuarioDTO = UsuarioResponseDTO.valueOf(usuario);
        String jwtToken = jwtService.generateJwt(usuarioDTO);

        return Response.ok()
                .header("Authorization", "Bearer " + jwtToken)
                .entity(usuarioDTO)
                .build();
    }

    @POST
    @Path("login-basico")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(UsuarioBasicoRequestDTO usuarioBasicoRequestDTO) {
        LOG.info("loggin no sistema" + usuarioBasicoRequestDTO.nome());
        String jwtToken = usuarioBasicoService.login(usuarioBasicoRequestDTO); 
  
        if (jwtToken != null) {
            return Response.ok("Login bem-sucedido")
                    .header("Authorization", "Bearer " + jwtToken)
                    .build();
        }

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Credenciais inválidas")
                .build();
    }
}
