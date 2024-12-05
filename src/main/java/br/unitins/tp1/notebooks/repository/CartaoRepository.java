package br.unitins.tp1.notebooks.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import br.unitins.tp1.notebooks.modelo.PagamentoCartao;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class  CartaoRepository implements PanacheRepository<PagamentoCartao> {

    public boolean findByClienteId(Long clienteId) {
        return find("id", clienteId).count() > 0;
    }
}
