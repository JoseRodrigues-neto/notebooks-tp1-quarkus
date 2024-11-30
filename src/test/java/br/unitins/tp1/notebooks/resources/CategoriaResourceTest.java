// package br.unitins.tp1.notebooks.resources;

// import br.unitins.tp1.notebooks.dto.CategoriaRequestDTO;
// import br.unitins.tp1.notebooks.modelo.Categoria;
// import br.unitins.tp1.notebooks.repository.CategoriaRepository;
// import io.quarkus.test.junit.QuarkusTest;
// import io.restassured.http.ContentType;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import jakarta.inject.Inject;
// import static io.restassured.RestAssured.given;
// import static org.hamcrest.Matchers.*;

// @QuarkusTest
// public class CategoriaResourceTest {

//     @Inject
//     CategoriaRepository categoriaRepository;

//     private Long categoriaId;

//     @BeforeEach
//     public void setup() {
//         // Criar uma categoria de exemplo para os testes de atualização e deleção
//         Categoria categoria = new Categoria();
//         categoria.setNome("Eletrônicos");
//         categoria.setDescricao("Categorias de eletrônicos");
//         categoriaRepository.persist(categoria);
//         categoriaId = categoria.getId();
//     }

//     @Test
//     public void testCreateCategoria() {
//         CategoriaRequestDTO dto = new CategoriaRequestDTO("Computadores", "Categorias de computadores");

//         given()
//             .contentType(ContentType.JSON)
//             .body(dto)
//         .when()
//             .post("/categorias")
//         .then()
//             .statusCode(201)  // 201 - Created
//             .body("nome", equalTo("Computadores"))
//             .body("descricao", equalTo("Categorias de computadores"))
//             .body("id", notNullValue());  // Verifica se o ID foi gerado
//     }

//     @Test
//     public void testUpdateCategoria() {
//         CategoriaRequestDTO dto = new CategoriaRequestDTO("Acessórios", "Categorias de acessórios");

//         given()
//             .contentType(ContentType.JSON)
//             .body(dto)
//         .when()
//             .put("/categorias/" + categoriaId)
//         .then()
//             .statusCode(200)  // 200 - OK
//             .body("nome", equalTo("Acessórios"))
//             .body("descricao", equalTo("Categorias de acessórios"));
//     }

//     @Test
//     public void testFindById() {
//         given()
//             .pathParam("id", categoriaId)
//         .when()
//             .get("/categorias/{id}")
//         .then()
//             .statusCode(200)  // 200 - OK
//             .body("id", equalTo(categoriaId.intValue()))
//             .body("nome", equalTo("Eletrônicos"))
//             .body("descricao", equalTo("Categorias de eletrônicos"));
//     }

//     @Test
//     public void testFindAll() {
//         given()
//         .when()
//             .get("/categorias")
//         .then()
//             .statusCode(200)  // 200 - OK
//             .body("$", not(empty()));  // Verifica se a resposta contém categorias
//     }

//     @Test
//     public void testFindByNome() {
//         given()
//             .pathParam("nome", "Eletrônicos")
//         .when()
//             .get("/categorias/search/{nome}")
//         .then()
//             .statusCode(200)  // 200 - OK
//             .body("size()", greaterThan(0));  // Verifica que existe pelo menos uma categoria com esse nome
//     }

//     @Test
//     public void testDeleteCategoria() {
//         given()
//             .pathParam("id", categoriaId)
//         .when()
//             .delete("/categorias/{id}")
//         .then()
//             .statusCode(204);  // 204 - No Content

//         // Verifica se a categoria foi realmente excluída
//         given()
//             .pathParam("id", categoriaId)
//         .when()
//             .get("/categorias/{id}")
//         .then()
//             .statusCode(404);  // 404 - Not Found, pois a categoria foi excluída
//     }
// }
