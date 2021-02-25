package com.patronage.calculator.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CalculatorServiceTest {

    // Adding 1 positive and 1 negative Test for every method. 0 division also and sizes of vectors/matrices

    @Autowired
    private CalculatorService calculatorService;

    @Test
    void shouldAddTwoNumbers() {
        double result = calculatorService.addingTwoNumbers(2, 2);
        assertThat(result).isEqualTo(4);
    }

    @Test
    void shouldNotAddTwoNumbers(){
        double result = calculatorService.addingTwoNumbers(2,2);
        assertThat(result).isNotEqualTo(5);
    }

    @Test
    void shouldSubtractTwoNumbers(){
        double result = calculatorService.subtractTwoNumbers(6,4);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void shouldNotSubtractTwoNumbers(){
        double result = calculatorService.subtractTwoNumbers(6,4);
        assertThat(result).isNotEqualTo(8);
    }

    @Test
    void shouldMultiplyTwoNumbers(){
        double result = calculatorService.multiplyTwoNumbers(2,4);
        assertThat(result).isEqualTo(8);
    }

    @Test
    void shouldNotMultiplyTwoNumbers(){
        double result = calculatorService.multiplyTwoNumbers(2,4);
        assertThat(result).isNotEqualTo(7);
    }

    @Test
    void shouldDivideTwoNumbers(){
        ResponseEntity result = calculatorService.divideTwoNumbers(4.0,2.0);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2.0);
    }

    @Test
    void shouldNotDivideTwoNumbers(){
        ResponseEntity result = calculatorService.divideTwoNumbers(4.0,2.0);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotEqualTo(3.0);
    }

    @Test
    void shouldNotDivideByZero(){
        ResponseEntity result = calculatorService.divideTwoNumbers(4.0,0.0);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldPowerTwoNumbers(){
        double result = calculatorService.exponentiationNumber(2,5);
        assertThat(result).isEqualTo(32);
    }

    @Test
    void shouldNotPowerTwoNumbers(){
        double result = calculatorService.exponentiationNumber(2,5);
        assertThat(result).isNotEqualTo(2);
    }

    @Test
    void shouldRootTwoNumbers(){
        ResponseEntity result = calculatorService.rootNumber(9.0,2.0);
        assertThat(result.getBody()).isEqualTo(3.0);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldNotRootTwoNumbers(){
        ResponseEntity result = calculatorService.rootNumber(9.0,2.0);
        assertThat(result.getBody()).isNotEqualTo(8.0);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldNotRootByZero(){
        ResponseEntity result = calculatorService.rootNumber(9.0,0.0);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldMultiplyNumberAndVector() {
        double[] inputVector = new double[]{1.0 ,2.0};
        ResponseEntity<double[]> result = calculatorService.multiplyNumberAndVector(2.0, inputVector);

        double[] outputVector = {2.0, 4.0};

        assertThat(result.getBody()).isEqualTo(outputVector);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldNotMultiplyNumberAndVector() {
        double[] inputVector = new double[]{1.0 ,2.0};
        ResponseEntity<double[]> result = calculatorService.multiplyNumberAndVector(2.0, inputVector);

        double[] outputVector = {6.0, 6.0};

        assertThat(result.getBody()).isNotEqualTo(outputVector);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void wrongSizeOfVector() {
        double[] inputVector = new double[]{1.0 ,3.0,5.0, 4.0, 8.0};
        ResponseEntity<double[]> result = calculatorService.multiplyNumberAndVector(2.0, inputVector);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldAddTwoVectors() throws IOException {
        double[] inputVector1 = new double[]{1.0, 2.0, 3.0};
        double[] inputVector2 = new double[]{3.0, 2.0, 1.0};
        ResponseEntity<double[]> result = calculatorService.addingTwoVectors(inputVector1,inputVector2);

        double[] outputVector = {4.0 , 4.0 , 4.0};

        assertThat(result.getBody()).isEqualTo(outputVector);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void shouldNotAddTwoVectors() throws IOException {
        double[] inputVector1 = new double[]{1.0, 2.0, 3.0};
        double[] inputVector2 = new double[]{3.0, 2.0, 1.0};
        ResponseEntity<double[]> result = calculatorService.addingTwoVectors(inputVector1,inputVector2);

        double[] outputVector = {5.0, 5.0, 5.0};

        assertThat(result.getBody()).isNotEqualTo(outputVector);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void wrongSizeOfOneOfTheVectors() throws IOException {
        double[] inputVector1 = new double[]{1.0, 2.0, 3.0, 4.0, 5.0};
        double[] inputVector2 = new double[]{3.0, 2.0, 1.0};
        ResponseEntity<double[]> result = calculatorService.addingTwoVectors(inputVector1, inputVector2);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}