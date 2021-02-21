package com.patronage.calculator.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

@Service
public class CalculatorService{

    private static final Logger logger = LogManager.getLogger(CalculatorService.class);
    @Value("${matrix.max.col}")
    private int matrixMaxCol;
    @Value("${matrix.max.row}")
    private int matrixMaxRow;
    @Value("${vector.max.length}")
    private int vectorMaxLength;

    @Autowired
    private HistoryInterface historyInterface;

    public double addingTwoNumbers(double firstNumber, double secondNumber) {
        double result = firstNumber + secondNumber;
        String message = String.format("Perform adding operation %.1f + %.1f = %.1f",firstNumber,secondNumber,result);
        historyInterface.saveHistory(message);      //toFile or database
        logger.info(message);                       // console
        return result;
    }

    public double subtractTwoNumbers(double firstNumber, double secondNumber){
        double result = firstNumber - secondNumber;
        String message = String.format("Performing subtraction %.1f - %.1f = %.1f",firstNumber,secondNumber,result);
        historyInterface.saveHistory(message);
        logger.info(message);
        return result;
    }

    public double multiplyTwoNumbers(double firstNumber, double secondNumber){
        double result = firstNumber * secondNumber;
        String message = String.format("Multiplying 2 numbers %.1f * %.1f = %.1f",firstNumber,secondNumber,result);
        historyInterface.saveHistory(message);
        logger.info(message);
        return result;
    }

