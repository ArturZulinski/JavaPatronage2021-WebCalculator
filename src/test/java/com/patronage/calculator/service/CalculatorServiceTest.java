package com.patronage.calculator.service;

import com.patronage.calculator.exception.ApiException;

import com.patronage.calculator.exception.ApiExceptionHandler;
import com.patronage.calculator.exception.ApiRequestsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test()
    public void shouldNotDivideByZero(){
        Exception exception = assertThrows(ApiRequestsException.class, () -> {
            calculatorService.divideTwoNumbers(4.0,0.0);
        });

        String expectedMessage = "You cannot divide by 0 !!!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
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
        Exception exception = assertThrows(ApiRequestsException.class, () -> {
            calculatorService.rootNumber(9.0,0.0);
        });

        String expectedMessage = "You cannot root by 0 !!!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
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
        Exception exception = assertThrows(ApiRequestsException.class, () -> {
        double[] inputVector = new double[]{1.0 ,3.0,5.0, 4.0, 8.0};
        calculatorService.multiplyNumberAndVector(2.0, inputVector);
        });

        String expectedMessage = "The value of Vector is to big!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
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
        Exception exception = assertThrows(ApiRequestsException.class, () -> {
        double[] inputVector1 = new double[]{1.0, 2.0, 3.0, 4.0, 5.0};
        double[] inputVector2 = new double[]{3.0, 2.0, 1.0};
        calculatorService.addingTwoVectors(inputVector1, inputVector2);
        });

        String expectedMessage = "The Vectors are not the same size!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}