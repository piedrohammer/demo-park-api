package com.hammer.demoparkapi.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ClienteCreateDTO {

    @NotBlank
    @Size(min = 5, max = 100)
    private String nome;

    @Size(min = 11, max = 11)
    @CPF
    private String cpf;
}

/*
A anotação @CPF ela é do pacote do Hibernate, então lá no Hibernate existe a anotação CPF para validação
de CPF, como também existe a anotação para CNPJ.
Ela valida de duas formas o CPF.
Ela vai validar o CPF com 11 dígitos, ou seja, sem a pontuação e sem o traço, ou ela vai validar
com 14 dígitos, que é quando você utiliza a pontuação e o traço.
No caso, nós vamos limitar a nossa API para aceitar apenas sem a pontuação.
* */