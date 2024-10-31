package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.FuncionarioRequestDTO;
import br.unitins.tp1.notebooks.dto.FuncionarioResponseDTO;
import br.unitins.tp1.notebooks.modelo.Funcionario;
import br.unitins.tp1.notebooks.modelo.Usuario;
import br.unitins.tp1.notebooks.repository.FuncionarioRepository;
import br.unitins.tp1.notebooks.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
 

import java.util.List;

@ApplicationScoped
public class FuncionarioServiceImpl implements FuncionarioService {

    @Inject
    FuncionarioRepository funcionarioRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public FuncionarioResponseDTO create(FuncionarioRequestDTO funcionarioDTO) {
 
        Usuario usuario = new Usuario(
                funcionarioDTO.usuario().getNome(),
                funcionarioDTO.usuario().getEmail(),
                funcionarioDTO.usuario().getSenha()
        );
        usuarioRepository.persist(usuario);
    
 
        Funcionario funcionario = new Funcionario(
                funcionarioDTO.matricula(),
                funcionarioDTO.cargo(),
                usuario
        );
        funcionarioRepository.persist(funcionario);
    
 
        return new FuncionarioResponseDTO(
                funcionario.getId(),
                usuario.getNome(),  
                usuario.getEmail(),  
                funcionario.getMatricula(),  
                funcionario.getCargo()  
        );
    }
    
    @Override
    public FuncionarioResponseDTO findById(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        if (funcionario == null) {
            return null;
        }

        return new FuncionarioResponseDTO(
                funcionario.getId(),
                funcionario.getMatricula(),
                funcionario.getCargo(),
                funcionario.getUsuario().getNome(),
                funcionario.getUsuario().getEmail()
        );
    }

    @Override
    @Transactional
    public void update(Long id, FuncionarioRequestDTO funcionarioDTO) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        if (funcionario != null) {
      
            funcionario.setMatricula(funcionarioDTO.matricula());  
            funcionario.setCargo(funcionarioDTO.cargo());
    
 
            Usuario usuario = funcionario.getUsuario();
            usuario.setNome(funcionarioDTO.usuario().getNome());
            usuario.setEmail(funcionarioDTO.usuario().getEmail());
            usuario.setSenha(funcionarioDTO.usuario().getSenha());
    
       
            funcionarioRepository.persist(funcionario);
        }
  
    }


    @Override
    @Transactional
    public void delete(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        if (funcionario != null) {
            funcionarioRepository.delete(funcionario);
        }
    }

    @Override
    public List<FuncionarioResponseDTO> listAll() {
        List<Funcionario> funcionarios = funcionarioRepository.listAll();
        return funcionarios.stream()
                .map(f -> new FuncionarioResponseDTO(
                        f.getId(),
                        f.getUsuario().getNome(),    
                        f.getUsuario().getEmail(),    
                        f.getMatricula(),            
                        f.getCargo()                 
                ))
                .toList();
    }
    
    
}
