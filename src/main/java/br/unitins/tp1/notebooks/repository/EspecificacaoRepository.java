package br.unitins.tp1.notebooks.repository;
 

import br.unitins.tp1.notebooks.modelo.Cliente;
import br.unitins.tp1.notebooks.modelo.Especificacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
@ApplicationScoped
public class EspecificacaoRepository implements PanacheRepository<Especificacao> {

    public List<Especificacao> findByProcessador(String processador) {
        return find("processador LIKE ?1", "%" + processador + "%").list();
    }
    
      public Especificacao findByProcessadorUnico(String processador) {
        return find("processador", processador).firstResult();
    }

}
