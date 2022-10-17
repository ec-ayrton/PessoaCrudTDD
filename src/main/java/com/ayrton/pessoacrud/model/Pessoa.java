package com.ayrton.crud.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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
    @Column(name = "DATA_DE_NASCIMENTO")
    private Date DataNascimento;
    @Column(name = "IDADE")
    private Long Idade;
    @Column(name = "EXCLUIDO")
    private Boolean ehExcluido;
}
