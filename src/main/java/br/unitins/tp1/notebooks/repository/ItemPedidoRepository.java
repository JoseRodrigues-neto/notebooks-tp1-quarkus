package br.unitins.tp1.notebooks.repository;

import br.unitins.tp1.notebooks.modelo.ItemPedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemPedidoRepository implements PanacheRepository<ItemPedido> {
    // Todos os métodos padrão do Panache estão disponíveis:
    // - persist()
    // - findById()
    // - listAll()
    // - delete()
    // Métodos personalizados podem ser adicionados abaixo, se necessário.
}
