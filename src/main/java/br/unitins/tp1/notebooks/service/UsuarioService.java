package br.unitins.tp1.notebooks.service;

import java.util.List;
import br.unitins.tp1.notebooks.modelo.Usuario;
import jakarta.validation.Valid;
import br.unitins.tp1.notebooks.dto.AlteraSenhaDTO;
import br.unitins.tp1.notebooks.dto.AlteraUserNameDTO;
import br.unitins.tp1.notebooks.dto.UsuarioResponseDTO;

public interface UsuarioService {

  List<UsuarioResponseDTO> findByName(String nome);
  List<UsuarioResponseDTO> listAll();
  Usuario findById(Long id);
  Usuario findByUsernameAndSenha(String username, String senha);
  Usuario alteraSenha(@Valid AlteraSenhaDTO alteraSenhaDTO);
  Usuario alteraUsername(@Valid AlteraUserNameDTO alteraUsernameDTO);
  void alteraEmail(String novoEmail);
  void AlteraNome(String novoNome);
  Usuario findByUsername(String username);
}
