package com.ayrton.pessoacrud.service;

import com.ayrton.pessoacrud.model.Pessoa;

import java.util.List;

public interface PessoaService {

    Pessoa buscarPorId(Long id);
    List<Pessoa> buscarTodasPessoas(); //
    List<Pessoa>  buscarPorIdade(Long limiteInferior,Long limiteSuperior);
    Pessoa buscarPorCpf(String cpf); //
    void cadastrarPessoa(Pessoa pessoa); //
    void editarPessoa(Long id, Pessoa pessoa); //
    void excluirPessoa(Long id); //
}
