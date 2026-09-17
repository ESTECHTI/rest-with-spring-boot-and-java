package br.com.estechti.services;

import org.springframework.stereotype.Service;

import br.com.estechti.exception.UnsupportedMathOperationException;

@Service 
public class MathService {
   
    public Double sum(Double n1, Double n2) { return n1 + n2; }
    public Double subtraction(Double n1, Double n2) { return n1 - n2; }
    public Double multiplication(Double n1, Double n2) { return n1 * n2;}
    public Double division(Double n1, Double n2) {
        if (n2 == 0) throw new UnsupportedMathOperationException("Divison by zero is not allowed!");
        return n1 / n2;
    }
    public Double mean(Double n1, Double n2) { return (n1 + n2) / 2.0; }
    public Double squareRoot(Double n) {
        if (n < 0) throw new UnsupportedMathOperationException("Cannot calculate square root of a negative number!");
        return Math.sqrt(n);
    }
}
