package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.ClienteRequestDTO;
import br.unitins.tp1.notebooks.dto.ClienteResponseDTO;
import br.unitins.tp1.notebooks.modelo.Cliente;
import br.unitins.tp1.notebooks.modelo.Perfil;
import  br.unitins.tp1.notebooks.validation.ValidationException;
import br.unitins.tp1.notebooks.modelo.Usuario;
import br.unitins.tp1.notebooks.service.HashServiceImpl;
import br.unitins.tp1.notebooks.repository.ClienteRepository;
import br.unitins.tp1.notebooks.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
 

import java.util.List;

@ApplicationScoped 
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
   HashService hashService;

    @Override
    @Transactional
    public Cliente create(@Valid ClienteRequestDTO clienteDTO) {
       
        validarCpf(clienteDTO.cpf());
        // Criar um novo Usuario e setar os atributos
        Usuario usuario = new Usuario();
        usuario.setUsername(clienteDTO.username());
        usuario.setNome(clienteDTO.nome());
        usuario.setEmail(clienteDTO.email());
        
         usuario.setPerfil(Perfil.USER);
        // Realizar o hash da senha antes de persistir
        String senhaHash = hashService.getHashSenha(clienteDTO.senha());
        usuario.setSenha(senhaHash); // Agora a senha está hashada
    
        // Persistir o Usuario no banco de dados primeiro
        usuarioRepository.persist(usuario);
    
        // Criar o Cliente e associar o Usuario a ele
        Cliente cliente = new Cliente();
        cliente.setCpf(clienteDTO.cpf());
        cliente.setTelefone(clienteDTO.telefone());
        cliente.setEndereco(clienteDTO.endereco());
        cliente.setDataNascimento(clienteDTO.dataNascimento());
        cliente.setUsuario(usuario); // Associar o Usuario ao Cliente
    
        // Persistir o Cliente no banco de dados
        clienteRepository.persist(cliente);
    
        // Retornar o cliente recém-criado
        return cliente;
    }
    
    private void validarCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        if (cliente != null) 
            throw new ValidationException("cpf", "cpf já cadastrado");
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
       
        Cliente cliente = clienteRepository.findById(id);
        if (cliente != null) {
            return ClienteResponseDTO.valueOf(cliente);
        }
        return null; 
    }

    @Override
    @Transactional
    public void update(Long id, ClienteRequestDTO clienteDTO) {
        // Buscar o cliente pelo id
        Cliente cliente = clienteRepository.findById(id);
        if (cliente != null) {
            // Atualizar os dados do cliente
            cliente.setCpf(clienteDTO.cpf());
            cliente.setTelefone(clienteDTO.telefone());
            cliente.setEndereco(clienteDTO.endereco());
            cliente.setDataNascimento(clienteDTO.dataNascimento());
            
            // Atualizar os dados do usuário associado
            cliente.getUsuario().setUsername(clienteDTO.username());
            cliente.getUsuario().setNome(clienteDTO.nome());
            cliente.getUsuario().setEmail(clienteDTO.email());
            cliente.getUsuario().setSenha(clienteDTO.senha()); // Certifique-se de que a senha está sendo hashada
    
            // Persistir as alterações
            clienteRepository.persist(cliente); 
        }
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        // Excluir o cliente pelo id
        clienteRepository.deleteById(id);
    }

    @Override
    public List<ClienteResponseDTO> listAll() {
        // Listar todos os clientes e mapear para o DTO
        return clienteRepository.findAll().list().stream()
                .map(ClienteResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<ClienteResponseDTO> findByNome(String nome) {
        // Buscar clientes pelo nome
        return clienteRepository.findByNome(nome).stream()
                .map(ClienteResponseDTO::valueOf)
                .toList();
    }

    @Override
    @Transactional
    public Cliente updateNomeImagem(Long id, String nomeImagem) {
        Cliente Cliente = clienteRepository.findById(id);

        Cliente.setNomeImagem(nomeImagem);
        
        return Cliente;
    }



}
