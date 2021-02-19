package com.patronage.calculator.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
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

    public double sum(double firstNumber, double secondNumber) {
        double result = firstNumber + secondNumber;
        String message = MessageFormat
                .format("Perform adding operation {} + {} = {}", firstNumber, secondNumber, result);
        historyInterface.saveHistory(message); //toFile or database
        logger.info(message); // console
        return result;
    }

    public double sub(double firstNumber, double secondNumber){
        double result = firstNumber - secondNumber;
        logger.info("Performing subtraction {} - {} = {}",firstNumber,secondNumber,result);
        return result;
    }

    public double mul(double firstNumber, double secondNumber){
        double result = firstNumber * secondNumber;
        logger.info("Multiplying 2 numbers {} * {} = {}",firstNumber,secondNumber,result);
        return result;
    }

    public ResponseEntity<Double> div(double firstNumber, double secondNumber){
        double result = firstNumber / secondNumber;
        if(secondNumber==0){
            logger.info("You cannot divide by 0 !!!");
            return new ResponseEntity("You cannot divide by 0 !!!", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("Dividing {} by {} gives {}", firstNumber, secondNumber, result);
            return new ResponseEntity(result, HttpStatus.OK);
        }
    }

    public double exp(double firstNumber, double secondNumber){
        double result = Math.pow(firstNumber,secondNumber);
        logger.info("{} to power {} equals {}",firstNumber,secondNumber,result);
        return result;
    }

    public ResponseEntity<Double> root(double firstNumber, double secondNumber){
        double result = Math.pow(firstNumber,(1/secondNumber));
        if(secondNumber==0){
            logger.info("You cannot root by 0 !!!");
            return new ResponseEntity("You cannot root by 0 !!!", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("{} root of {} equals {}",secondNumber,firstNumber,result);
            return new ResponseEntity(result, HttpStatus.OK);
        }
    }

    public ResponseEntity<Vector<Double>> mul(double number, double vector[]){
        Vector<Double> result = new Vector<Double>();
        if(vector.length <= vectorMaxLength){
            for (int i = 0; i < vector.length; i++) {
                    result.addElement(number * vector[i]);
            }
        }
        else{
            logger.error("The value of Vector is to big!");
            return new ResponseEntity("The value of Vector is too big!", HttpStatus.BAD_REQUEST);
        }
        logger.info("Perform mul operation {} * {} = {}",number,vector,result);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public ResponseEntity<Vector<Double>> add(double firstVector[], double secondVector[]){
        Vector<Double> result = new Vector<Double>();
        if((firstVector.length <= vectorMaxLength) && (secondVector.length <= vectorMaxLength) &&
                (firstVector.length==secondVector.length)){
            for(int i =0; i <firstVector.length; i++) {
                result.addElement(firstVector[i] + secondVector[i]);
            }
        }
        else if(firstVector.length != secondVector.length){
            logger.error("The Vectors are different sizes!");
            return new ResponseEntity("The Vectors are different sizes!", HttpStatus.BAD_REQUEST);
        }
        else if (firstVector.length > vectorMaxLength){
            logger.error("The first Vector is too big!");
            return new ResponseEntity("The first Vector is too big!", HttpStatus.BAD_REQUEST);
        }
        else if (secondVector.length > vectorMaxLength){
            logger.error("The second Vector is too big!");
            return new ResponseEntity("The second Vector is too big!", HttpStatus.BAD_REQUEST);
        }
        logger.info("Perform add operation {} + {} = {}",firstVector,secondVector,result);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public ResponseEntity<Vector<Double>> sub(double firstVector[], double secondVector[]){
        Vector<Double> result = new Vector<Double>();
        if((firstVector.length <= vectorMaxLength ) && (secondVector.length <= vectorMaxLength) &&
                (firstVector.length == secondVector.length)){
            for(int i =0; i < firstVector.length; i++) {
                result.addElement(firstVector[i] - secondVector[i]);
            }
        }
        else if(firstVector.length != secondVector.length){
            logger.error("The Vectors are different sizes!");
            return new ResponseEntity("The Vectors are different sizes!", HttpStatus.BAD_REQUEST);
        }
        else if (firstVector.length > vectorMaxLength){
            logger.error("The first Vector is too big!");
            return new ResponseEntity("The first Vector is too big!", HttpStatus.BAD_REQUEST);
        }
        else if (secondVector.length > vectorMaxLength){
            logger.error("The second Vector is too big!");
            return new ResponseEntity("The second Vector is too big!", HttpStatus.BAD_REQUEST);
        }
        logger.info("Perform mul operation {} * {} = {}",firstVector,secondVector,result);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    //Works only good on paper. Have some problem inputting it in browser API
    public ResponseEntity<Vector<Vector<Double>>> mul(double number, double matrix[][]) {
        if((matrix.length <= matrixMaxCol) && (matrix[0].length <= matrixMaxRow)){
            for (int r = 0; r < matrix.length; r++) {
                for (int c = 0; c < matrix[0].length; c++) {
                        System.out.printf(" " + (number * matrix[r][c]) + "\t");
                        matrix[r][c] = matrix[r][c] * number;
                }
                System.out.println();
            }
            logger.info("You successfully multiply matrix by number");
            return new ResponseEntity(matrix, HttpStatus.OK);
        }
        else{
            logger.info("Either row or column of the matrix is to big!");
            return new ResponseEntity("Either row or column of the matrix is to big!", HttpStatus.BAD_REQUEST);
        }
    }

    //Works only good on paper. Have some problem inputting it in browser API
    public ResponseEntity<Vector<Vector<Double>>> sum(double firstMatrix[][], double secondMatrix[][]) {
        if((secondMatrix.length <= matrixMaxCol) && (secondMatrix[0].length <= matrixMaxRow) &&
                (firstMatrix.length <= matrixMaxCol) && (firstMatrix[0].length <= matrixMaxRow)){
            for (int r = 0; r < secondMatrix.length; r++) {
                for (int c = 0; c < secondMatrix[0].length; c++) {
                    System.out.printf(" " + (firstMatrix[r][c] + secondMatrix[r][c]) + "\t");
                    secondMatrix[r][c] = secondMatrix[r][c] + firstMatrix[r][c];
                }
                System.out.println();
            }
            logger.info("You successfully add 2 matrices");
            return new ResponseEntity(secondMatrix, HttpStatus.OK);
        }
        else{
            logger.info("Either row or column of one of the matrices is to big!");
            return new ResponseEntity("Either row or column of the matrix is to big!", HttpStatus.BAD_REQUEST);
        }
    }

    //Works only good on paper. Have some problem inputting it in browser API
    public ResponseEntity<Vector<Vector<Double>>> sub(double firstMatrix[][], double secondMatrix[][]) {
        if((secondMatrix.length <= matrixMaxCol) && (secondMatrix[0].length <= matrixMaxRow) &&
                (firstMatrix.length <= matrixMaxCol) && (firstMatrix[0].length <= matrixMaxRow)){
            for (int r = 0; r < secondMatrix.length; r++) {
                for (int c = 0; c < secondMatrix[0].length; c++) {
                    System.out.printf(" " + (firstMatrix[r][c] - secondMatrix[r][c]) + "\t");
                    secondMatrix[r][c] = secondMatrix[r][c] - firstMatrix[r][c];
                }
                System.out.println();
            }
            logger.info("You successfully subtract 2 matrices");
            return new ResponseEntity(secondMatrix, HttpStatus.OK);
        }
        else{
            logger.info("Either row or column of one of the matrices is to big!");
            return new ResponseEntity("Either row or column of the matrix is to big!", HttpStatus.BAD_REQUEST);
        }
    }

    //Multiplying matrix - matrix and matrix - vector not implemented. In future updates

    public ResponseEntity downloadFileFromLocal() {
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
