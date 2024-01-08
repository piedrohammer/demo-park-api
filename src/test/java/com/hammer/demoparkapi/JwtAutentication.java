package com.hammer.demoparkapi;

import com.hammer.demoparkapi.jwt.JwtToken;
import com.hammer.demoparkapi.web.dtos.UsuarioLoginDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

public class JwtAutentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password) {
        String token = client
                .post()
                .uri("/api/v1/auth")
                .bodyValue(new UsuarioLoginDTO(username, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody().getToken();
        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}

/*
Nessa classe vamos incluir um método public static.
Eu vou usar como retorno desse método um objeto do tipo Consumer.
Que é do pacote Java útil function.
No Consumer vamos ter um valor genérico que vai ser uma classe nomeada como HttpHeaders.
Essa classe é do pacote do Spring,
Vamos nomear o método como getHeaderAuthorization
O primeiro argumento vai ser o nosso objeto WebTestClient.
Vamos ter também uma String para username, e uma String para password.
Agora no corpo do método, vamos adicionar uma variável de string chamada Token e a partir da variável
client vamos começar a criar a operação para autenticação.
Então vamos ter uma autenticação que utiliza um método do tipo post.
Vamos adicionar ao uri "/api/v1/auth"
Agora vamos adicionar o corpo da requisição.
O corpo da requisição vai precisar conter o username e password,
então vamos utilizar o nosso objeto de login que é UsuarioLoginDTO.
Vamos passar aqui o username e o password
Agora utilizar o método exchange, e usar o .expectStatus().isOk()
Vamos ter o expectBody e vai ter que nos retornar um JwtToken.class
E agora em .returnResult() acessa o método getResponseBody(), ele tem no corpo dele um objeto token.
por fim o getToken();
a instrução de retorno vai utilizar uma expressão lambida como headers
acessar o método headers.add
Esse método vai receber o nome do cabeçalho que vamos adicionar na resposta.
Que é o (HttpHeaders.AUTHORIZATION, "Bearer " + token);
*/
