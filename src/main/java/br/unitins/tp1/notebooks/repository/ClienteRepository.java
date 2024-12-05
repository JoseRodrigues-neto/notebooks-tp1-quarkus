package br.unitins.tp1.notebooks.repository;


import br.unitins.tp1.notebooks.modelo.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public Cliente findByCpf(String cpf) {
        return find("cpf", cpf).firstResult(); 
    }
    public List<Cliente> findByNome(String nome) {
        return find("usuario.nome", nome).list();
    }

     public Cliente findByNomeUnico(String nome) {
        return find("usuario.nome", nome).firstResult();
    }

    public Cliente findByUsername(String username){
        return find("usuario.username = ?1", username).firstResult();
    }
}
 