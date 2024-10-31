package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.ClienteRequestDTO;
import br.unitins.tp1.notebooks.dto.ClienteResponseDTO;
import br.unitins.tp1.notebooks.modelo.Cliente;
import br.unitins.tp1.notebooks.modelo.Usuario;
import br.unitins.tp1.notebooks.repository.ClienteRepository;
import br.unitins.tp1.notebooks.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteRequestDTO clienteDTO) {
        Usuario usuario = new Usuario(
                clienteDTO.usuario().getNome(),
                clienteDTO.usuario().getEmail(),
                clienteDTO.usuario().getSenha()
        );
        usuarioRepository.persist(usuario);
    
        Cliente cliente = new Cliente(clienteDTO.cpf(), usuario);
        clienteRepository.persist(cliente);
    
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getCpf(),  
                usuario.getNome(),  
                usuario.getEmail()  
        );
    }
    
    @Override
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            return null;  
        }

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getCpf(),
                cliente.getUsuario().getNome(),
                cliente.getUsuario().getEmail()
        );
    }

    @Override
    @Transactional
    public void update(Long id, ClienteRequestDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente != null) {
            cliente.setCpf(clienteDTO.cpf());

            Usuario usuario = cliente.getUsuario();
            if (usuario != null) {
                usuario.setNome(clienteDTO.usuario().getNome());
                usuario.setEmail(clienteDTO.usuario().getEmail());
                usuario.setSenha(clienteDTO.usuario().getSenha());
            }

            clienteRepository.persist(cliente);
        } else {
            throw new EntityNotFoundException("Cliente não encontrado");  
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente != null) {
            clienteRepository.delete(cliente);
        }
    }

    @Override
    public List<ClienteResponseDTO> listAll() {
        return clienteRepository.findAll().stream()
                .map(cliente -> new ClienteResponseDTO(
                        cliente.getId(),
                        cliente.getCpf(),
                        cliente.getUsuario().getNome(),
                        cliente.getUsuario().getEmail()))
                .collect(Collectors.toList());
    }
}
