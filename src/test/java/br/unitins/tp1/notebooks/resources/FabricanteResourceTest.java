package br.unitins.tp1.notebooks.resources;

import br.unitins.tp1.notebooks.dto.FabricanteRequestDTO;
import br.unitins.tp1.notebooks.service.FabricanteService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class FabricanteResourceTest {

    @Inject
    FabricanteService fabricanteService;

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testlistAll() {
        given() 
                .when()
                .get("/fabricantes")
                .then()
                .statusCode(200)
                .body("size()", is(notNullValue()));
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testFindById() {
        FabricanteRequestDTO dto = new FabricanteRequestDTO("Fabricante teste1", "Brasil");
        Long id = fabricanteService.create(dto).getId();

        given()
                .when().get("/fabricantes/" + id)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(id.intValue()));
 
        fabricanteService.delete(fabricanteService.findById(id).getId());
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testCreate() {
        FabricanteRequestDTO dto = new FabricanteRequestDTO("Fabricante teste2", "teste");
       
        given()
        .contentType(ContentType.JSON)
        .body(dto)
    .when()
        .post("/fabricantes")
    .then()
        .statusCode(201);

            fabricanteService.delete(fabricanteService.findByPais("teste").getId());      

    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testeUpdate() {
        FabricanteRequestDTO dto = new FabricanteRequestDTO("Fabricante teste3", "EUA");
        Long id = fabricanteService.create(dto).getId();

        FabricanteRequestDTO novoDto = new FabricanteRequestDTO("Fabricante X", "Brasil");

        given()
                .contentType(ContentType.JSON)
                .body(novoDto)
                .when().put("/fabricantes/" + id)
                .then()
                .statusCode(200);

        fabricanteService.delete(fabricanteService.findById(id).getId());
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testDelete() {
        FabricanteRequestDTO dto = new FabricanteRequestDTO("Fabricante teste4", "EUA");
        Long id = fabricanteService.create(dto).getId();

        given()
                .when().delete("/fabricantes/" + id)
                .then()
                .statusCode(204);

        given()
                .when()
                .get("/especificacoes/{id}", id)
                .then()
                .statusCode(400);

    }
}
