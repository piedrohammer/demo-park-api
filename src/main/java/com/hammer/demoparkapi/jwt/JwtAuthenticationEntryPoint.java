package com.hammer.demoparkapi.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("Http Status 401 {}", authException.getMessage());
        response.setHeader("www-authenticate", "Bearer realm='/api/v1/auth'");
        response.sendError(401);
    }
}

/*
no método Comments.
Nós vamos trabalhar nessa parte referente a resposta com o status 401.
Então eu vou adicionar a partir da variável response que nós temos como argumento do método o acesso
ao cabeçalho.
Então vamos usar o método SetHeader e ele recebe dois parâmetros.
O primeiro parâmetro é a chave, ou seja, o nome do cabeçalho e o segundo parâmetro é o valor.
Então a nossa chave vai ser "www-authenticate".
E vamos passar como valor a instrução, "Bearer realm='/api/v1/auth'"
O que significa isso?
Significa que quando o usuário não estiver autenticado la no cabeçalho teremos o cabeçalho "www-authenticate"
com a instrução Bearer informando que ele vai ter que trabalhar com um token do tipo Bearer
e esse token deve ser enviado para '/api/v1/auth'

Então a partir da variável response, vamos acessar o método.
sendError.
Neste método vamos passar o status que vai ser 401.
Eu vou adicionar aqui no topo da classe.
A notação Slf4j for Jai do Lombok. E vou colocar um log. do tipo info.
Para logar no console essa operação.
Então vou adicionar uma mensagem http status 401 {} e essas {} vão receber como valor.
A partir da variável authException que temos na assinatura do método.
A mensagem obtida a partir do método getMessage.

Agora ir até a classe de configuração do Spring.
E na classe nós vamos utilizar o método exceptionHandling
Vou criar aqui uma variável chamada ex.
E agora, a partir da variável, vamos acessar o método.
authenticationEntryPoint.
Como parâmetro desse método, vamos colocar uma instância.
Da classe que nós criamos.
Então new JwtAuthenticationEntryPoint()
Dessa forma, sempre que tiver uma exceção referente ao usuário não logado na aplicação, o Spring vai
lá na classe JWTAuthenticationEntryPoint e lança a exceção com o status 401.
* */
