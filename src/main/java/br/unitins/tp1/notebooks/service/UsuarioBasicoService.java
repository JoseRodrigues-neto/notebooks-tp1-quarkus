package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.UsuarioBasicoRequestDTO;
import br.unitins.tp1.notebooks.modelo.UsuarioBasico;
import jakarta.validation.Valid;

public interface UsuarioBasicoService {
    
    UsuarioBasico create(@Valid UsuarioBasicoRequestDTO usuarioBasicoRequestDTO);
    String login(@Valid UsuarioBasicoRequestDTO usuarioBasicoRequestDTO);
}
