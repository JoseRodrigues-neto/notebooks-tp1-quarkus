package br.unitins.tp1.notebooks.repository;


import br.unitins.tp1.notebooks.modelo.Notebook;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class NotebookRepository implements PanacheRepository<Notebook> {

    public List<Notebook> findByModelo(String modelo) {
        return find("modelo like ?1", "%" + modelo + "%").list();  
    }
}
