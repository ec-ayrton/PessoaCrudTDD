package com.ayrton.pessoacrud.controller;

import com.ayrton.pessoacrud.model.Pessoa;
import com.ayrton.pessoacrud.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> getPessoas(){
        return pessoaService.buscarTodasPessoas();
    }

    @GetMapping("/{cpf}")
    public Pessoa getPessoaPorCPF(@PathVariable String cpf){
        return pessoaService.buscarPorCpf(cpf);
    }

}
