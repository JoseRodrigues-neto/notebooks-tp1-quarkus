package br.unitins.tp1.notebooks.repository;

import br.unitins.tp1.notebooks.modelo.PagamentoPix;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped 
public class  PixRepository implements PanacheRepository<PagamentoPix> {

    
}
