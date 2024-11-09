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
}
 