package com.ayrton.pessoacrud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PESSOA",uniqueConstraints = @UniqueConstraint(columnNames = "CPF"))
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "CPF")
    private String cpf;
    @Column(name = "IDADE")
    private Long idade;
    @Column(name = "EXCLUIDO")
    private Boolean ehExcluido;

}
