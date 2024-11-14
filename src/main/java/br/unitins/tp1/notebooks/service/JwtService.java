package br.unitins.tp1.notebooks.service;


import br.unitins.tp1.notebooks.dto.UsuarioResponseDTO;

public interface JwtService {

    String generateJwt(UsuarioResponseDTO dto);
    
}
