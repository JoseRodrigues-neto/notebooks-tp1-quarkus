package br.unitins.tp1.notebooks.resources;

import br.unitins.tp1.notebooks.dto.CategoriaRequestDTO;
import br.unitins.tp1.notebooks.repository.CategoriaRepository;
import br.unitins.tp1.notebooks.service.CategoriaService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class CategoriaResourceTest {

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    CategoriaService service;

    @BeforeEach
    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testCreateCategoria() {
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Computadores", "Categorias de computadores");

        Long categoriaId = service.create(dto).getId();

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/categorias")
                .then()
                .statusCode(201)
                .body("nome", equalTo("Computadores"))
                .body("descricao", equalTo("Categorias de computadores"))
                .body("id", notNullValue());

        service.delete(service.findById(categoriaId).getId());
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testUpdateCategoria() {
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Acessorios", "Categorias de acessórios");
        Long categoriaId = service.create(dto).getId();

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .put("/categorias/" + categoriaId)
                .then()
                .statusCode(200)
                .body("nome", equalTo("Acessorios"))
                .body("descricao", equalTo("Categorias de acessórios"));

        service.delete(service.findById(categoriaId).getId());
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testFindById() {
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Computadores", "Categorias de computadores");

        Long categoriaId = service.create(dto).getId();

        given()
                .pathParam("id", categoriaId)
                .when()
                .get("/categorias/{id}")
                .then()
                .statusCode(200) 
                .body("id", equalTo(categoriaId.intValue()))
                .body("nome", equalTo("Computadores"))
                .body("descricao", equalTo("Categorias de computadores"));

        service.delete(service.findById(categoriaId).getId());
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testFindAll() { 
        given()
                .when()
                .get("/categorias")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testFindByNome() {
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Computadores", "Categorias de computadores");

        Long categoriaId = service.create(dto).getId();

        given()
                .pathParam("nome", "Computadores")
                .when()
                .get("/categorias/search/{nome}")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));

        service.delete(service.findById(categoriaId).getId());
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testDeleteCategoria() {
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Computadores", "Categorias de computadores");
        Long categoriaId = service.create(dto).getId();

        given()
                .pathParam("id", categoriaId)
                .when()
                .delete("/categorias/{id}")
                .then()
                .statusCode(204); 

        given()
                .pathParam("id", categoriaId)
                .when()
                .get("/categorias/{id}")
                .then()
                .statusCode(400);

    }
}
