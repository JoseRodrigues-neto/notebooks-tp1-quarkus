package br.unitins.tp1.notebooks.resources;

import br.unitins.tp1.notebooks.dto.FuncionarioRequestDTO;
import br.unitins.tp1.notebooks.modelo.Funcionario;
import br.unitins.tp1.notebooks.service.FuncionarioService;
import br.unitins.tp1.notebooks.service.UsuarioService;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class FuncionarioResourceTest {

    @Inject
    FuncionarioService funcionarioService;

    @Inject
    UsuarioService user;

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testCreateFuncionario() {
        FuncionarioRequestDTO dto = new FuncionarioRequestDTO(
                "messi", 
                "Lionel", 
                "messi@example.com",
                "senha123", 
                "12345", 
                "Developer" 
        );

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/funcionarios")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nome", is("Lionel"))
                .body("email", is("messi@example.com"));

        funcionarioService.delete(funcionarioService.findByMatricula("12345").getId());
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testListAllFuncionarios() {
        given()
                .when().get("/funcionarios")
                .then().statusCode(200);
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testFindById() {

        FuncionarioRequestDTO dto = new FuncionarioRequestDTO(
                "ronaldo",
                "cristano", 
                "cristano@example.com",
                "senha123", 
                "12345", 
                "Developer"
        );

        Long id = funcionarioService.create(dto).getId();

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/funcionarios/{id}", id)
                .then()
                .statusCode(200) 
                .body("id", is(id.intValue()));

        funcionarioService.delete(funcionarioService.findByMatricula("12345").getId());
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testUpdateFuncionario() {

        FuncionarioRequestDTO dto = new FuncionarioRequestDTO(
                "neymar", 
                "jr",
                "ney@example.com", 
                "senha123", 
                "12347", 
                "Developer" 
        );

        Long id = funcionarioService.create(dto).getId();

        FuncionarioRequestDTO novodto = new FuncionarioRequestDTO(
                "novoneymar", 
                "novojr",
                "novoney@example.com", 
                "novosenha123", 
                "12347",
                "Developer-senior" 
        );
        given()
                .contentType(ContentType.JSON)
                .body(novodto)
                .when()
                .put("/funcionarios/{id}", id)
                .then()
                .statusCode(200);

        Funcionario funcionario = funcionarioService.findById(id);

        assertEquals(funcionario.getUsuario().getUsername(), "novoneymar");
        assertEquals(funcionario.getCargo(), "Developer-senior");

        funcionarioService.delete(funcionarioService.findByMatricula("12347").getId());

    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testDeleteFuncionario() {

        FuncionarioRequestDTO dto = new FuncionarioRequestDTO(
                "pele", 
                "Edison", 
                "pele@example.com",
                "senha123", 
                "12349",
                "Developer"); 
        Long id = funcionarioService.create(dto).getId();

        given()
                .when()
                .delete("/funcionarios/{id}", id)
                .then()
                .statusCode(204);

        given()
                .when()
                .get("/funcionarios/{id}", id)
                .then()
                .statusCode(400);

    }
}
