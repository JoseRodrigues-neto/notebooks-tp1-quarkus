package br.unitins.tp1.notebooks.service;

import java.util.List;
import java.util.UUID;

import br.unitins.tp1.notebooks.dto.UsuarioBasicoRequestDTO;
 
import br.unitins.tp1.notebooks.modelo.Perfil;
import br.unitins.tp1.notebooks.modelo.UsuarioBasico;
import br.unitins.tp1.notebooks.repository.UsuarioBasicRepository;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped  
public class UsuarioBasicoServiceImpl implements UsuarioBasicoService {
 
       @Inject
    UsuarioBasicRepository usuarioBasicoRepository;
    
    @Override
    @Transactional
    public UsuarioBasico create(UsuarioBasicoRequestDTO usuarioBasicoRequestDTO) {

        UsuarioBasico usuarioBasico = new UsuarioBasico(usuarioBasicoRequestDTO.nome(), usuarioBasicoRequestDTO.email());
        
          usuarioBasico.setPerfil(Perfil.USER_BASIC); 

          usuarioBasicoRepository.persist(usuarioBasico);
    
        return usuarioBasico;
    }
  
     @Override
     @Transactional
    public String login(UsuarioBasicoRequestDTO usuarioBasicoRequestDTO) {


        UsuarioBasico usuario = usuarioBasicoRepository.find("email", usuarioBasicoRequestDTO.email()).firstResult();

        if (usuario != null && usuario.getEmail().equals(usuarioBasicoRequestDTO.email())) {
         
            return Jwt.issuer("unitins-jwt")
            .upn(usuarioBasicoRequestDTO.email())  
            .claim("perfil", usuario.getPerfil().toString())  
            .claim("sub", usuarioBasicoRequestDTO.email())  
            .claim("groups", List.of("Basico"))  
            .expiresIn(3600) 
            .claim("iat", System.currentTimeMillis() / 1000)  
            .claim("jti", UUID.randomUUID().toString())  
            .sign(); 
  
        }
        return null;
    }

}
