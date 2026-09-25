package br.com.estechti.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.estechti.exception.ExceptionResponse;
import br.com.estechti.exception.ResourceNotFoundExceptionException;

/* essas duas anotações trabalham juntas para criar um interceptador global de 
exceções da API REST: 

@ControllerAdvice -> É uma anotação que marca uma classe como um interceptador global de 
exceções para controladores Spring MVC (incluindo REST Controllers). 
Ela "escuta" exceções que acontecem em qualquer Controller da aplicação.

@RestControllerAdvice -> É uma especialização do @ControllerAdvice que indica 
que essa classe não apenas intercepta exceções, mas também retorna dados em formatos 
como JSON ou XML (típico de APIs REST). Na prática, quando uma exceção é capturada por 
um método anotado com @ExceptionHandler dentro de um @RestControllerAdvice, 
o método pode retornar um objeto (como o seu ExceptionResponse) que será convertido 
automaticamente para o formato de resposta da API (geralmente JSON).
*/
@ControllerAdvice
@RestController
public class CustomEntityResponseHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    // O <ExceptionResponse> (Generics) define o tipo do corpo (body) da resposta HTTP que será serializado em JSON
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundExceptionException.class)
    // O <ExceptionResponse> (Generics) define o tipo do corpo (body) da resposta HTTP que será serializado em JSON
    public final ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundExceptionException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
