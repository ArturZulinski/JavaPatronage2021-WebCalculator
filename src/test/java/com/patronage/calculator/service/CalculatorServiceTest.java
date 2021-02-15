package com.patronage.calculator.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CalculatorServiceTest {

    @Autowired
    private CalculatorService calculatorService;

    @Test
    void shouldAddTwoNumbers() {
        double result = calculatorService.sum(2, 2);
        assertThat(result).isEqualTo(4);
    }

    @Test
    void shouldMultiplyNumbrAndVector() {
        double[] inputVector = new double[]{1.0 ,2.0};
        ResponseEntity<Vector<Double>> result = calculatorService.mul(2.0, inputVector);

        Vector<Double> outputVector = new Vector<>(2);
        outputVector.add(2.0);
        outputVector.add(4.0);

        assertThat(result.getBody()).isEqualTo(outputVector);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldNotMultiplyNumberAndVector() {
        double[] inputVector = new double[]{1.0 ,3.0,5.0, 4.0, 8.0};
        ResponseEntity<Vector<Double>> result = calculatorService.mul(2.0, inputVector);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}