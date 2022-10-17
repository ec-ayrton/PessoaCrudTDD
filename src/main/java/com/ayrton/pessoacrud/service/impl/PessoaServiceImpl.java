package com.ayrton.pessoacrud.service.Impl;

import com.ayrton.pessoacrud.model.Pessoa;
import com.ayrton.pessoacrud.repository.PessoaRepository;
import com.ayrton.pessoacrud.service.PessoaService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
public class PessoaServiceImpl implements PessoaService {

    public static final String RECURSO_NAO_ENCONTRADO = "Recurso não encontrado.";
    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public Pessoa buscarPorId(Long id) {
        return pessoaRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(RECURSO_NAO_ENCONTRADO));
    }

    @Override
    public List<Pessoa> buscarTodasPessoas() {
        return pessoaRepository.findAll();
    }

    @Override
    public List<Pessoa> buscarPorIdade(Long limiteInferior, Long limiteSuperior) {
        return pessoaRepository.findByIdadeBetween(limiteInferior,limiteSuperior);
    }

    @Override
    public Pessoa buscarPorCpf(String cpf) {
        return pessoaRepository.findByCpf(cpf).orElseThrow(()-> new EntityNotFoundException(RECURSO_NAO_ENCONTRADO));
    }

    @Override
    public void cadastrarPessoa(Pessoa pessoa) {
        validaCampos(pessoa);
        pessoaRepository.saveAndFlush(pessoa);
    }

    @Override
    public void editarPessoa(Long id, Pessoa pessoa) {
        Pessoa pessoaSalvaNoBanco = this.buscarPorId(id);
        validaCampos(pessoa);
        validaEdicao(pessoaSalvaNoBanco,pessoa);
        pessoaRepository.saveAndFlush(pessoa);
    }

    @Override
    public void excluirPessoa(Long id) {
        Pessoa pessoa = this.buscarPorId(id);
        pessoa.setEhExcluido(Boolean.TRUE);
        pessoaRepository.saveAndFlush(pessoa);
    }

    private void validaCampos(Pessoa pessoa) {
        StringBuilder stringBuilder = new StringBuilder();
        if(Objects.isNull(pessoa)){
            throw new UnsupportedOperationException("Objeto nulo!");
        }
        if(Objects.isNull(pessoa.getNome())){
            stringBuilder.append("Nome vazio.").append("\n");
        }
        if(Objects.isNull(pessoa.getCpf())){
            stringBuilder.append("cpf vazio.").append("\n");
        }
        if(Objects.isNull(pessoa.getIdade())){
            stringBuilder.append("idade vazia.").append("\n");
        }
        if(StringUtils.hasLength(stringBuilder))
            throw new UnsupportedOperationException(stringBuilder.toString());
    }
    private void validaEdicao(Pessoa pessoaSalvaNoBanco, Pessoa pessoa) {
        StringBuilder stringBuilder = new StringBuilder();
        if(ObjectUtils.notEqual(pessoa.getCpf(),pessoaSalvaNoBanco.getCpf())){
            stringBuilder.append("Campo cpf não pode ser editado.\n");
        }
        if(ObjectUtils.notEqual(pessoa.getEhExcluido(),pessoaSalvaNoBanco.getEhExcluido())){
            stringBuilder.append("Campo exclusao não pode ser editado.\n");
        }
        if(StringUtils.hasLength(stringBuilder))
            throw new UnsupportedOperationException(stringBuilder.toString());
    }
}
