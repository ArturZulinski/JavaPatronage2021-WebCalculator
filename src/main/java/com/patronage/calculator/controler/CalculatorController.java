package com.patronage.calculator.controler;

import com.patronage.calculator.service.CalculatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Vector;

@RestController
@RequestMapping("/calculator")
@Api("This application let User to do simple calculations like addition, subtraction and others, on real numbers, vectors and " +
        "matrices depending on the configurations of the two.")
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @PostMapping("/operations/sum/number-number")
    @ApiOperation("Addition of 2 real numbers")
    public double sum(@RequestParam double firstNumber, @RequestParam double secondNumber) {
        return calculatorService.sum(firstNumber, secondNumber);
    }

    @PostMapping("/operations/sub/number-number")
    @ApiOperation("Subtraction of 2 Real Numbers")
    public double sub(@RequestParam double firstNumber, @RequestParam double secondNumber) {
        return calculatorService.sub(firstNumber,secondNumber);
    }

    @PostMapping("/operations/mul/number-number")
    @ApiOperation("Multiplication of 2 Real Numbers")
    public double mul(@RequestParam double firstNumber, @RequestParam double secondNumber) {
        return calculatorService.mul(firstNumber,secondNumber);
    }

    @PostMapping("/operations/div/number-number")
    @ApiOperation("Division of 2 Real Numbers")
    public ResponseEntity<Double> div(@RequestParam double firstNumber, @RequestParam double secondNumber) {
        return calculatorService.div(firstNumber,secondNumber);
    }

    @PostMapping("/operations/exp/number-number")
    @ApiOperation("Exponentiation of 2 Real Numbers")
    public double exp(@RequestParam double firstNumber, @RequestParam double secondNumber) {
        return calculatorService.exp(firstNumber,secondNumber);
    }

    @PostMapping(value = "/operations/root/number-number", produces = {"application/json"})
    @ApiOperation("Root of 2 Real Numbers")
    public ResponseEntity<Double> root(@RequestParam double firstNumber, @RequestParam double secondNumber) {
        return calculatorService.root(firstNumber,secondNumber);
    }

    @PostMapping(value = "/operations/mul/number-vector", produces = {"application/json"})
    @ApiOperation(value = "Multiplication of vector and number", notes = "This operation take 1 Real Number and 1 Vector " +
            "and multiply them together") // adding some extra notes
    public ResponseEntity<Vector<Double>> mul(@RequestParam double number, @RequestParam double vector[]) {
        return calculatorService.mul(number, vector);
    }

    @PostMapping("/operations/mul/number-matrix")
    @ApiOperation("Multiplication of matrix and number")
    public ResponseEntity<Vector<Vector<Double>>> mul(@RequestParam double number, @RequestParam double matrix[][]){
        return calculatorService.mul(number,matrix);
    }

    @PostMapping("/operations/sum/matrix-matrix")
    @ApiOperation("Adding 2 matrices")
    public ResponseEntity<Vector<Vector<Double>>> sum(@RequestParam double firstMatrix[][], @RequestParam double secondMatrix[][]){
        return calculatorService.sum(firstMatrix,secondMatrix);
    }

    @PostMapping("/operations/sub/matrix-matrix")
    @ApiOperation("Subtraction of 2 matrices")
    public ResponseEntity<Vector<Vector<Double>>> sub(@RequestParam double firstMatrix[][], @RequestParam double secondMatrix[][]){
        return calculatorService.sub(firstMatrix,secondMatrix);
    }
    @PostMapping("/operations/add/vector-vector")
    @ApiOperation("Adding 2 vectors together")
    public ResponseEntity<Vector<Double>> add(@RequestParam double firstVector[],@RequestParam double secondVector[]){
        return calculatorService.add(firstVector,secondVector);
    }

    @PostMapping("/operations/sub/vector-vector")
    @ApiOperation("Subtraction of 2 vectors")
    public ResponseEntity<Vector<Double>> sub(@RequestParam double firstVector[],@RequestParam double secondVector[]){
        return calculatorService.sub(firstVector,secondVector);
    }

    @GetMapping("/operations/instruction/")
    @ApiOperation("Downloading file 'operations.txt' containing all possible operations with given variables")
    public ResponseEntity downloadFileFromLocal() {
        return calculatorService.downloadFileFromLocal();
    }

}