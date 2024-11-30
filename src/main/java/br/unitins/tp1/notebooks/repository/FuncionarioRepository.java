package br.unitins.tp1.notebooks.repository;


import br.unitins.tp1.notebooks.modelo.Funcionario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class FuncionarioRepository implements PanacheRepository<Funcionario> {

    
    public List<Funcionario> findByName(String name) {
        return find("usuario.nome", name).list(); 
    }

     
    public Funcionario findByNameUnico(String name) {
        return find("usuario.nome", name).firstResult(); 
    }

    public Funcionario findByMatricula(String matricula) {
        return find("matricula", matricula).firstResult(); 
    }


}
