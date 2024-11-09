package br.unitins.tp1.notebooks.service;


import br.unitins.tp1.notebooks.dto.ClienteRequestDTO;
import br.unitins.tp1.notebooks.dto.ClienteResponseDTO;  
import java.util.List;

public interface ClienteService {
    ClienteResponseDTO create(ClienteRequestDTO clienteDTO);  
    List<ClienteResponseDTO> findByNome(String nome);
    ClienteResponseDTO findById(Long id);  
    void update(Long id, ClienteRequestDTO clienteDTO);
    void delete(Long id);
    List<ClienteResponseDTO> listAll(); 
}
 