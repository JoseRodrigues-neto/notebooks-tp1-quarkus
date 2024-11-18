package br.unitins.tp1.notebooks.repository;

import br.unitins.tp1.notebooks.modelo.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;
 

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    // Aqui você pode adicionar métodos personalizados, se necessário
  
    // Exemplo: Encontrar pedidos por cliente
    public List<Pedido> findByClienteId(Long clienteId) {
        return find("cliente.id", clienteId).list();
    }

    // Exemplo: Encontrar pedidos por data
    public List<Pedido> findByDataPedido(LocalDate dataPedido) {
        return find("dataPedido", dataPedido).list();
    }
   
    public Pedido findById(Long id) {
        return find("id", id).firstResult(); // Usando o método find para buscar pelo ID
    }
  
}
