package com.br.estech.controllers;

import com.br.estech.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!"; // Template for greeting message
    private AtomicLong counter = new AtomicLong();

    /**
     * Método que responde ao endpoint /greeting.
     * Recebe um parâmetro 'name' via requisição HTTP (padrão "World" se não
     * informado).
     * Retorna um objeto Greeting contendo um id incremental e uma mensagem
     * personalizada.
     * Exemplo de resposta: {"id":1, "content":"Hello, Fulano!"}
     */
    @RequestMapping("/greeting") // Endpoint para saudação
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
