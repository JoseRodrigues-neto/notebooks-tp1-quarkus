package br.unitins.tp1.notebooks.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import br.unitins.tp1.notebooks.modelo.PagamentoCartao;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class  CartaoRepository implements PanacheRepository<PagamentoCartao> {

    
}
