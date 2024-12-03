package br.unitins.tp1.notebooks.resources;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import br.unitins.tp1.notebooks.dto.AuthRequestDTO;
import br.unitins.tp1.notebooks.dto.ClienteRequestDTO;
import br.unitins.tp1.notebooks.dto.UsuarioBasicoRequestDTO;
import br.unitins.tp1.notebooks.modelo.Usuario;
import br.unitins.tp1.notebooks.service.UsuarioBasicoServiceImpl;
import br.unitins.tp1.notebooks.service.UsuarioService;

@QuarkusTest
public class AuthResourceTest {

        @Inject
    UsuarioService usuarioService;

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testLoginUsuarioExistente() {
        AuthRequestDTO dto = new AuthRequestDTO(
            "n123",  
            "123"    
        );
    
        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/auth") 
            .then()
            .statusCode(200) 
            .header("Authorization", notNullValue())   
            .body("username", equalTo("n123"));   
    }
    

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testLoginUsuarioNormalCredenciaisInvalidas() {
 
        AuthRequestDTO dto = new AuthRequestDTO(
            "teste",      
            "1251"      
        );

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/auth")  
                .then()
                .statusCode(400);  
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testLoginBasicoUsuarioNormal() {
        
        UsuarioBasicoRequestDTO dto = new UsuarioBasicoRequestDTO("teste", "teste@example.com");

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/auth/login-basico")   
                .then()
                .statusCode(200)  
                .header("Authorization", notNullValue()) 
                .body(containsString("Login bem-sucedido")); 
    } 

    @Test
    public void testLoginBasicoUsuarioNormalCredenciaisInvalidas() {
         
        UsuarioBasicoRequestDTO dto = new UsuarioBasicoRequestDTO("usuarioInexistente", "emailErrado@example.com");

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/auth/login-basico")   
                .then()
                .statusCode(401) 
                .body(containsString("Credenciais inválidas")); 
    }
}
