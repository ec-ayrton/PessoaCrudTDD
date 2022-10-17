package com.ayrton.pessoacrud.controllerTest;

import com.ayrton.pessoacrud.exceptions.ErroPadrao;
import com.ayrton.pessoacrud.model.Pessoa;
import com.ayrton.pessoacrud.service.PessoaService;
import com.ayrton.pessoacrud.testConfig.TestIntegrationConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class PessoaControllerTest extends TestIntegrationConfig {


    private RequestSpecification requisicao;

    @Autowired
    private PessoaService pessoaService;

    @Value("${local.server.port}")
    private int porta;

    public static final String RECURSO_NAO_ENCONTRADO = "Recurso não encontrado.";


    @BeforeAll
    public static void iniciar() {
        registraClasse(Pessoa.class);
    }
    @BeforeEach
    void configura(){
        requisicao = new RequestSpecBuilder()
                .setBasePath("/pessoa")
                .setPort(porta)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    @Test
    void deveriaListarTodasPessoasListaVazia() {
        List<Pessoa> listavazia = given()
                .spec(requisicao)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .jsonPath()
                .getList(".", Pessoa.class);
        assertTrue(listavazia.isEmpty());
    }
    @Test
    void deveriaListarTodasPessoasListaNaoVazia() {
        String CPF_2 = "01233344466";
        pessoaService.cadastrarPessoa(dadoUmaPessoa(CPF_2));
        List<Pessoa> listaNaoVazia = given()
                .spec(requisicao)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .jsonPath()
                .getList(".", Pessoa.class);
        assertFalse(listaNaoVazia.isEmpty());

    }
    @Test
    void deveriaBuscarPessoaPorCPF() {
        String CPF_1 = "01233344455";
        Pessoa pessoa = dadoUmaPessoa(CPF_1);
        pessoaService.cadastrarPessoa(pessoa);
        Pessoa pessoaPersistida = given()
                .spec(requisicao)
                .when()
                .get("/{cpf}", CPF_1)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Pessoa.class);

        assertNotNull(pessoaPersistida);
        assertEquals(pessoaPersistida.getCpf(), CPF_1);
        assertEquals(pessoaPersistida.getCpf(),pessoa.getCpf());
        assertEquals(pessoaPersistida.getNome(), pessoa.getNome());
    }

    @Test
    void naoDeveriaBuscarPessoaPorCPF() {
        ErroPadrao exception = given()
                .spec(requisicao)
                .when()
                .get("/{cpf}","00011122288")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .as(ErroPadrao.class);
        assertEquals(RECURSO_NAO_ENCONTRADO, exception.getMensagem());
    }
    private Pessoa dadoUmaPessoa(String cpf){
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("bob");
        pessoa.setCpf(cpf);
        pessoa.setEhExcluido(false);
        pessoa.setIdade(22L);
        return  pessoa;
    }
}
