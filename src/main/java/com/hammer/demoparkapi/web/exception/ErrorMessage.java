package com.hammer.demoparkapi.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class ErrorMessage {

    private String path;
    private String method;
    private int status;
    private String statusText;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> erros;

    public ErrorMessage() {
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
        addErros(result);
    }

    private void addErros(BindingResult result) {
        this.erros = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()){
            this.erros.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    /*
     quando a gente for trabalhar com a parte de validação, a gente tem que enviar lá para o cliente
     um objeto dentro do JSON que contenha o nome do campo que gerou o erro de validação.
     E o valor desse campo vai ser a mensagem de validação.
     Então esse MAP vai conter isso, o nome do campo como chave e o valor vai ser a mensagem do erro.
     Então, para ter acesso ao nome do campo e ao valor da mensagem, a gente vai utilizar um for.
     Vamos usar aqui o campo field de error.
     e a partir do result nós temos acesso ao método getFieldErrors
     eu acesso a variável errors.
     Uso o método put onde eu vou passar uma chave e um valor,
     a chave eu pego a partir da variável field error e do método getField
     Já a mensagem eu pego a partir de fieldError.
     Usando o método getDefaultMessage
     *
     */
}

/*
@JsonInclude(JsonInclude.Include.NON_NULL) -> Include.NON_NULL quando o objeto Error message for gerado
e for transformado em um JSON para ser incluido na resposta da requisição a Jackson, que é a biblioteca que faz essa conversão,
ela vai ver que o campo Errors está anotado e como ele é nulo, ela não vai colocar lá dentro do objeto JSON.
Por quê?
Porque está dizendo que é para incluir apenas se o campo não for nulo.
Como o campo é nulo, ela não inclui.
*/