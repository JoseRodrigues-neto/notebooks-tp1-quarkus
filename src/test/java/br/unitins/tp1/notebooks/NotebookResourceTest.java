package br.unitins.tp1.notebooks;

import br.unitins.tp1.notebooks.dto.NotebookRequestDTO;
import br.unitins.tp1.notebooks.dto.NotebookResponseDTO;
import br.unitins.tp1.notebooks.service.NotebookService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class NotebookResourceTest {

    @Inject
    NotebookService notebookService;

    @Test
    public void testGetAllNotebooks() {
        given()
                .when().get("/notebooks")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsertNotebook() {
        // Testa a inserção de um notebook
        NotebookRequestDTO notebookRequest = new NotebookRequestDTO(
                "Notebook Teste",
                2500.00,
                24,
                1L,
                1L,
                "PRETO",
                1L
        );

        given()
                .contentType(ContentType.JSON)
                .body(notebookRequest)
                .when().post("/notebooks")
                .then()
                .statusCode(201)
                .body("modelo", is("Notebook Teste"),
                        "preco", is(2500.00),
                        "garantia", is(24));
    }

    @Test
    public void testUpdateNotebook() {
        NotebookRequestDTO notebookRequest = new NotebookRequestDTO(
                "Notebook para Atualização",
                3000.00,
                12,
                1L,
                1L,
                "BRANCO",
                1L
        );

        NotebookResponseDTO createdNotebook = notebookService.create(notebookRequest);
        Long notebookId = createdNotebook.id(); // Acessando o id diretamente do record

        NotebookRequestDTO notebookUpdateRequest = new NotebookRequestDTO(
                "Notebook Atualizado",
                2800.00,
                36,
                1L,
                1L,
                "AZUL",
                1L
        );

        given()
                .contentType(ContentType.JSON)
                .body(notebookUpdateRequest)
                .when().put("/notebooks/" + notebookId)
                .then()
                .statusCode(204);

        NotebookResponseDTO updatedNotebook = notebookService.findById(notebookId);
        assertThat(updatedNotebook.modelo(), is("Notebook Atualizado"));
        assertThat(updatedNotebook.preco(), is(2800.00));
    }

    @Test
    public void testDeleteNotebook() {
        NotebookRequestDTO notebookRequest = new NotebookRequestDTO(
                "Notebook para Deletar",
                1500.00,
                12,
                1L,
                1L,
                "VERDE",
                1L
        );

        Long notebookId = notebookService.create(notebookRequest).id(); // Acessando o id diretamente do record

        // Deletando o notebook
        given()
                .when().delete("/notebooks/" + notebookId)
                 .then()
                .statusCode(204);

        // Verificando se o notebook foi excluído
        NotebookResponseDTO deletedNotebook = notebookService.findById(notebookId);
        assertThat(deletedNotebook, is((NotebookResponseDTO) null));
    }
}
