package br.com.estechti.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import br.com.estechti.exception.UnsupportedMathOperationException;
import br.com.estechti.exception.handler.CustomEntityResponseHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MathController.class)
@Import(CustomEntityResponseHandler.class)
class MathControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deve retornar a soma de dois números inteiros positivos com status 200")
    void testSumPositiveIntegers() throws Exception {
        mockMvc.perform(get("/math/sum/10/25"))
                .andExpect(status().isOk())
                .andExpect(content().string("35.0"));
    }

    @Test
    @DisplayName("Deve retornar a soma com números decimais usando ponto com status 200")
    void testSumDecimalsWithDot() throws Exception {
        mockMvc.perform(get("/math/sum/10.5/2.5"))
                .andExpect(status().isOk())
                .andExpect(content().string("13.0"));
    }

    @Test
    @DisplayName("Deve retornar a soma com números decimais usando vírgula com status 200")
    void testSumDecimalsWithComma() throws Exception {
        mockMvc.perform(get("/math/sum/10,5/2,5"))
                .andExpect(status().isOk())
                .andExpect(content().string("13.0"));
    }

    @Test
    @DisplayName("Deve retornar a soma com números negativos com status 200")
    void testSumNegativeNumbers() throws Exception {
        mockMvc.perform(get("/math/sum/-10/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("-5.0"));
    }

    @Test
    @DisplayName("Deve retornar erro 500 quando o primeiro número não for numérico")
    void testSumFirstNumberNotNumeric() throws Exception {
        mockMvc.perform(get("/math/sum/abc/5"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Please set a numeric value!"))
                .andExpect(jsonPath("$.details").value("uri=/math/sum/abc/5"));
    }

    @Test
    @DisplayName("Deve retornar erro 500 quando o segundo número não for numérico")
    void testSumSecondNumberNotNumeric() throws Exception {
        mockMvc.perform(get("/math/sum/5/xyz"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Please set a numeric value!"))
                .andExpect(jsonPath("$.details").value("uri=/math/sum/5/xyz"));
    }

    @Test
    @DisplayName("Teste unitário direto: deve somar com sucesso")
    void testDirectUnitSumSuccess() throws Exception {
        MathController controller = new MathController();
        Double result = controller.sum("15.5", "4.5");
        assertEquals(20.0, result);
    }

    @Test
    @DisplayName("Teste unitário direto: deve lançar UnsupportedMathOperationException para entrada não numérica")
    void testDirectUnitSumThrowsException() {
        MathController controller = new MathController();

        UnsupportedMathOperationException exception = assertThrows(
                UnsupportedMathOperationException.class,
                () -> controller.sum("abc", "10")
        );

        assertEquals("Please set a numeric value!", exception.getMessage());
    }
}
