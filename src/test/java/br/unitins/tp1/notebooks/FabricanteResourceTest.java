package br.unitins.tp1.notebooks;

import br.unitins.tp1.notebooks.dto.FabricanteRequestDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class FabricanteResourceTest {

    @Test
    public void testListar() {
        given()
          .when().get("/fabricantes")
          .then()
             .statusCode(200)
             .contentType(ContentType.JSON)
             .body("$", not(empty()));  // Verifica se a lista não está vazia
    }

    @Test
    public void testBuscarPorId() {
        Long id = 1L;  // Substitua por um ID válido no seu import.sql

        given()
          .when().get("/fabricantes/" + id)
          .then()
             .statusCode(200)
             .contentType(ContentType.JSON)
             .body("id", equalTo(id.intValue()));
    }

    @Test
    public void testSalvar() {
        FabricanteRequestDTO novoFabricante = new FabricanteRequestDTO("Fabricante X", "Brasil");

        given()
          .contentType(ContentType.JSON)
          .body(novoFabricante)
          .when().post("/fabricantes")
          .then()
             .statusCode(201);
    }

    @Test
    public void testAtualizar() {
        Long id = 1L;  // Substitua por um ID válido no seu import.sql
        FabricanteRequestDTO fabricanteAtualizado = new FabricanteRequestDTO("Fabricante Y", "EUA");

        given()
          .contentType(ContentType.JSON)
          .body(fabricanteAtualizado)
          .when().put("/fabricantes/" + id)
          .then()
             .statusCode(200);
    }

    @Test
    public void testRemover() {
        Long id = 1L;  // Substitua por um ID válido no seu import.sql

        given()
          .when().delete("/fabricantes/" + id)
          .then()
             .statusCode(204);
    }
}