    public ResponseEntity<Double> divideTwoNumbers(double firstNumber, double secondNumber){
        double result = firstNumber / secondNumber;
        if(secondNumber==0){
            String message = "You cannot divide by 0 !!!";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
        else {
            String message = String.format("Dividing %.1f / %.1f = %.1f", firstNumber, secondNumber, result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(result, HttpStatus.OK);
        }
    }

    public double exponentiationNumber(double firstNumber, double secondNumber){
        double result = Math.pow(firstNumber,secondNumber);
        String message = String.format("%.1f to power %.1f equals %.1f",firstNumber,secondNumber,result);
        historyInterface.saveHistory(message);
        logger.info(message);
        return result;
    }

    public ResponseEntity<Double> rootNumber(double firstNumber, double secondNumber){
        double result = Math.pow(firstNumber,(1/secondNumber));
        if(secondNumber==0){
            String message = "You cannot root by 0 !!!";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
        else{
            String message = String.format("%.1f root of %.1f equals %.1f",secondNumber,firstNumber,result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(result, HttpStatus.OK);
        }
    }

    public ResponseEntity<double[]> multiplyNumberAndVector(double number, double vector[]){
        int sizeOfVector = vector.length;
        double[] result = new double[sizeOfVector];
        if(vector.length <= vectorMaxLength){
            for (int i = 0; i < vector.length; i++) {
                    result[i] = number * vector[i];
            }
            String message = String.format("Perform multiplication operation {} * {} = {}",number,vector,result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        else{
            String message = "The value of Vector is to big!";
            historyInterface.saveHistory(message);
            logger.error(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<double[]> addingTwoVectors(double firstVector[], double secondVector[]){
        int sizeOfVector = firstVector.length;
        double[] result = new double[sizeOfVector];
        if(firstVector.length!=secondVector.length){
            String message = "The Vectors are not the same size!";
            historyInterface.saveHistory(message);
            logger.error(message);
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }
        else if(firstVector.length > vectorMaxLength){
            String message = "The first Vector is too big";
            historyInterface.saveHistory(message);
            logger.error(message);
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }
        else if(secondVector.length > vectorMaxLength){
            String message = "The second Vector is too big";
            historyInterface.saveHistory(message);
            logger.error(message);
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }
        else{
            for(int i=0;i<firstVector.length; i++){
                result[i] = firstVector[i] + secondVector[i];
            }
            String message = String.format("Perform multiplication operation {} * {} = {}",firstVector,secondVector,result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
    }

    public ResponseEntity<double[]> subtractTwoVectors(double firstVector[], double secondVector[]){
        int sizeOfVector = firstVector.length;
        double[] result = new double[sizeOfVector];
        if(firstVector.length!=secondVector.length){
            String message = "The Vectors are not the same size!";
            historyInterface.saveHistory(message);
            logger.error(message);
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }
        else if(firstVector.length > vectorMaxLength){
            String message = "The first Vector is too big";
            historyInterface.saveHistory(message);
            logger.error(message);
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }
        else if(secondVector.length > vectorMaxLength){
            String message = "The second Vector is too big";
            historyInterface.saveHistory(message);
            logger.error(message);
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }
        else{
            for(int i=0;i<firstVector.length; i++){
                result[i] = firstVector[i] - secondVector[i];
            }
            String message = String.format("Perform multiplication operation {} * {} = {}",firstVector,secondVector,result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
    }

    //Need to add Exceptions and errors
    public ResponseEntity<double[][]> multiplyMatrixByNumber(double number, double[][] matrix) {
        int sizeOfRow = matrix.length;
        int sizeOfColumn = matrix[0].length;
        double[][] result = new double[sizeOfRow][sizeOfColumn];
        if ((matrix.length <= matrixMaxRow) && (matrix[0].length <= matrixMaxCol)) {
            for (int r = 0; r < matrix.length; r++) {
                for (int c = 0; c < matrix[0].length; c++) {
                    result[r][c] = number * matrix[r][c];
                }
            }
        }
        String message = String.format("Successfully multiply number and matrix {} * {} = {}", number,matrix,result);
        historyInterface.saveHistory(message);
        logger.info(message);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    //Need to add Exceptions and errors
    public ResponseEntity<double[][]> addingTwoMatrices(double[][] firstMatrix, double[][] secondMatrix){
        int sizeOfRow = firstMatrix.length;
        int sizeOfColumn = firstMatrix[0].length;
        double[][] result = new double[sizeOfRow][sizeOfColumn];
        if ((firstMatrix.length <= matrixMaxRow) && (firstMatrix[0].length <= matrixMaxCol) &&
                (firstMatrix.length == secondMatrix.length) && (firstMatrix[0].length == secondMatrix[0].length)) {
            for (int r = 0; r < firstMatrix.length; r++) {
                for (int c = 0; c < firstMatrix[0].length; c++) {
                    result[r][c] = firstMatrix[r][c] + secondMatrix[r][c];
                }
            }
        }
        String message = String.format("Successfully multiply 2 matrices {} * {} = {}", firstMatrix,secondMatrix,result);
        historyInterface.saveHistory(message);
        logger.info(message);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    //Need to add Exceptions and errors
    public ResponseEntity<double[][]> subtractTwoMatrices(double firstMatrix[][], double secondMatrix[][]){
        int sizeOfRow = firstMatrix.length;
        int sizeOfColumn = firstMatrix[0].length;
        double[][] result = new double[sizeOfRow][sizeOfColumn];
        if ((firstMatrix.length <= matrixMaxRow) && (firstMatrix[0].length <= matrixMaxCol) &&
                (firstMatrix.length == secondMatrix.length) && (firstMatrix[0].length == secondMatrix[0].length)) {
            for (int r = 0; r < firstMatrix.length; r++) {
                for (int c = 0; c < firstMatrix[0].length; c++) {
                    result[r][c] = firstMatrix[r][c] - secondMatrix[r][c];
                }
            }
        }
        String message = String.format("Successfully subtract 2 matrices {} * {} = {}", firstMatrix,secondMatrix,result);
        historyInterface.saveHistory(message);
        logger.info(message);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    //Need to add Exceptions and errors
    public ResponseEntity<double[][]> multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        int sizeOfFirstMatrixRow = firstMatrix.length;
        int sizeOfFirstMatrixColumn = firstMatrix[0].length;
        int sizeOfSecondMatrixColumn = secondMatrix[0].length;
        double[][] result = new double[sizeOfFirstMatrixRow][sizeOfSecondMatrixColumn];
        for(int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < secondMatrix[0].length; j++) {
                for (int k = 0; k < firstMatrix.length; k++) {
                    result[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }
        String message = String.format("Successfully mutiply 2 matrices {} * {} = {}", firstMatrix,secondMatrix,result);
        historyInterface.saveHistory(message);
        logger.info(message);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    //Need to add Exceptions and errors
    public ResponseEntity<double[][]> multiplyMatrixByVector(double[][] matrix, double[] vector) {
        int sizeOfFirstMatrixRow = matrix.length;
        int sizeOfFirstMatrixColumn = matrix[0].length;
        int sizeOfSecondMatrixColumn = vector.length;
        double[][] result = new double[sizeOfFirstMatrixRow][sizeOfSecondMatrixColumn];
        for(int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < vector.length; j++) {
                for (int k = 0; k < matrix.length; k++) {
                    result[i][j] += matrix[i][k] * vector[j];
                }
            }
        }
        String message = String.format("Successfully mutiply matrix by Vector {} * {} = {}", matrix,vector,result);
        historyInterface.saveHistory(message);
        logger.info(message);
        return new ResponseEntity(result,HttpStatus.OK);
    }


    public ResponseEntity downloadFileFromLocal(){
        Path path = Paths.get("src\\main\\resources\\operations.txt");
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        logger.info("Downloaded the operations.txt file");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
