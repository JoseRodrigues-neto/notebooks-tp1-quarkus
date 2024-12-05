package br.unitins.tp1.notebooks.resources;

import br.unitins.tp1.notebooks.dto.NotebookRequestDTO;
import br.unitins.tp1.notebooks.dto.NotebookResponseDTO;
import br.unitins.tp1.notebooks.modelo.Notebook;
import br.unitins.tp1.notebooks.service.NotebookService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class NotebookResourceTest {

    @Inject
    NotebookService notebookService;

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testFindbyAllNotebooks() {
        given()
                .when().get("/notebooks")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testCreateNotebook() {
        NotebookRequestDTO dto = new NotebookRequestDTO(
                "Notebook Teste",
                2500.00,
                24,
                2L,
                1L,
                "PRETO",
                1L);

        Long id = notebookService.create(dto).getId();

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/notebooks")
                .then()
                .statusCode(201)
                .body("modelo", is("Notebook Teste"))  
                .body("cor", is("PRETO"))
                .body("garantia", is(24));

        notebookService.delete(notebookService.findById(id).getId());
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testUpdateNotebook() {
        NotebookRequestDTO dto = new NotebookRequestDTO(
                "Notebook para Atualização",
                3000.00,
                12,
                2L,
                1L,
                "BRANCO",
                1L);

        Long id = notebookService.create(dto).getId();

        NotebookRequestDTO novoDto = new NotebookRequestDTO(
                "Notebook Atualizado",
                2800.00,
                36,
                1L,
                1L,
                "AZUL",
                1L);

        given()
                .contentType(ContentType.JSON)
                .body(novoDto)
                .when().put("/notebooks/{id}", id)
                .then()
                .body("modelo", is("Notebook Atualizado"))
                .body("garantia", is(36))
                .body("cor", is("AZUL"))
                .statusCode(200);

        notebookService.delete(notebookService.findById(id).getId());
    }
  
    @Test
@TestSecurity(user = "test", roles = "Adm")
public void testFindById() {
    NotebookRequestDTO dto = new NotebookRequestDTO(
            "Notebook para Buscar",
            3000.00,
            12,
            2L,
            1L,
            "PRETO",
            1L);

    Long id = notebookService.create(dto).getId();

    given()
            .when().get("/notebooks/{id}", id)  
            .then()
            .statusCode(200) 
            .body("id", is(id.intValue()))   
            .body("modelo", is("Notebook para Buscar"));

    // Deleta o notebook após a verificação
    notebookService.delete(notebookService.findById(id).getId());
}

 

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testDeleteNotebook() {
        NotebookRequestDTO dto = new NotebookRequestDTO(
                "Notebook Atualizado",
                2800.00,
                36,
                1L,
                1L,
                "AZUL",
                1L);
        Long id = notebookService.create(dto).getId();

        // Deletando o notebook
        given()
                .when().delete("/notebooks/{id}", id)
                .then()
                .statusCode(204);

               
        given()
                .when()
                .get("/especificacoes/{id}", id)
                .then()
                .statusCode(400);
    }

}
