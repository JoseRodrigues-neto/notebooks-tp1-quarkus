package br.unitins.tp1.notebooks.repository;


import br.unitins.tp1.notebooks.modelo.Categoria;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<Categoria> {

    public List<Categoria> findByNome(String nome) {
        return find("nome", nome).list();
    }

    public Categoria findByNomeUnico(String nome) {
        return find("nome", nome).firstResult();
    }
    
}
