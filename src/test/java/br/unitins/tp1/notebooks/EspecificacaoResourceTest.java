package br.unitins.tp1.notebooks;

import br.unitins.tp1.notebooks.dto.EspecificacaoRequestDTO;
import br.unitins.tp1.notebooks.dto.EspecificacaoResponseDTO;
import br.unitins.tp1.notebooks.service.EspecificacaoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class EspecificacaoResourceTest {

    @Inject
    EspecificacaoService especificacaoService;

    // Método auxiliar para criação de especificações de teste
    private EspecificacaoRequestDTO createTestEspecificacao(String processador, String memoriaRam, String armazenamento, String tela, String bateria, Double peso) {
        return new EspecificacaoRequestDTO(processador, memoriaRam, armazenamento, tela, bateria, peso);
    }
    

    @BeforeEach
    public void setup() {
        // Código para limpar o estado ou configurar ambiente antes de cada teste
        // Para garantir que cada teste comece com um estado limpo, se necessário
    }

    @Test
    public void testCreateEspecificacao() {
        EspecificacaoRequestDTO especificacao = createTestEspecificacao("Intel i7", "16GB", "1TB SSD", "15.6 inches", "8 horas", 2.5);

        given()
                .contentType(ContentType.JSON)
                .body(especificacao)
                .when()
                .post("/especificacoes")
                .then()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("processador", is("Intel i7"))
                .body("memoriaRam", is("16GB"))
                .body("armazenamento", is("1TB SSD"))
                .body("tela", is("15.6 inches"))
                .body("bateria", is("8 horas"))
                .body("peso", is(2.5f));  // 2.5 precisa ser float para a comparação de números de ponto flutuante
    }

    @Test
    public void testListAllEspecificacoes() {
        given()
                .when()
                .get("/especificacoes")
                .then()
                .statusCode(200)
                .body("size()", is(notNullValue()))
                .body("[0].processador", is(notNullValue())); // validação de conteúdo mínimo esperado
    }

    @Test
    public void testFindById() {
        // Criação de uma especificação para teste de busca
        EspecificacaoRequestDTO especificacao = createTestEspecificacao("Intel i9", "32GB", "2TB SSD", "17.3 inches", "10 horas", 3.0);
        EspecificacaoResponseDTO created = especificacaoService.create(especificacao);

        given()
                .when()
                .get("/especificacoes/{id}", created.id())
                .then()
                .statusCode(200)
                .body("id", is(created.id().intValue()))
                .body("processador", is("Intel i9"))
                .body("memoriaRam", is("32GB"))
                .body("armazenamento", is("2TB SSD"));
    }

    @Test
    public void testUpdateEspecificacao() {
        // Criação de uma especificação inicial
        EspecificacaoRequestDTO especificacao = createTestEspecificacao("AMD Ryzen 7", "16GB", "512GB SSD", "14 inches", "6 horas", 1.8);
        EspecificacaoResponseDTO created = especificacaoService.create(especificacao);

        // Definindo novos valores para atualização
        EspecificacaoRequestDTO updatedEspecificacao = createTestEspecificacao("AMD Ryzen 9", "32GB", "1TB SSD", "15 inches", "8 horas", 2.0);

        given()
                .contentType(ContentType.JSON)
                .body(updatedEspecificacao)
                .when()
                .put("/especificacoes/{id}", created.id())
                .then()
                .statusCode(200)
                .body("processador", is("AMD Ryzen 9"))
                .body("memoriaRam", is("32GB"))
                .body("armazenamento", is("1TB SSD"))
                .body("tela", is("15 inches"))
                .body("bateria", is("8 horas"))
                .body("peso", is(2.0f));  // 2.0 como float
    }

    @Test
    public void testDeleteEspecificacao() {
        // Criação de uma especificação para teste de exclusão
        EspecificacaoRequestDTO especificacao = createTestEspecificacao("Intel i5", "8GB", "256GB SSD", "13 inches", "5 horas", 1.5);
        EspecificacaoResponseDTO created = especificacaoService.create(especificacao);

        given()
                .when()
                .delete("/especificacoes/{id}", created.id())
                .then()
                .statusCode(204);

        // Verificação se foi realmente deletado
        given()
                .when()
                .get("/especificacoes/{id}", created.id())
                .then()
                .statusCode(404);
    }
}
