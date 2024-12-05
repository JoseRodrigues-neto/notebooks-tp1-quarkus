package br.unitins.tp1.notebooks.resource;

import br.unitins.tp1.notebooks.dto.NotebookRequestDTO;
import br.unitins.tp1.notebooks.dto.NotebookResponseDTO;
import br.unitins.tp1.notebooks.form.NotebookImageForm;
import br.unitins.tp1.notebooks.service.FileService;
import br.unitins.tp1.notebooks.modelo.Notebook;
import br.unitins.tp1.notebooks.service.NotebookService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

import org.jboss.logging.Logger;
import java.io.IOException;
import java.util.List;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/notebooks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotebookResource {

    @Inject
    NotebookService notebookService;

    @Inject
    @Named("notebookFileService") 
    public FileService notebookFileService;

    private static final Logger LOG = Logger.getLogger(NotebookResource.class);
 
    @POST 
    @RolesAllowed("Adm")
    public Response create(NotebookRequestDTO dto) {
        LOG.info("Iniciando criação de notebook com os dados fornecidos");
        NotebookResponseDTO notebook = NotebookResponseDTO.valueOf(notebookService.create(dto));
        LOG.info("Notebook criado com sucesso.");
        return Response.status(Response.Status.CREATED).entity(notebook).build();

    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response update(@PathParam("id") Long id, @Valid NotebookRequestDTO dto) {
        LOG.info("Iniciando atualização do notebook com ID: " + id);
        Notebook updatedNotebook = notebookService.update(id, dto);
        NotebookResponseDTO notebookResponse = NotebookResponseDTO.valueOf(updatedNotebook);
        LOG.debug("Notebook com ID " + id + " atualizado com sucesso.");

        return Response.ok(notebookResponse).build();

    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response delete(@PathParam("id") Long id) {
        LOG.debug("Iniciando remoção do notebook com ID: " + id);
        notebookService.delete(id);
        LOG.debug(" deletado");
        return Response.noContent().build();

    }

    @GET
    @RolesAllowed({"Adm", "User", "Basico"})
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        LOG.info("Buscando notebook com ID: " + id);
        NotebookResponseDTO notebook = NotebookResponseDTO.valueOf(notebookService.findById(id));
        return Response.ok(notebook).build();

    }

    @GET
    @RolesAllowed({"Adm", "User","Basico"})
    public Response findAll() {
        LOG.info("Buscando notebooks");
        List<NotebookResponseDTO> notebooks = notebookService.findAll();
        return Response.ok(notebooks).build();
    }

    @GET
    @RolesAllowed({"Adm", "User", "Basico"})
    @Path("/search")
    public Response findByModelo(@QueryParam("modelo") String modelo) {
        LOG.info("Buscando modelo notebook");
        if (modelo == null || modelo.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("O parâmetro 'modelo' não pode ser vazio.")
                    .build();
        }
        List<NotebookResponseDTO> notebooks = notebookService.findByModelo(modelo);
        return Response.ok(notebooks).build();
    }

    @PATCH
    @RolesAllowed("Adm")
    @Path("/{id}/upload/imagem")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImage(@PathParam("id") Long id, @MultipartForm NotebookImageForm form) {
        LOG.info("uplaod de imagem em notebook: " + id);
        try {
            String nomeImagem = notebookFileService.save(form.getNomeImagem(), form.getImagem());

            notebookService.updateNomeImagem(id, nomeImagem);

        } catch (IOException e) {
            Response.status(500).build();
        }
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed({"Adm","User"})
    @Path("/download/imagem/{nomeImagem}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM) 
    public Response downloadImage(@PathParam("nomeImagem") String nomeImagem) {
        LOG.info("download de imagem: " + nomeImagem);
        ResponseBuilder response = Response.ok(notebookFileService.find(nomeImagem));
        response.header("Content-Disposition", "attachment; filename=" + nomeImagem);
        return response.build();
    }

    @PATCH
    @Path("/{id}/preco")
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed("Adm")
    public Response updatePreco(@PathParam("id") Long id, Double preco) {
        LOG.info("Atualizando preço do notebook com ID: " + id);

        Notebook updatedNotebook = notebookService.updatePreco(id, preco);
        return Response.ok(NotebookResponseDTO.valueOf(updatedNotebook)).build();
    }

    @PATCH
    @Path("/{id}/garantia")
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed("Adm")
    public Response updateGarantia(@PathParam("id") Long id, Integer garantia) {
        LOG.info("Atualizando garantia do notebook com ID: " + id);
        Notebook updatedNotebook = notebookService.updateGarantia(id, garantia);
        return Response.ok(NotebookResponseDTO.valueOf(updatedNotebook)).build();
    }



}
