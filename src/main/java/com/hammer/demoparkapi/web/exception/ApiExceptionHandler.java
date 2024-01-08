package com.hammer.demoparkapi.web.exception;

import com.hammer.demoparkapi.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class) // exceção para acesse negado
    public ResponseEntity<ErrorMessage> AccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    @ExceptionHandler({UsernameUniqueViolationException.class, CpfUniqueViolationException.class, CodigoUniqueViolationException.class}) // -> exceção para usuario unico
    public ResponseEntity<ErrorMessage>  uniqueViolationException(RuntimeException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class) // -> exceção para busca por id.
    public ResponseEntity<ErrorMessage> entityNotFoundException (RuntimeException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(PasswordInvalidException.class) // -> exceção para senha
    public ResponseEntity<ErrorMessage> passwordInvalidException(RuntimeException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // -> exceção para campos inválidos
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request, BindingResult result) {
        log.error("Api Error - ", ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) inválido(s)", result));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> internalServerErrorException(Exception ex, HttpServletRequest request) {
        ErrorMessage error = new ErrorMessage(
                request, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        log.error("Internal Server Error {} {} ", error, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
}

/*
Explicação para exceção de usuario unico

Onde temos a exceção como argumento, a gente pode colocar tanto UsernameUniqueViolationException quanto RuntimeException.
Por quê?
Porque a nossa exceção é uma Runtime Exception.
Assim, se você tiver mais de uma exceção do tipo e Unique, você vai cadastrando e vai sempre capturando
as mensagens a partir do antigo Exception.
Na parte do ResponseEntity quando temos repetição de valores que não são aceitos na aplicação como campos únicos
pode colocar o status 409, que no caso é o Conflict.
Dessa forma está indicando ao cliente que houve um conflito de informações.
na parte de mensagem vamos colocar a própria mensagem que criamos.
Então é ex.getMessage.

A anotação ExceptionHandler aceita um array de classes, colocando chaves {} -> @ExceptionHandler({UsernameUniqueViolationException.class, CpfUniqueViolationException.class})
adicionando a segunda classe que é CpfUniqueViolationException.class
Dessa forma, quando for qualquer uma dessas duas classes que tiveram uma exceção lançada e capturado
por esse método aqui.
*/

/*
Explicação para exceção de busca por id.

Mesma logica do que a de usuario unico porem em HttpStatus usaremos HttpStatus.NOT_FOUND.
*/

/*
Explicação para exceção de campos inválidos

Vamos colocar a própria variável referente à exceção que estamos capturando.
Porque assim, caso você queira capturar a mensagem dessa exceção, você pode.
Vamos adicionar uma variável do tipo HttpServletRequest e chamar ela de request,
E vamos adicionar o binding result BindingResult result
A gente precisa porque as informações que a gente vai enviar lá para error message estão contidas nessas variáveis.
Agora, no corpo do método, vamos colocar a instrução de retorno.
Vamos incluir aqui ResponseEntity.status
Agora, aqui no status a gente define qual o status que vai ser enviado como resposta na requisição.
Então vamos pegar aqui o http status, poderia seguir o mesmo caminho do Spring, retornando um bad request.
Porém nós vamos utilizar um outro valor que é o 422.
Que é esse aqui o UNPROCESSABLE_ENTITY.
é um erro quando a aplicação não consegue, por algum motivo, processar o objeto que foi enviado pelo cliente.
vamos colocar um contentType só para forçar a resposta, APPLICATION_JSON.
Então MediaType application JSON.
E agora vamos colocar o corpo da resposta.
Vamos acessar o método Construtor Error Message, no qual a gente tem a variável result.
Então, o primeiro parâmetro, se não me engano, é o request.
O segundo parâmetro era o HttpStatus.
O HttpStatus.UNPROCESSABLE_ENTITY.
Agora a gente tem que colocar a mensagem, que é a mensagem padrão do erro.
A gente poderia pegar a mensagem da exceção.
Utilizando aqui o getMessage, mas eu adicionai aqui uma mensagem própria.
*/

/*
@RestControllerAdvice -> essa notação do tipo advice.Ela funciona como uma espécie de ouvinte,
então a gente vai registrar dentro da classe exceções,
e quando essas exceções forem lançadas, essa classe vai capturar essas exceções e fazer o tratamento
que nós vamos indicar nos métodos que vamos adicionar aqui na classe

@Slf4j -> anotação do Lombok.
E a gente pode imprimir aqui com o método Error, tem que colocar uma string de sua preferência (eu coloquei "Api Error - ")
E depois passa o ex.
Por que eu estou fazendo isso?
Porque às vezes você tem uma exceção tratada e, dependendo da mensagem, você às vezes tem dificuldade
dentro da aplicação de visualizar onde o erro estourou exatamente.
Então, logando essa exceção no console, a gente consegue ver exatamente onde a exceção estourou
*/
