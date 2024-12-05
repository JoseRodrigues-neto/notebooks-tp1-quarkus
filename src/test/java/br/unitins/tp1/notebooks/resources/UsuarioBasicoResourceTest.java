package br.unitins.tp1.notebooks.resources;

import br.unitins.tp1.notebooks.dto.UsuarioBasicoRequestDTO;
import br.unitins.tp1.notebooks.repository.UsuarioBasicRepository;
import br.unitins.tp1.notebooks.service.UsuarioBasicoServiceImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UsuarioBasicoResourceTest {

    @Inject
    UsuarioBasicoServiceImpl usuarioBasicoService;

    @Inject
    UsuarioBasicRepository usuarioBasicoRepository;

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testCreateUsuarioBasico() {
    
        UsuarioBasicoRequestDTO dto = new UsuarioBasicoRequestDTO(
                "testeTest",      
                "joao@example.com"     
        );
      
 
        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/usuarios-basicos/create")
                .then()
                .statusCode(201)  
                .body("nome", is("testeTest"))  
                .body("email", is("joao@example.com"));
              
    }

}
