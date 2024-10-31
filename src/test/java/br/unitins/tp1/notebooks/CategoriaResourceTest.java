package br.unitins.tp1.notebooks;

import br.unitins.tp1.notebooks.dto.CategoriaRequestDTO;
import br.unitins.tp1.notebooks.dto.CategoriaResponseDTO;
import br.unitins.tp1.notebooks.service.CategoriaService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class CategoriaResourceTest {

    @Inject
    CategoriaService categoriaService;

    @Test
    public void testGetAllCategorias() {
        given()
                .when().get("/categorias")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsertCategoria() {
        CategoriaRequestDTO categoriaRequest = new CategoriaRequestDTO("Categoria Teste", "Descrição da Categoria Teste");

        given()
                .contentType(ContentType.JSON)
                .body(categoriaRequest)
                .when().post("/categorias")
                .then()
                .statusCode(201)
                .body("nome", is("Categoria Teste"),
                      "descricao", is("Descrição da Categoria Teste"));
    }

    @Test
    public void testUpdateCategoria() {
        CategoriaRequestDTO categoriaRequest = new CategoriaRequestDTO("Categoria para Atualização", "Descrição antes da atualização");
        CategoriaResponseDTO createdCategoria = categoriaService.create(categoriaRequest);
        Long categoriaId = createdCategoria.id();

        CategoriaRequestDTO categoriaUpdateRequest = new CategoriaRequestDTO("Categoria Atualizada", "Descrição após a atualização");

        given()
                .contentType(ContentType.JSON)
                .body(categoriaUpdateRequest)
                .when().put("/categorias/" + categoriaId)
                .then()
                .statusCode(200);

        CategoriaResponseDTO updatedCategoria = categoriaService.findById(categoriaId);
        assertThat(updatedCategoria.nome(), is("Categoria Atualizada"));
        assertThat(updatedCategoria.descricao(), is("Descrição após a atualização"));
    }

    @Test
    public void testDeleteCategoria() {
        CategoriaRequestDTO categoriaRequest = new CategoriaRequestDTO("Categoria para Deletar", "Descrição da Categoria para Deletar");
        Long categoriaId = categoriaService.create(categoriaRequest).id();

        // Deletando a categoria
        given()
                .when().delete("/categorias/" + categoriaId)
                .then()
                .statusCode(204);

        // Verificando se a categoria foi excluída
        CategoriaResponseDTO deletedCategoria = categoriaService.findById(categoriaId);
        assertThat(deletedCategoria, is((CategoriaResponseDTO) null));
    }
}
