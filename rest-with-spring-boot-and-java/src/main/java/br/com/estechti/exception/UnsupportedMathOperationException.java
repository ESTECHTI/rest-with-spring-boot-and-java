package br.com.estechti.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// @ResponseStatus: Define o código HTTP que o Spring deve retornar caso essa exceção
// seja lançada e não interceptada por um handler específico (400 Bad Request = requisição inválida do cliente)
@ResponseStatus(HttpStatus.BAD_REQUEST)
// extends RuntimeException: Torna esta classe uma exceção não-checada (unchecked exception).
// Não exige try-catch obrigatório nem declaração 'throws' na assinatura dos métodos.
public class UnsupportedMathOperationException extends RuntimeException {

	// serialVersionUID: Identificador de versão da classe para serialização em Java.
	// Como Throwable implementa Serializable, é uma boa prática definir para evitar inconsistências.
	private static final long serialVersionUID = 1L;

	// Construtor: Recebe a mensagem de erro (ex: "Please set a numeric value!")
	// e a repassa para o construtor da classe-mãe (super/RuntimeException).
	public UnsupportedMathOperationException(String message) {
		super(message);
	}

}
