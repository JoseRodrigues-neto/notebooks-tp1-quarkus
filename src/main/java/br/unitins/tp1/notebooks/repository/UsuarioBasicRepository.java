package br.unitins.tp1.notebooks.repository;

import br.unitins.tp1.notebooks.modelo.UsuarioBasico;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioBasicRepository implements PanacheRepositoryBase<UsuarioBasico, Long> {
   
}
