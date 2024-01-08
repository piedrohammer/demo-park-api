package com.hammer.demoparkapi.web.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioCreateDTO {

    @NotBlank
    @Email(message = "formato de email inválido!", regexp = "[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String username;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
}

/*
@Email -> Ele recebe um e-mail e esse valor de e-mail precisa ser um e-mail com um formato válido.
Ex: email@email.com

@NotBlank -> O elemento anotado, que o campo, não deve ser nulo
e deve conter pelo menos 1 caractere que não seja um espaço em branco.

@Size -> Tamanho minimo e maximo de caractere para a senha.

regexp -> expressão regular Basicamente ela diz que antes do arroba deve ter uma quantidade ilimitada de letras ou números,
ou ponto ou sinal de adição ou sinal de subtração, que seria um hífen, ok.
Se você quiser que tenha um sustenido e se você quiser que tenha um sinal de porcentagem vc pode colocar [a-z0-9.+-].
Depois do arroba, ela informa que vai ter que ter ou letras, ou números, ou ponto ou um traço.
Então todos esses caracteres, se tiver depois do arroba, vão ser aceitos e depois desses caracteres obrigatoriamente tem que ter um ponto.
Então aqui é onde você vai testar o ponto com.br, ponto A e esse tipo de extensão. \\[a-z]{2,}$
Então depois do ponto, aí você vai ter que ter apenas letras e vai ter que ter no mínimo duas.
A quantidade pode ser qualquer uma, desde que o mínimo seja duas.
*/