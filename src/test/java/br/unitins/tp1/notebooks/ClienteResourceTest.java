package br.unitins.tp1.notebooks;

import br.unitins.tp1.notebooks.dto.ClienteRequestDTO;
import br.unitins.tp1.notebooks.dto.ClienteResponseDTO;
import br.unitins.tp1.notebooks.service.ClienteService;
import br.unitins.tp1.notebooks.dto.UsuarioRequestDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class ClienteResourceTest {

    @Inject
    ClienteService clienteService;

    @Test
    public void testCreateCliente() {

        ClienteRequestDTO cliente = new ClienteRequestDTO("12345678901",
                new UsuarioRequestDTO("John Doe", "john@example.com", "password"));

        Response response = given()
                .contentType(ContentType.JSON)
                .body(cliente)
                .when()
                .post("/clientes")
                .then()
                .statusCode(201)
                .extract().response();

        ClienteResponseDTO createdCliente = response.as(ClienteResponseDTO.class);
        assert createdCliente.id() != null;
    }

    @Test
    public void testListAllClientes() {

        given()
                .when()
                .get("/clientes")
                .then()
                .statusCode(200)
                .body("size()", is(notNullValue()));
    }

    @Test
    public void testFindById() {

        ClienteRequestDTO cliente = new ClienteRequestDTO("09876543210",
                new UsuarioRequestDTO("Jane Doe", "jane@example.com", "password"));
        ClienteResponseDTO created = clienteService.create(cliente);

        given()
                .when()
                .get("/clientes/{id}", created.id())
                .then()
                .statusCode(200)
                .body("id", is(created.id().intValue()));
    }

    @Test
    public void testUpdateCliente() {

        ClienteRequestDTO cliente = new ClienteRequestDTO("12345678901",
                new UsuarioRequestDTO("John Doe", "john@example.com", "password"));
        ClienteResponseDTO created = clienteService.create(cliente);

        ClienteRequestDTO updatedCliente = new ClienteRequestDTO("12345678901",
                new UsuarioRequestDTO("John New", "john.new@example.com", "newpassword"));

        given()
                .contentType(ContentType.JSON)
                .body(updatedCliente)
                .when()
                .put("/clientes/{id}", created.id())
                .then()
                .statusCode(204);

        ClienteResponseDTO updatedResponse = clienteService.findById(created.id());
        assert updatedResponse != null;

        assert "John New".equals(updatedResponse.nome());
    }

    @Test
    public void testDeleteCliente() {

        ClienteRequestDTO cliente = new ClienteRequestDTO("09876543210",
                new UsuarioRequestDTO("Jane Doe", "jane@example.com", "password"));
        ClienteResponseDTO created = clienteService.create(cliente);

        given()
                .when()
                .delete("/clientes/{id}", created.id())
                .then()
                .statusCode(204);

        // Verifica se o cliente foi realmente excluído
        ClienteResponseDTO responseAfterDelete = clienteService.findById(created.id());
        assert responseAfterDelete == null;
    }
}
