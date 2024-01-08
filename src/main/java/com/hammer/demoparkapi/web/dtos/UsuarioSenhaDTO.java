package com.hammer.demoparkapi.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioSenhaDTO {

    @NotBlank
    @Size(min = 6, max = 20)
    private String senhaAtual;

    @NotBlank
    @Size(min = 6, max = 20)
    private String novaSenha;

    @NotBlank
    @Size(min = 6, max = 20)
    private String confirmaSenha;
}
