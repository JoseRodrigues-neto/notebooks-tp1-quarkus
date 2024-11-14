package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.AuthRequestDTO;
import br.unitins.tp1.notebooks.dto.UsuarioResponseDTO;
import br.unitins.tp1.notebooks.modelo.Usuario;
import br.unitins.tp1.notebooks.service.HashService;
import br.unitins.tp1.notebooks.service.JwtService;
import br.unitins.tp1.notebooks.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    HashService hashService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    JwtService jwtService;
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthRequestDTO authDTO) {
        // Faz o hash da senha informada
        String hash = hashService.getHashSenha(authDTO.senha());

        // Verifica o usuário no banco de dados usando o username e a senha hash
        Usuario usuario = usuarioService.findByUsernameAndSenha(authDTO.username(), hash);

        if (usuario == null) {
            return Response.status(Status.UNAUTHORIZED)
                .entity("Usuário ou senha inválidos").build(); // Resposta para falha de login
        }

        // Converte o usuário para o DTO de resposta
        UsuarioResponseDTO usuarioDTO = UsuarioResponseDTO.valueOf(usuario);

        // Gera o token JWT para o usuário autenticado
        String jwtToken = jwtService.generateJwt(usuarioDTO);

        // Retorna o token JWT com o cabeçalho "Authorization"
        return Response.ok()
            .header("Authorization", "Bearer " + jwtToken)
            .entity(usuarioDTO)  // Retorna também as informações do usuário (opcional)
            .build();
    }
}
