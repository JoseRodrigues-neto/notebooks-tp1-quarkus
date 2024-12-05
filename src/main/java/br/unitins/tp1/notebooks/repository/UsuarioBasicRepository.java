package br.unitins.tp1.notebooks.repository;

import br.unitins.tp1.notebooks.modelo.UsuarioBasico;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioBasicRepository implements PanacheRepositoryBase<UsuarioBasico, Long> {
   
    public UsuarioBasico findByNomeAndEmail(String nome, String email) {
        return find("nome = ?1 and email = ?2", nome, email).firstResult();
    }
}
