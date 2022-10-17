package com.ayrton.crud.service;

import com.ayrton.crud.model.Pessoa;

import java.util.List;

public interface PessoaService {

    Pessoa buscarPorId(Long id);
    List<Pessoa> buscarTodasPessoas();
    List<Pessoa> buscarPorNome(String nome);
    Pessoa buscarPorIdade(Long limiteInferior,Long limiteSuperior);
    Pessoa buscarPorCpf(String cpf);
    void cadastrarPessoa(Pessoa pessoa);
    void editarPessoa(Long id, Pessoa pessoa);
    void excluirPessoa(Long id);
}
