package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.FuncionarioRequestDTO;
import br.unitins.tp1.notebooks.dto.FuncionarioResponseDTO;
import br.unitins.tp1.notebooks.modelo.Funcionario;
import br.unitins.tp1.notebooks.modelo.Perfil;
import br.unitins.tp1.notebooks.modelo.Usuario;
import br.unitins.tp1.notebooks.repository.FuncionarioRepository;
import br.unitins.tp1.notebooks.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FuncionarioServiceImpl implements FuncionarioService {

    @Inject
    FuncionarioRepository funcionarioRepository;

        @Inject
   HashService hashService;

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public FuncionarioResponseDTO findById(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        return funcionario != null ? FuncionarioResponseDTO.valueOf(funcionario) : null;
    }

    @Override
    public List<FuncionarioResponseDTO> findByName(String name) {
        return funcionarioRepository.findByName(name)
                                    .stream()
                                    .map(FuncionarioResponseDTO::valueOf)
                                    .collect(Collectors.toList());
    }

    @Override
    public List<FuncionarioResponseDTO> listAll() {
        return funcionarioRepository.listAll()
                                    .stream()
                                    .map(FuncionarioResponseDTO::valueOf)
                                    .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FuncionarioResponseDTO create(FuncionarioRequestDTO funcionarioDTO) {
         
        Usuario usuario = new Usuario();
        usuario.setUsername(funcionarioDTO.username());
        usuario.setNome(funcionarioDTO.nome());
        usuario.setEmail(funcionarioDTO.email());
       
        String senhaHash = hashService.getHashSenha(funcionarioDTO.senha());
        usuario.setSenha(senhaHash);

              usuario.setPerfil(Perfil.ADM);

        usuarioRepository.persist(usuario);
       
        Funcionario funcionario = new Funcionario();
        funcionario.setMatricula(funcionarioDTO.matricula());
        funcionario.setCargo(funcionarioDTO.cargo());
  
        funcionario.setUsuario(usuario);
        funcionarioRepository.persist(funcionario);
        
    
        // Retornar o DTO de resposta
        return FuncionarioResponseDTO.valueOf(funcionario);
    }
    

    @Override
    @Transactional
    public void update(Long id, FuncionarioRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id);
      
            // Atualizando os campos do Funcionario
            funcionario.setMatricula(dto.matricula());
            funcionario.setCargo(dto.cargo());
    
           
                // Atualizando o Usuario existente
                Usuario usuario = funcionario.getUsuario();
                usuario.setUsername(dto.username());
                usuario.setNome(dto.nome());
                usuario.setEmail(dto.email());
                usuario.setSenha(dto.senha());
    
           
        }
    
    
    @Override
    @Transactional
    public void delete(Long id) {
        funcionarioRepository.deleteById(id);
    }
}
