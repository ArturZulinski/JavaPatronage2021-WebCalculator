package com.patronage.calculator.controler;

import com.patronage.calculator.service.CalculatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Vector;

@RestController
@RequestMapping("/calculator")
@Api("This application let User to do simple calculations like addition, subtraction and others, on real numbers, vectors and " +
        "matrices depending on the configurations of the two.")
public class CalculatorControler {

    @Autowired
    private CalculatorService calculatorService;

    @PostMapping("/operations/sum/number-number")
    @ApiOperation("Addition of 2 real numbers")
    public int sum(@RequestParam int firstNumber, @RequestParam int secondNumber) {
        return calculatorService.sum(firstNumber, secondNumber);
    }

    @PostMapping("/operations/sub/number-number")
    @ApiOperation("Subtraction of 2 Real Numbers")
    public int sub(@RequestParam int firstNumber, @RequestParam int secondNumber) {
        return (firstNumber - secondNumber);
    }

    @PostMapping("/operations/mul/number-number")
    @ApiOperation("Multiplication of 2 Real Numbers")
    public int mul(@RequestParam int firstNumber, @RequestParam int secondNumber) {
        return (firstNumber * secondNumber);
    }

    @PostMapping("/operations/div/number-number")
    @ApiOperation("Division of 2 Real Numbers")
    public int div(@RequestParam int firstNumber, @RequestParam int secondNumber) {
        return (firstNumber / secondNumber);
    }

    @PostMapping("/operations/exp/number-number")
    @ApiOperation("Exponentiation of 2 Real Numbers")
    public double exp(@RequestParam double firstNumber, @RequestParam double secondNumber) {
        return (Math.pow(firstNumber, secondNumber));
    }

    @PostMapping(value = "/operations/root/number-number", produces = {"application/json"})
    @ApiOperation("Root of 2 Real Numbers")
    public double root(@RequestParam double firstNumber, @RequestParam double secondNumber) {
        return (Math.pow(firstNumber, (1 / secondNumber)));
    }

    @PostMapping(value = "/operations/mul/number-vector", produces = {"application/json"})
    @ApiOperation(value = "Multiplication of vector and number", notes = "bjbkhjajdghgdhsfhds")
    public ResponseEntity<Vector<Double>> mul(@RequestParam double number, @RequestParam double vector[]) {
        return calculatorService.mul(number, vector);
    }

//    @PostMapping("/operations/mul/number-matrix")
//    @ApiOperation("Multiplication of matrix and number")
//    public Array<Double> mul(@RequestParam double firstNumber, @RequestParam double secondMatrix[][]){
//        Vector<Double> result = new Vector<Double>();
//        if(secondMatrix.length < 5){
//            for (int i = 0; i < secondMatrix.length; i++) {
//                for(int j=0; j< secondMatrix[0].length;j++){
//                        result.addElement(firstNumber*secondMatrix[i][j]);
//                }
//
//            }
//        }
//        else{
//            throw new RuntimeException("The value of Vector is to big!");
//        }
//        return result[][];
//    }

    @PostMapping(value = "/operations/test", produces = {"application/json"})
    @ApiOperation("TEST")
    public Integer[][] testing(Integer[][] arr) {
        System.out.println("array test" + Arrays.toString(arr));
        return arr;
//        Integer[][] newArr=new Integer[arr.length][arr[0].length];
//        for(int i=0;i<arr.length;i++)
//        {
//            for(int j=0;j<arr[0].length;j++)
//            {
//                newArr[i][j]=arr[i][arr[0].length-1-j];
//            }
//
//        }
//        return newArr; // rerunning the array witch created inside this method.
//    }

        //@DeleteMapping
    }

    @GetMapping("/operations/instruction/")
    public ResponseEntity downloadFileFromLocal() {
        Path path = Paths.get("src\\main\\resources\\operations.txt");
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
