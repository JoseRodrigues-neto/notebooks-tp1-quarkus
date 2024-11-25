package br.unitins.tp1.notebooks;

 
import br.unitins.tp1.notebooks.dto.ClienteRequestDTO;
import br.unitins.tp1.notebooks.repository.ClienteRepository;
import br.unitins.tp1.notebooks.service.ClienteService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import static org.hamcrest.CoreMatchers.is;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static io.restassured.RestAssured.given;

import java.time.LocalDate;


@QuarkusTest
public class ClienteResourceTest {

    @Inject
    ClienteService clienteService;


   
    @Test
public void testFindAll() {
    given()
        .when().get("/clientes") 
        .then().statusCode(200); 
}
  

@Test
public void testCreateCliente() {
    // Dados de entrada para o cliente com LocalDate para a data
    ClienteRequestDTO dto = new ClienteRequestDTO(
        "123456789",           // CPF
        "usuarioTest",         // Username
        "Joao",     // Nome
        "email@teste.com",     // Email
        "senha123",            // Senha
        "123456789",           // Telefone
        "Endereco Teste",      // Endereço
        LocalDate.of(1990, 1, 1) // Data de nascimento
    );

    // Enviar a requisição POST e verificar a resposta
    given()
        .contentType(ContentType.JSON)
        .body(dto)
    .when()
        .post("/clientes")  // Rota do endpoint que você está testando
    .then()
        .statusCode(201)
        .body("usuario.id", notNullValue())  // Verifica que o id não é nulo
        .body("usuario.username", is("usuarioTest")) // Verifica o nome de usuário
        .body("usuario.nome", is("Joao")) // Verifica o nome
        .body("usuario.email", is("email@teste.com")) // Verifica o email
        .body("cpf", is("123456789")) // Verifica o CPF
        .body("telefone", is("123456789")) // Verifica o telefone
        .body("endereco", is("Endereco Teste")) // Verifica o endereço
        .body("dataNascimento", is("1990-01-01")); // Verifica a dsata de nascimento // Verifica se a resposta tem código 201 (created)


}   


@Test
public void testUpdateCliente() {
    // Passo 1: Criar um cliente para ser atualizado
    ClienteRequestDTO clienteCriado = new ClienteRequestDTO(
        "123456789",           // CPF
        "usuarioTest",         // Username
        "Joao",     // Nome
        "email@teste.com",     // Email
        "senha123",            // Senha
        "123456789",           // Telefone
        "Endereco Teste",      // Endereço
        LocalDate.of(1990, 1, 1) // Data de nascimento
    );

    // Realizar a criação do cliente
    Long clienteId = given()
        .contentType(ContentType.JSON)
        .body(clienteCriado)
    .when()
        .post("/clientes")
    .then()
        .statusCode(201)
        .extract()
        .path("id"); // Extrair o ID do cliente criado

    // Passo 2: Atualizar o cliente com novos dados
    ClienteRequestDTO clienteAtualizado = new ClienteRequestDTO(
        "987654321",           // CPF atualizado
        "usuarioAtualizado",   // Username atualizado
        "Joao",     // Nome atualizado
        "novoemail@teste.com", // Email atualizado
        "novaSenha123",        // Senha atualizada
        "987654321",           // Telefone atualizado
        "Novo Endereço",       // Endereço atualizado
        LocalDate.of(1985, 5, 15) // Nova data de nascimento
    );

    // Realizar a atualização do cliente
    given()
        .contentType(ContentType.JSON)
        .body(clienteAtualizado)
    .when()
        .put("/clientes/" + clienteId) // Rota com o ID do cliente
    .then()
        .statusCode(200) // Verifica se a atualização foi bem-sucedida
        .body("id", is(clienteId.intValue())) // Verifica se o ID é o mesmo
        .body("usuario.username", is("usuarioAtualizado")) // Verifica o username atualizado
        .body("usuario.nome", is("Henrique Carlos")) // Verifica o nome atualizado
        .body("usuario.email", is("novoemail@teste.com")) // Verifica o email atualizado
        .body("cpf", is("987654321")) // Verifica o CPF atualizado
        .body("telefone", is("987654321")) // Verifica o telefone atualizado
        .body("endereco", is("Novo Endereço")) // Verifica o endereço atualizado
        .body("dataNascimento", is("1985-05-15")); // Verifica a data de nascimento atualizada
}  

@Test
public void testDeleteCliente() {
    // Primeiro, criar um cliente para excluir
    ClienteRequestDTO dto = new ClienteRequestDTO(
        "987654321",           // CPF
        "usuarioDelete",       // Username
        "Carlos Silva",        // Nome
        "delete@teste.com",    // Email
        "senha123",            // Senha
        "987654321",           // Telefone
        "Endereco para Delete",// Endereço
        LocalDate.of(1985, 5, 15) // Data de nascimento
    );

    // Criar o cliente e obter o ID gerado
    Long clienteId = given()
        .contentType(ContentType.JSON)
        .body(dto)
    .when()
        .post("/clientes")  // Endpoint para criação
    .then()
        .statusCode(201) // Verifica que foi criado com sucesso
        .body("usuario.id", notNullValue()) // Verifica que o ID do usuário foi gerado
        .extract()
        .path("usuario.id"); // Extrai o ID do cliente criado

    // Excluir o cliente pelo ID
    given()
        .when()
        .delete("/clientes/" + clienteId)  // Endpoint para exclusão
    .then()
        .statusCode(204); // Verifica que a exclusão foi bem-sucedida

    // Verificar que o cliente foi realmente excluído
    given()
        .when()
        .get("/clientes/" + clienteId)  // Tentar buscar o cliente excluído
    .then()
        .statusCode(404); // Verifica que o cliente não foi encontrado
}
 
@Test
public void testFindById() {
    Long clienteId = 1L; 

    given()
        .contentType(ContentType.JSON)
    .when()
        .get("/clientes/{id}", clienteId) // Endpoint para buscar o cliente pelo ID
    .then()
        .statusCode(200)  // Verifica que a resposta tem código 200 (OK)
        .body("id", is(clienteId.intValue())); // Verifica que o ID do cliente na resposta é o mesmo
         // Verifica que o email não é nulo
}

}
  