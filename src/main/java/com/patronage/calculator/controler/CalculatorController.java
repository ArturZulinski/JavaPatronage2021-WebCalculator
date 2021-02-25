package com.patronage.calculator.controler;

import com.patronage.calculator.service.CalculatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

@RestController
@RequestMapping("/calculator")
@Api("This application let User to do simple calculations like addition, subtraction and others, on real numbers, vectors and " +
        "matrices depending on the configurations of the two.")
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @PostMapping("/operations/add/number-number")
    @ApiOperation("Addition of 2 real numbers")
    public double addingTwoNumbers(@RequestParam double firstNumber,
                                   @RequestParam double secondNumber){
        return calculatorService.addingTwoNumbers(firstNumber, secondNumber);
    }

    @PostMapping("/operations/subtract/number-number")
    @ApiOperation("Subtraction of 2 Real Numbers")
    public double subtractTwoNumbers(@RequestParam double firstNumber,
                                     @RequestParam double secondNumber){
        return calculatorService.subtractTwoNumbers(firstNumber,secondNumber);
    }

    @PostMapping("/operations/multiply/number-number")
    @ApiOperation("Multiplication of 2 Real Numbers")
    public double multiplyTwoNumbers(@RequestParam double firstNumber,
                                     @RequestParam double secondNumber){
        return calculatorService.multiplyTwoNumbers(firstNumber,secondNumber);
    }

    @PostMapping("/operations/divide/number-number")
    @ApiOperation("Division of 2 Real Numbers")
    public ResponseEntity<Double> divideTwoNumbers(@RequestParam double firstNumber,
                                                   @RequestParam double secondNumber){
        return calculatorService.divideTwoNumbers(firstNumber,secondNumber);
    }

    @PostMapping("/operations/exponentiation/number-number")
    @ApiOperation("Exponentiation of 2 Real Numbers")
    public double exponentiationNumber(@RequestParam double firstNumber,
                                       @RequestParam double secondNumber){
        return calculatorService.exponentiationNumber(firstNumber,secondNumber);
    }

    @PostMapping(value = "/operations/root/number-number", produces = {"application/json"})
    @ApiOperation("Root of 2 Real Numbers")
    public ResponseEntity<Double> rootNumber(@RequestParam double firstNumber,
                                             @RequestParam double secondNumber){
        return calculatorService.rootNumber(firstNumber,secondNumber);
    }

    @PostMapping(value = "/operations/multiply/number-vector", produces = {"application/json"})
    @ApiOperation(value = "Multiplication of vector and number", notes = "This operation take 1 Real Number and 1 Vector " +
            "and multiply them together") // adding some extra notes
    public ResponseEntity<double[]> multiplyNumberAndVector(@RequestParam double number,
                                                            @RequestParam double vector[]){
        return calculatorService.multiplyNumberAndVector(number, vector);
    }

    @PostMapping("/operations/multiply/number-matrix")
    @ApiOperation("Multiplication of matrix and number")
    public ResponseEntity<double[][]> multiplyMatrixByNumber(@RequestParam double number,
                                                             @RequestBody double[][] matrix){
        return calculatorService.multiplyMatrixByNumber(number,matrix);
    }

    @PostMapping("/operations/add/matrix-matrix")
    @ApiOperation("Adding 2 matrices")
    public ResponseEntity<double[][]> addingTwoMatrices(@RequestBody double[][] firstMatrix,
                                                        @RequestBody double[][] secondMatrix){
        return calculatorService.addingTwoMatrices(firstMatrix,secondMatrix);
    }

    @PostMapping("/operations/subtract/matrix-matrix")
    @ApiOperation("Subtraction of 2 matrices")
    public ResponseEntity<double[][]> subtractTwoMatrices(@RequestBody double firstMatrix[][],
                                                          @RequestBody double secondMatrix[][]){
        return calculatorService.subtractTwoMatrices(firstMatrix,secondMatrix);
    }
    @PostMapping("/operations/add/vector-vector")
    @ApiOperation("Adding 2 vectors together")
    public ResponseEntity<double[]> addingTwoVectors(@RequestParam double firstVector[],
                                                     @RequestParam double secondVector[]) throws IOException {
        return calculatorService.addingTwoVectors(firstVector,secondVector);
    }

    @PostMapping("/operations/subtract/vector-vector")
    @ApiOperation("Subtraction of 2 vectors")
    public ResponseEntity<double[]> subtractTwoVectors(@RequestParam double firstVector[],
                                                       @RequestParam double secondVector[]){
        return calculatorService.subtractTwoVectors(firstVector,secondVector);
    }

    @PostMapping("/operations/multiply/matrix-matrix")
    @ApiOperation("Multiply 2 matrices")
    public ResponseEntity<double[][]> multiplyMatrices(@RequestBody double[][] firstMatrix,
                                                       @RequestBody double[][] secondMatrix){
        return calculatorService.multiplyMatrices(firstMatrix,secondMatrix);
    }

    @PostMapping("/operations/multiply/matrix-vector")
    @ApiOperation("Multiply matrix and vector")
    public ResponseEntity<double[][]> multiplyMatrices(@RequestBody double[][] matrix,
                                                       @RequestBody double[] vector){
        return calculatorService.multiplyMatrixByVector(matrix,vector);
    }

    @GetMapping("/operations/instruction/")
    @ApiOperation("Downloading file 'operations.txt' containing all possible operations with given variables")
    public ResponseEntity downloadFileFromLocal() {
        return calculatorService.downloadFileFromLocal();
    }

}

