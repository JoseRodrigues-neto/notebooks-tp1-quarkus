package br.unitins.tp1.notebooks.repository;


import br.unitins.tp1.notebooks.modelo.Funcionario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FuncionarioRepository implements PanacheRepository<Funcionario> {

    public Funcionario findByMatricula(String matricula) {
        return find("matricula", matricula).firstResult();
    }
}
