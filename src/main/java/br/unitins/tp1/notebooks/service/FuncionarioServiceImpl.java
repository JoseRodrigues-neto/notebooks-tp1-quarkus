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
import jakarta.ws.rs.NotFoundException;

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
    @Transactional
    public Funcionario create(FuncionarioRequestDTO funcionarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(funcionarioDTO.username());
        usuario.setNome(funcionarioDTO.nome());
        usuario.setEmail(funcionarioDTO.email());

        // Gerar hash da senha
        String senhaHash = hashService.getHashSenha(funcionarioDTO.senha());
        usuario.setSenha(senhaHash);

        // Definir perfil do usuário
        usuario.setPerfil(Perfil.ADM);

        usuarioRepository.persist(usuario);

        Funcionario funcionario = new Funcionario();
        funcionario.setMatricula(funcionarioDTO.matricula());
        funcionario.setCargo(funcionarioDTO.cargo());
        funcionario.setUsuario(usuario);

        funcionarioRepository.persist(funcionario);

        return funcionario; // Retorna a entidade Funcionario diretamente
    }

    @Override
    public Funcionario findById(Long id) {
        return funcionarioRepository.findById(id);
    }

    @Override
    @Transactional
    public void update(Long id, FuncionarioRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        if (funcionario == null) {
            throw new  NotFoundException("Funcionário não encontrado com o ID fornecido.");
        }

        // Atualizando os campos do Funcionario
        funcionario.setMatricula(dto.matricula());
        funcionario.setCargo(dto.cargo());

        // Atualizando o Usuario associado ao Funcionario
        Usuario usuario = funcionario.getUsuario();
        usuario.setUsername(dto.username());
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());

        // Atualizar a senha com hash se fornecida
        if (dto.senha() != null && !dto.senha().isEmpty()) {
            String senhaHash = hashService.getHashSenha(dto.senha());
            usuario.setSenha(senhaHash);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        if (funcionario == null) {
            throw new NotFoundException("Funcionário não encontrado com o ID fornecido.");
        }

        // Remover o funcionário e o usuário associado
        Usuario usuario = funcionario.getUsuario();
        funcionarioRepository.delete(funcionario);
        usuarioRepository.delete(usuario);
    }

    @Override
    public List<FuncionarioResponseDTO> listAll() {
        return funcionarioRepository.listAll()
                .stream()
                .map(FuncionarioResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<FuncionarioResponseDTO> findByName(String name) {
        return funcionarioRepository.findByName(name)
                .stream()
                .map(FuncionarioResponseDTO::valueOf)
                .collect(Collectors.toList());
    }
}
