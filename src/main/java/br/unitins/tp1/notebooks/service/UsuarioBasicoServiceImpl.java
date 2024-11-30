package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.UsuarioBasicoRequestDTO;
import br.unitins.tp1.notebooks.dto.UsuarioBasicoResponseDTO;
import br.unitins.tp1.notebooks.modelo.Perfil;
import br.unitins.tp1.notebooks.modelo.Usuario;
import br.unitins.tp1.notebooks.modelo.UsuarioBasico;
import br.unitins.tp1.notebooks.repository.UsuarioBasicRepository;
import br.unitins.tp1.notebooks.repository.UsuarioRepository;
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

    public String login(UsuarioBasicoRequestDTO usuarioBasicoRequestDTO) {


        UsuarioBasico usuario = usuarioBasicoRepository.find("email", usuarioBasicoRequestDTO.email()).firstResult();

        if (usuario != null && usuario.getEmail().equals(usuarioBasicoRequestDTO.email())) {
         
            return Jwt.issuer("example.com")
                      .upn(usuarioBasicoRequestDTO.email()) 
                      .claim("perfil", usuario.getPerfil().toString())  
                      .expiresIn(3600)  
                      .sign();  
        }
        return null;
    }

}
