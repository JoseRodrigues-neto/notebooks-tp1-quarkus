package br.unitins.tp1.notebooks.repository;


import br.unitins.tp1.notebooks.modelo.Cliente;
import br.unitins.tp1.notebooks.modelo.Fabricante;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
 
import java.util.List;

@ApplicationScoped
public class FabricanteRepository implements PanacheRepository<Fabricante> {

public List<Fabricante> findByNome(String nome) {
    return find("nome", nome).list();
}

 public Fabricante findByNomeUnico(String nome) {
        return find("nome", nome).firstResult();
    }
    
    public Fabricante findByPais(String pais) {
        return find("paisOrigem", pais).firstResult(); 
    }
}
 