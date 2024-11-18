package br.unitins.tp1.notebooks.repository;

import br.unitins.tp1.notebooks.modelo.Lote;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class LoteRepository implements PanacheRepository<Lote> {

    public Lote findById(Long id) {
        return find("id", id).firstResult();
    }

    public List<Lote> findByNotebookId(Long notebookId) {
        return list("notebook.id", notebookId);
    }
}
