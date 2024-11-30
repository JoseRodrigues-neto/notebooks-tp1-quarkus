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
        // funcina na primeira rodada do codigo depois para 
        ClienteRequestDTO dto = new ClienteRequestDTO(
                "12345678901",              
                "teste",           
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
            .body("cpf", is("12345678901"))
            .extract()
            .path("id");
            
           cliente.delete(cliente.findByCpf("12345678901").getId());
       //    user.delete(user.findByUsername("teste").getId());
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
            .body("size()", greaterThan(0)); // Verifica que pelo menos um cliente foi retornado
    }

    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testFindById() {
        // Criação do cliente
        ClienteRequestDTO dto = new ClienteRequestDTO(
            "12345678902",                // CPF
            "testeId",                      // Username
            "Teste",                      // Nome
            "testeuserID@example.com",      // Email
            "senha123",                   // Senha
            "123456789",                  // Telefone
            "Rua Teste, 123",             // Endereço
            LocalDate.of(1990, 5, 15)     // Data de nascimento
        );
 
        Long id = cliente.create(dto).getId();
      
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/clientes/{id}", id)      
        .then()
            .statusCode(200)               // Verifica se o status da resposta é 200 (OK)
            .body("id", is(id.intValue())); 
      
            cliente.delete(cliente.findByCpf("12345678902").getId());
         //   user.delete(user.findByUsername("testeId").getId());
        }
    
 
    @Test
    @TestSecurity(user = "test", roles = "Adm")
    public void testUpdateCliente() {
        ClienteRequestDTO clienteDTO = new ClienteRequestDTO(
            "12345678903",                // CPF
            "testeUpdate",                // Username
            "Cliente Teste",               // Nome
            "testeusuarios@example.com",    // Email
            "senha123",                    // Senha
            "123456789",                   // Telefone
            "Rua Teste, 123",              // Endereço
            LocalDate.of(1990, 5, 15)      // Data de nascimento
        );
    
        // Cria o cliente e obtém o ID
        Long clienteId = cliente.create(clienteDTO).getId();
    
        // Dados para atualização
        ClienteRequestDTO clienteAtualizadoDTO = new ClienteRequestDTO(
            "12345678903",               // Mantendo o CPF
            "novoUsuario",               // Novo Username
            "Novo Nome",                 // Novo Nome
            "novousuariso@example.com",   // Novo Email
            "senha456",                  // Nova Senha
            "987654321",                 // Novo Telefone
            "Avenida Atualizada, 999",   // Novo Endereço
            LocalDate.of(1985, 11, 30)   // Nova Data de Nascimento
        );
    
        // Atualiza o cliente
        given()
            .contentType(ContentType.JSON)
            .body(clienteAtualizadoDTO)
        .when()
            .put("/clientes/{id}", clienteId)  // Realiza a requisição PUT para atualizar
        .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());  // Verifica se o código de status é 204 (sem conteúdo)
    
        // Verifique se os dados foram atualizados corretamente
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/clientes/{id}", clienteId)  // Requisita os dados atualizados do cliente
        .then()
            .statusCode(200)  // Verifica se o status da resposta é 200 (OK)
            .body("nome", is("Novo Nome"))  // Verifica se o nome foi atualizado
            .body("email", is("novousuariso@example.com"));  // Verifica se o email foi atualizado
        
            cliente.delete(cliente.findByCpf("12345678903").getId());
       //     user.delete(user.findByUsername("novoUsuario").getId());
    }

    

    @Test
    @TestSecurity(user = "test", roles = "Adm")
public void testDeleteCliente() {
    // Criação do cliente
    ClienteRequestDTO dto = new ClienteRequestDTO(
        "12345678904",                // CPF
        "testeDelete",                // Username
        "Cliente Teste",               // Nome
        "testedelete@example.com",    // Email
        "senha123",                    // Senha
        "123456789",                   // Telefone
        "Rua Teste, 123",              // Endereço
        LocalDate.of(1990, 5, 15)      // Data de nascimento
    );

    // Cria o cliente e obtém o ID
    Long clienteId = cliente.create(dto).getId();

    // Faz a requisição DELETE para remover o cliente criado
    given()
        .contentType(ContentType.JSON)
    .when()
        .delete("/clientes/{id}", clienteId)  // Deleta o cliente com o ID especificado
    .then()
        .statusCode(Response.Status.NO_CONTENT.getStatusCode());  

        given()
        .when()
        .get("/funcionarios/{id}", clienteId)
        .then()
        .statusCode(400); 
}

}