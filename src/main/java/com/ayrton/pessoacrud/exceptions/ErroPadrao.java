package com.ayrton.pessoacrud.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class erroPadrao implements Serializable {
    private Integer status;
    private String mensagem;
}
