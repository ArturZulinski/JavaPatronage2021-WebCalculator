package com.patronage.calculator.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

@SpringBootTest
class CalculatorServiceTest {

    // Adding 1 positive and 1 negative Test for every method. 0 division also and sizes of vectors/matrices

    @Autowired
    private CalculatorService calculatorService;

    @Test
    void shouldAddTwoNumbers() {
        double result = calculatorService.sum(2, 2);
        assertThat(result).isEqualTo(4);
    }

    @Test
    void shouldNotAddTwoNumbers(){
        double result = calculatorService.sum(2,2);
        assertThat(result).isNotEqualTo(5);
    }

    @Test
    void shouldSubtractTwoNumbers(){
        double result = calculatorService.sub(6,4);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void shouldNotSubtractTwoNumbers(){
        double result = calculatorService.sub(6,4);
        assertThat(result).isNotEqualTo(8);
    }

    @Test
    void shouldMultiplyTwoNumbers(){
        double result = calculatorService.mul(2,4);
        assertThat(result).isEqualTo(8);
    }

    @Test
    void shouldNotMultiplyTwoNumbers(){
        double result = calculatorService.mul(2,4);
        assertThat(result).isNotEqualTo(7);
    }

    @Test
    void shouldDivideTwoNumbers(){
        ResponseEntity result = calculatorService.div(4.0,2.0);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2.0);
    }

    @Test
    void shouldNotDivideTwoNumbers(){
        ResponseEntity result = calculatorService.div(4.0,2.0);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotEqualTo(3.0);
    }

    @Test
    void shouldNotDivideByZero(){
        ResponseEntity result = calculatorService.div(4.0,0.0);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldPowerTwoNumbers(){
        double result = calculatorService.exp(2,5);
        assertThat(result).isEqualTo(32);
    }

    @Test
    void shouldNotPowerTwoNumbers(){
        double result = calculatorService.exp(2,5);
        assertThat(result).isNotEqualTo(2);
    }

    @Test
    void shouldRootTwoNumbers(){
        ResponseEntity result = calculatorService.root(9.0,2.0);
        assertThat(result.getBody()).isEqualTo(3.0);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldNotRootTwoNumbers(){
        ResponseEntity result = calculatorService.root(9.0,2.0);
        assertThat(result.getBody()).isNotEqualTo(8.0);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldNotRootByZero(){
        ResponseEntity result = calculatorService.root(9.0,0.0);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldMultiplyNumberAndVector() {
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
        double[] inputVector = new double[]{1.0 ,2.0};
        ResponseEntity<Vector<Double>> result = calculatorService.mul(2.0, inputVector);

        Vector<Double> outputVector = new Vector<>(2);
        outputVector.add(6.0);
        outputVector.add(6.0);

        assertThat(result.getBody()).isNotEqualTo(outputVector);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void wrongSizeOfVector() {
        double[] inputVector = new double[]{1.0 ,3.0,5.0, 4.0, 8.0};
        ResponseEntity<Vector<Double>> result = calculatorService.mul(2.0, inputVector);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldAddTwoVectors(){
        double[] inputVector1 = new double[]{1.0, 2.0, 3.0};
        double[] inputVector2 = new double[]{3.0, 2.0, 1.0};
        ResponseEntity<Vector<Double>> result = calculatorService.add(inputVector1,inputVector2);

        Vector<Double> outputVector = new Vector<>(3);
        outputVector.add(4.0);
        outputVector.add(4.0);
        outputVector.add(4.0);

        assertThat(result.getBody()).isEqualTo(outputVector);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void shouldNotAddTwoVectors(){
        double[] inputVector1 = new double[]{1.0, 2.0, 3.0};
        double[] inputVector2 = new double[]{3.0, 2.0, 1.0};
        ResponseEntity<Vector<Double>> result = calculatorService.add(inputVector1,inputVector2);

        Vector<Double> outputVector = new Vector<>(3);
        outputVector.add(5.0);
        outputVector.add(5.0);
        outputVector.add(5.0);

        assertThat(result.getBody()).isNotEqualTo(outputVector);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void wrongSizeOfOneOfTheVectors() {
        double[] inputVector1 = new double[]{1.0, 2.0, 3.0, 4.0, 5.0};
        double[] inputVector2 = new double[]{3.0, 2.0, 1.0};
        ResponseEntity<Vector<Double>> result = calculatorService.add(inputVector1, inputVector2);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void bbbba(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentLine = "2021-02-19 20:40:19,551 2.0 to power 2.0 equals 4.0";
        String actualDate = currentLine.substring(0,19);
        LocalDateTime actualDateTime = LocalDateTime.parse(actualDate,formatter);
        System.out.println("actualDateTime = " + actualDateTime);
        System.out.println("actualDate = " + actualDate);
    }

}