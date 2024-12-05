package br.unitins.tp1.notebooks.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import br.unitins.tp1.notebooks.dto.UsuarioResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    @Override
    public String generateJwt(UsuarioResponseDTO dto) {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(EXPIRATION_TIME);

         
        Set<String> roles = new HashSet<String>();
        roles.add(dto.perfil().getLabel());  

        // Gerando o JWT com as informações do usuário
        return Jwt.issuer("unitins-jwt")
            .subject(dto.username())  
            .groups(roles)  
            .expiresAt(expiryDate)  
            .sign();  
    }
}
