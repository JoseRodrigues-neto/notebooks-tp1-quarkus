package br.unitins.tp1.notebooks.repository;


import br.unitins.tp1.notebooks.modelo.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public Cliente findByCpf(String cpf) {
        return find("cpf", cpf).firstResult();
    }
}
 