package br.unitins.tp1.notebooks.repository;

import br.unitins.tp1.notebooks.modelo.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;
 

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

   
    public List<Pedido> findByDataPedido(LocalDate dataPedido) {
        return find("dataPedido", dataPedido).list();
    }
   
    public Pedido findById(Long id) {
        return find("id", id).firstResult(); // Usando o método find para buscar pelo ID
    }
    
    public List<Pedido> findByClienteId(Long cliente_id) {
        return find("cliente.id = ?1", cliente_id).list();  // Busca os pedidos pelo ID do cliente
    }
}
