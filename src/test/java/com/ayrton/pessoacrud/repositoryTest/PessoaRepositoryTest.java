package com.ayrton.pessoacrud.repositoryTest;

import com.ayrton.pessoacrud.model.Pessoa;
import com.ayrton.pessoacrud.repository.PessoaRepository;
import com.ayrton.pessoacrud.testConfig.TestIntegrationConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PessoaRepositoryTest extends TestIntegrationConfig {

    @Autowired
    private PessoaRepository pessoaRepository;

    @BeforeAll
    public static void iniciar() {
        registraClasse(Pessoa.class);
    }

    @BeforeEach
    void carregaDados(){
        pessoaRepository.saveAndFlush(dadoUmaPessoa());
    }

    @AfterEach
    void apagaDados() {
        pessoaRepository.deleteAll();
    }

    @Test
    void deveriaBuscarPorCpf(){
        Optional<Pessoa> pessoa = pessoaRepository.findByCpf(dadoUmaPessoa().getCpf());
        assertTrue(pessoa.isPresent());
        assertEquals(pessoa.get().getCpf(),dadoUmaPessoa().getCpf());
    }

    @Test
    void naoDeveriaBuscarPorCpf(){
        Optional<Pessoa> pessoa = pessoaRepository.findByCpf("00011122233");
        assertFalse(pessoa.isPresent());
    }

    @Test
    void deveriaBuscarPorIdadeEntreIntervalo(){
        List<Pessoa> pessoas = pessoaRepository.findByIdadeBetween(20L,23L);
        assertFalse(pessoas.isEmpty());
    }
    @Test
    void deveriaBuscarPorIdadeSemIntervalo(){
        List<Pessoa> pessoas = pessoaRepository.findByIdadeBetween(22L,22L);
        assertFalse(pessoas.isEmpty());
    }

    @Test
    void naoDeveriaBuscarPorIdade(){
        List<Pessoa> pessoas = pessoaRepository.findByIdadeBetween(23L,24L);
        assertTrue(pessoas.isEmpty());
    }

    private Pessoa dadoUmaPessoa(){
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Ana");
        pessoa.setCpf("01233344455");
        pessoa.setEhExcluido(false);
        pessoa.setIdade(22L);
        return  pessoa;
    }
}
