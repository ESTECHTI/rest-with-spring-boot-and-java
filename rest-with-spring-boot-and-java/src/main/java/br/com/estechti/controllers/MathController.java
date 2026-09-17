package br.com.estechti.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estechti.converters.utils.NumberConverter;
import br.com.estechti.services.MathService;

@RestController
@RequestMapping("/math")
public class MathController {

    private final MathService mathService;

    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws Exception {

        return mathService.sum(
            NumberConverter.convertToDouble(numberOne),
            NumberConverter.convertToDouble(numberTwo)
        );
    }

    @RequestMapping("/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws Exception {

        return mathService.subtraction(
            NumberConverter.convertToDouble(numberOne),
            NumberConverter.convertToDouble(numberTwo)
        );
    }

    @RequestMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws Exception {

        return mathService.multiplication(
            NumberConverter.convertToDouble(numberOne),
            NumberConverter.convertToDouble(numberTwo)
        );
    }

    @RequestMapping("/division/{numberOne}/{numberTwo}")
    public Double division(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws Exception {

        return mathService.division(
            NumberConverter.convertToDouble(numberOne),
            NumberConverter.convertToDouble(numberTwo)
        );
    }

    @RequestMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws Exception {

        return mathService.mean(
            NumberConverter.convertToDouble(numberOne),
            NumberConverter.convertToDouble(numberTwo)
        );
    }
    
    @RequestMapping("/square-root/{number}")
    public Double squareRoot(
        @PathVariable("number") String number
    ) throws Exception {
        return mathService.squareRoot(NumberConverter.convertToDouble(number));
    }
}
