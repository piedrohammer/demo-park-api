package com.hammer.demoparkapi.web.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VagaCreateDTO {
    @NotBlank
    @Size(min = 4, max = 4)
    private String codigo;
    @NotBlank
    @Pattern(regexp = "LIVRE|OCUPADA")
    private String status;
}

/*
a notação @Pattern pode utilizar uma expressão regular para definir os valores que vai aceitar na requisição.
Então vai ser livre ou ocupada.
Se vier qualquer valor que seja diferente de livre ou ocupada, a anotação vai lançar uma exceção,
* */