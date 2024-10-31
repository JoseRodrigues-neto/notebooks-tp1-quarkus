package br.unitins.tp1.notebooks.repository;


import br.unitins.tp1.notebooks.modelo.Fabricante;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
 

@ApplicationScoped
public class FabricanteRepository implements PanacheRepository<Fabricante> {
}
 