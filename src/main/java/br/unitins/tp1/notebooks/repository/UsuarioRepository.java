package br.unitins.tp1.notebooks.repository;

import br.unitins.tp1.notebooks.modelo.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;


@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public List<Usuario> findByName(String nome) {
        return list("nome LIKE ?1", "%" + nome + "%");
    }

    public Usuario findByNameUnico(String nome) {
        return find("nome", nome).firstResult();
    }

    public Usuario findByUsernameAndSenha(String username, String senha) {
        return find("SELECT u FROM Usuario u WHERE u.username = ?1 AND u.senha = ?2", username, senha).firstResult();
    }
    public Usuario findByUsername(String username) {
        return find("username", username).firstResult();
    }

     public Usuario findById(String id) {
        return find("id", id).firstResult();
    }
}
