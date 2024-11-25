package br.unitins.tp1.notebooks.service;


import br.unitins.tp1.notebooks.dto.ClienteRequestDTO;
import br.unitins.tp1.notebooks.dto.ClienteResponseDTO;
import br.unitins.tp1.notebooks.modelo.Cliente;
import jakarta.validation.Valid;

import java.util.List;

public interface ClienteService {
    Cliente create(@Valid ClienteRequestDTO clienteDTO);  
    List<ClienteResponseDTO> findByNome(String nome);
    ClienteResponseDTO findById(Long id);  
    void update(Long id, ClienteRequestDTO clienteDTO);
    void delete(Long id);
    List<ClienteResponseDTO> listAll(); 
    Cliente updateNomeImagem(Long id, String nomeImagem);

}
 