package br.unitins.tp1.notebooks.resources;

import br.unitins.tp1.notebooks.dto.EspecificacaoRequestDTO;
import br.unitins.tp1.notebooks.service.EspecificacaoService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class EspecificacaoResourceTest {

    @Inject
    EspecificacaoService especificacaoService;

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testCreateEspecificacao() {
        EspecificacaoRequestDTO dto = new EspecificacaoRequestDTO(
                "testeCreate",
                "16GB",
                "1TB",
                "15.6 inches",
                "8 horas",
                2.5);

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/especificacoes")
                .then()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("processador", is("testeCreate"))
                .body("memoriaRam", is("16GB"))
                .body("armazenamento", is("1TB"))
                .body("tela", is("15.6 inches"))
                .body("bateria", is("8 horas"))
                .body("peso", is(2.5f));

        especificacaoService.delete(especificacaoService.findByNomeProcessador("testeCreate").getId());
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testListAllEspecificacoes() {
  
            given()
            .when()
            .get("/especificacoes")
            .then()
            .statusCode(200)
            .body("size()", is(notNullValue()));   
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testFindById() {
        EspecificacaoRequestDTO dto = new EspecificacaoRequestDTO(
            "teste",
            "16GB",
            "1TB",
            "15.6 inches",
            "8 horas",
            2.5);
   
            Long id = especificacaoService.create(dto).getId();

    given()
    .when()
    .get("/especificacoes/{id}", id)
    .then()
    .statusCode(200)
    .body("id", is(id.intValue()))
    .body("processador", is("teste"))
    .body("memoriaRam", is("16GB"))
    .body("armazenamento", is("1TB"));

    especificacaoService.delete(especificacaoService.findByNomeProcessador("teste").getId());

    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testUpdateEspecificacao() {
        EspecificacaoRequestDTO dto = new EspecificacaoRequestDTO(
            "testeUpdate",
            "16GB",
            "1TB",
            "15.6 inches",
            "8 horas",
            2.7); 

            Long id = especificacaoService.create(dto).getId();

        EspecificacaoRequestDTO novaEspecificacao = new EspecificacaoRequestDTO(
            "novotesteUpdate",
            "10GB",
            "12TB",
            "16 inches",
            "80 horas",
            2.5); 

        given()
        .contentType(ContentType.JSON)
        .body(novaEspecificacao)
        .when()
        .put("/especificacoes/{id}", id)
        .then()
        .statusCode(200)
        .body("processador", is("novotesteUpdate"))
        .body("memoriaRam", is("10GB"))
        .body("armazenamento", is("12TB"))
        .body("tela", is("16 inches"))
        .body("bateria", is("80 horas"))
        .body("peso", is(2.5f)); 

   especificacaoService.delete(especificacaoService.findByNomeProcessador("novotesteUpdate").getId());
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testDeleteEspecificacao() {
        EspecificacaoRequestDTO dto = new EspecificacaoRequestDTO(
            "testeDelete",
            "10GB",
            "12TB",
            "16 inches",
            "80 horas",
            2.5); 

            Long id = especificacaoService.create(dto).getId();

    given()
    .when()
    .delete("/especificacoes/{id}",  id)
    .then()
    .statusCode(204);

    // Verificação se foi realmente deletado
    given()
    .when()
    .get("/especificacoes/{id}", id)
    .then()
    .statusCode(400);
    }
}
