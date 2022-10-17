package com.ayrton.pessoacrud.serviceTest;

import com.ayrton.pessoacrud.model.Pessoa;
import com.ayrton.pessoacrud.repository.PessoaRepository;
import com.ayrton.pessoacrud.service.PessoaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServiceImplTest {

    public static final long ID = 1L;
    @MockBean
    private PessoaRepository repository;

    @Autowired
    private PessoaService pessoaService;

    private static String CPF = "01233344455";
    public static final String RECURSO_NAO_ENCONTRADO = "Recurso não encontrado.";

    @Test
    void deveriaBuscarTodasPessoas(){
        when(repository.findAll()).thenReturn(dadoUmaListaDePessoas());
        List<Pessoa> list = pessoaService.buscarTodasPessoas();
        assertFalse(list.isEmpty());
    }

    @Test
    void deveriaBuscarPorCpf(){
        when(repository.findByCpf(CPF)).thenReturn(Optional.of(dadoUmaPessoa(false)));
        Pessoa pessoa = pessoaService.buscarPorCpf(CPF);
        assertEquals(CPF,pessoa.getCpf());
    }

    @Test
    void naoDeveriaBuscarPorCpf(){
        when(repository.findByCpf(CPF)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows( EntityNotFoundException.class, () -> pessoaService.buscarPorCpf(CPF));
        assertEquals(RECURSO_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    void deveriaBuscarPorIdade(){
        when(repository.findByIdadeBetween(20L,23L)).thenReturn(dadoUmaListaDePessoas());
        List<Pessoa> list = pessoaService.buscarPorIdade(20L,23L);
        assertFalse(list.isEmpty());
    }
    @Test
    void naoDeveriaBuscarPorIdade(){
        when(repository.findByIdadeBetween(99L,99L)).thenReturn(new ArrayList<>());
        List<Pessoa> list = pessoaService.buscarPorIdade(99L,99L);
        assertTrue(list.isEmpty());
    }

    @Test
    void deveriaExcluir(){
        Pessoa pessoa = dadoUmaPessoa(false);
        when(repository.findById(ID)).thenReturn(Optional.of(pessoa));
        when(repository.saveAndFlush(dadoUmaPessoa(true))).thenReturn(dadoUmaPessoa(true));
        pessoaService.excluirPessoa(ID);
        verify(repository, times(1)).saveAndFlush(pessoa);
    }
    @Test
    void naoDeveriaExcluir(){
        when(repository.findById(ID)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows( EntityNotFoundException.class, () -> pessoaService.excluirPessoa(ID));
        assertEquals(RECURSO_NAO_ENCONTRADO, exception.getMessage());
        verify(repository, times(0)).saveAndFlush(any());
    }
    @Test
    void deveriaCadastrarPessoa(){
        Pessoa pessoa = dadoUmaPessoa(false);
        pessoaService.cadastrarPessoa(pessoa);
        verify(repository, times(1)).saveAndFlush(pessoa);
    }
    @Test
    void naoDeveriaCadastrarPessoaNulaOuComCamposVazios(){
        Pessoa pessoa = new Pessoa();
        UnsupportedOperationException exceptionCamposVazios = assertThrows( UnsupportedOperationException.class, () -> pessoaService.cadastrarPessoa(pessoa));
        verify(repository, times(0)).saveAndFlush(pessoa);
        assertEquals("Nome vazio.\n" + "cpf vazio.\n" + "idade vazia.\n", exceptionCamposVazios.getMessage());

        UnsupportedOperationException exceptionPessoaNula = assertThrows( UnsupportedOperationException.class, () -> pessoaService.cadastrarPessoa(null));
        verify(repository, times(0)).saveAndFlush(pessoa);
        assertEquals("Objeto nulo!", exceptionPessoaNula.getMessage());
    }
    @Test
    void deveriaEditarPessoa(){
        Pessoa pessoa = dadoUmaPessoa(false);
        when(repository.findById(ID)).thenReturn(Optional.of(pessoa));
        pessoaService.editarPessoa(ID,pessoa);
        verify(repository, times(1)).saveAndFlush(pessoa);
    }
    @Test
    void naoDeveriaEditarPessoaNaoEncontrada(){
        Pessoa pessoa = dadoUmaPessoa(false);
        when(repository.findById(ID)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows( EntityNotFoundException.class, () -> pessoaService.editarPessoa(ID,pessoa));
        assertEquals(RECURSO_NAO_ENCONTRADO, exception.getMessage());
        verify(repository, times(0)).saveAndFlush(pessoa);
    }
    @Test
    void naoDeveriaEditarPessoaComCamposNaoEditaveis(){
        Pessoa pessoa = dadoUmaPessoa(false);
        when(repository.findById(ID)).thenReturn(Optional.of(pessoa));

        UnsupportedOperationException exception = assertThrows( UnsupportedOperationException.class,
                () -> pessoaService.editarPessoa(ID,dadoUmaPessoaComNovosDados(true)));

        assertEquals("Campo cpf não pode ser editado.\n" + "Campo exclusao não pode ser editado.\n", exception.getMessage());
        verify(repository, times(0)).saveAndFlush(pessoa);
    }
    private Pessoa dadoUmaPessoa(boolean excluido){
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("bob");
        pessoa.setCpf(CPF);
        pessoa.setEhExcluido(excluido);
        pessoa.setIdade(22L);
        return  pessoa;
    }
    private Pessoa dadoUmaPessoaComNovosDados(boolean excluido){
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("bob esponja");
        pessoa.setCpf("99988877766");
        pessoa.setEhExcluido(excluido);
        pessoa.setIdade(32L);
        return  pessoa;
    }

    private List<Pessoa> dadoUmaListaDePessoas(){
        List<Pessoa> list = new ArrayList<>();
        list.add(new Pessoa());
        return list;
    }

}
