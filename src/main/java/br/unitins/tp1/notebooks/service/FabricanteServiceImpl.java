package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.FabricanteRequestDTO;
import br.unitins.tp1.notebooks.dto.FabricanteResponseDTO;
import br.unitins.tp1.notebooks.modelo.Fabricante;
import br.unitins.tp1.notebooks.repository.FabricanteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FabricanteServiceImpl implements FabricanteService {

   @Inject
   FabricanteRepository repository;

   @Override
   public List<FabricanteResponseDTO> listar() {
       return repository.listAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
   }

   @Override
   public FabricanteResponseDTO buscarPorId(Long id) {
       Fabricante fabricante = repository.findById(id);
       return fabricante != null ? toResponseDTO(fabricante) : null;
   }

   @Override
   @Transactional
   public void salvar(FabricanteRequestDTO dto) {
       Fabricante fabricante = fromRequestDTO(dto);
       repository.persist(fabricante);
   }

   @Override
   @Transactional
   public void atualizar(Long id, FabricanteRequestDTO dto) {
       Fabricante existente = repository.findById(id);
       if (existente != null) {
           existente.setNome(dto.nome());
           existente.setPaisOrigem(dto.paisOrigem());
           repository.persist(existente);
       }
   }

   @Override
   @Transactional
   public void remover(Long id) {
       repository.deleteById(id);
   }

  
   private FabricanteResponseDTO toResponseDTO(Fabricante fabricante) {
       return new FabricanteResponseDTO(fabricante.getId(), fabricante.getNome(), fabricante.getPaisOrigem());
   }

   private Fabricante fromRequestDTO(FabricanteRequestDTO dto) {
       Fabricante fabricante = new Fabricante();
       fabricante.setNome(dto.nome());
       fabricante.setPaisOrigem(dto.paisOrigem());
       return fabricante;
   }
}
