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
}
