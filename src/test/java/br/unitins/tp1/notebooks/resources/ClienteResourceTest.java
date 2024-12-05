package br.unitins.tp1.notebooks.resources;

import br.unitins.tp1.notebooks.dto.ClienteRequestDTO;
import br.unitins.tp1.notebooks.repository.ClienteRepository;
import br.unitins.tp1.notebooks.repository.UsuarioRepository;
import br.unitins.tp1.notebooks.service.ClienteService;
import br.unitins.tp1.notebooks.service.UsuarioService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import jakarta.ws.rs.core.Response;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


import java.time.LocalDate;

import static io.restassured.RestAssured.given;
 

@QuarkusTest
public class ClienteResourceTest {

      @Inject
    ClienteService cliente;

      @Inject
    UsuarioRepository usuarioRepository; 
     
    @Inject 
    UsuarioService user;
    
    @Inject
    ClienteRepository clienteRepository; 
   
    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testCreateCliente() {
        ClienteRequestDTO dto = new ClienteRequestDTO(
                "19011478523",             
                "testeuser",          
                "Teste",               
                "testeuser@xample.com", 
                "senha123",         
                "123456789",        
                "Rua Teste, 123",      
                LocalDate.of(1990, 5, 15)  
        );
 
        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/clientes")
        .then() 
            .statusCode(201) 
            .body("id", notNullValue()) 
            .body("nome", is("Teste"))   
            .body("cpf", is("19011478523"));    

       
        cliente.deleteTest(cliente.findByCpf("19011478523").getId());
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testListAllClientes() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/clientes")
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("size()", greaterThan(0)); 
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testFindById() {
        ClienteRequestDTO dto = new ClienteRequestDTO(
            "22222222222",           
            "testeId",                  
            "Teste",                      
            "testeuserID@example.com",     
            "senha123",                  
            "123456789",               
            "Rua Teste, 123",             
            LocalDate.of(1990, 5, 15)    
        );
 
        Long id = cliente.create(dto).getId();
      
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/clientes/{id}", id)      
        .then()
            .statusCode(200)             
            .body("id", is(id.intValue())); 
      
            cliente.deleteTest(cliente.findByCpf("22222222222").getId());
     
        }
    
 
    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testUpdateCliente() {
        ClienteRequestDTO clienteDTO = new ClienteRequestDTO(
            "12345678903",             
            "testeUpdate",            
            "Cliente Teste",              
            "testeusuarios123@example.com",    
            "senha123",                 
            "123456789",                  
            "Rua Teste, 123",         
            LocalDate.of(1990, 5, 15)    
        );
    
        Long clienteId = cliente.create(clienteDTO).getId();
    
        ClienteRequestDTO clienteAtualizadoDTO = new ClienteRequestDTO(
            "12345678999",               
            "novoUsuario",          
            "Novo Nome",                 
            "novousuariso12@example.com",   
            "senha456",              
            "987654321",                
            "Avenida Atualizada, 999",   
            LocalDate.of(1985, 11, 30)   
        );
    
        given()
            .contentType(ContentType.JSON)
            .body(clienteAtualizadoDTO)
        .when()
            .put("/clientes/{id}", clienteId)   
        .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());   
      
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/clientes/{id}", clienteId)   
        .then()
            .statusCode(200)   
            .body("nome", is("Novo zNome"))   
            .body("email", is("novousuariso12@example.com"));   

       
    } 

}