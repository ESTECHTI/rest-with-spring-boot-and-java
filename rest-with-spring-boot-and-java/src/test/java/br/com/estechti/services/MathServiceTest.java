package br.com.estechti.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MathServiceTest {
   
    private MathService mathService;

    @BeforeEach 
    void setUp() {
        mathService = new MathService();
    }

    @Test 
    @DisplayName("Deve somar números com valores negativos")
    void testSumPositiveNumbers() {
        Double result = mathService.sum(10.0, 5.0);
        assertEquals(15.0, result);
    }
}
