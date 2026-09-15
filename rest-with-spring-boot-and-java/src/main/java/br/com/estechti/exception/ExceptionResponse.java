package br.com.estechti.exception;

import java.util.Date;

/* O que é um record no Java?
Introduzido no Java 14+ (finalizado no Java 16), um record é uma classe especial 
focada no transporte de dados imutáveis (DTOs).

Ao declarar um record, o Java gera automaticamente por baixo dos panos:

Campos private final para cada parâmetro.
Construtor público canônico.
Métodos de leitura com o mesmo nome do atributo (ex: timestamp(), message()).
Métodos equals(), hashCode() e toString(). */
public record ExceptionResponse(Date timestamp, String message, String details) {
}
