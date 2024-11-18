package br.unitins.tp1.notebooks;

import br.unitins.tp1.notebooks.dto.FuncionarioRequestDTO;
import br.unitins.tp1.notebooks.dto.FuncionarioResponseDTO;
import br.unitins.tp1.notebooks.service.FuncionarioService;
import br.unitins.tp1.notebooks.dto.UsuarioRequestDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;a
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class FuncionarioResourceTest {

    @Inject
    FuncionarioService funcionarioService;

    @Test
    public void testCreateFuncionario() {
        FuncionarioRequestDTO funcionario = new FuncionarioRequestDTO("12345", "Developer",
                new UsuarioRequestDTO("John Doe", "john@example.com", "password"));

        Response response = given()
                .contentType(ContentType.JSON)
                .body(funcionario)
                .when()
                .post("/funcionarios")
                .then()
                .statusCode(201)
                .extract().response();

        FuncionarioResponseDTO createdFuncionario = response.as(FuncionarioResponseDTO.class);
        assert createdFuncionario.id() != null;
    }

    @Test
    public void testListAllFuncionarios() {
        given()
                .when()
                .get("/funcionarios")
                .then()
                .statusCode(200)
                .body("size()", is(notNullValue()));
    }

    @Test
    public void testFindById() {

        FuncionarioRequestDTO funcionario = new FuncionarioRequestDTO("54321", "Manager",
                new UsuarioRequestDTO("Jane Doe", "jane@example.com", "password"));
        FuncionarioResponseDTO created = funcionarioService.create(funcionario);

        given()
                .when()
                .get("/funcionarios/{id}", created.id())
                .then()
                .statusCode(200)
                .body("id", is(created.id().intValue()));
    }

    @Test
    public void testUpdateFuncionario() {

        FuncionarioRequestDTO funcionario = new FuncionarioRequestDTO("12345", "Developer",
                new UsuarioRequestDTO("John Doe", "john@example.com", "password"));
        FuncionarioResponseDTO created = funcionarioService.create(funcionario);

        // Criação do DTO para atualização do funcionário
        FuncionarioRequestDTO updatedFuncionario = new FuncionarioRequestDTO("67890", "Senior Developer",
                new UsuarioRequestDTO("John Doe", "john@example.com", "newpassword"));

        // Realiza a atualização
        given()
                .contentType(ContentType.JSON)
                .body(updatedFuncionario)
                .when()
                .put("/funcionarios/{id}", created.id())
                .then()
                .statusCode(200);

        FuncionarioResponseDTO updatedResponse = funcionarioService.findById(created.id());

        assert updatedResponse != null;
        assert updatedResponse.matricula().equals("67890");
        System.out.println("Matrícula Atual: " + updatedResponse.matricula());
    }

    @Test
    public void testDeleteFuncionario() {

        FuncionarioRequestDTO funcionario = new FuncionarioRequestDTO("54321", "Manager",
                new UsuarioRequestDTO("Jane Doe", "jane@example.com", "password"));
        FuncionarioResponseDTO created = funcionarioService.create(funcionario);

        given()
                .when()
                .delete("/funcionarios/{id}", created.id())
                .then()
                .statusCode(204);

        // Verifica se o funcionário foi realmente excluído
        FuncionarioResponseDTO responseAfterDelete = funcionarioService.findById(created.id());
        assert responseAfterDelete == null;
    }
}
