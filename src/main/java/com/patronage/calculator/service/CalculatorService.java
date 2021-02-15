package com.patronage.calculator.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
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

    public double sum(double firstNumber, double secondNumber) {
        double result = firstNumber + secondNumber;
        logger.info("Perform adding operation {} + {} = {}",firstNumber,secondNumber,result);
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

    public double div(double firstNumber, double secondNumber){
        double result = firstNumber / secondNumber;
        logger.info("Dividing {} by {} gives {}",firstNumber,secondNumber,result);
        return result;
    }

    public double exp(double firstNumber, double secondNumber){
        double result = Math.pow(firstNumber,secondNumber);
        logger.info("{} to power {} equals {}",firstNumber,secondNumber,result);
        return result;
    }

    public double root(double firstNumber, double secondNumber){
        double result = Math.pow(firstNumber,(1/secondNumber));
        logger.info("{} root of {} equals {}",secondNumber,firstNumber,result);
        return result;
    }

    public ResponseEntity<Vector<Double>> mul(double number, double vector[]){
        Vector<Double> result = new Vector<Double>();
        System.out.println("vector = " + vectorMaxLength);
        System.out.println("vector = " + vector.length);
        if(vector.length <= vectorMaxLength){
            for (int i = 0; i < vector.length; i++) {
                    result.addElement(number*vector[i]);
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
        if((firstVector.length < 5) && (secondVector.length < 5) && (firstVector.length==secondVector.length)){ // both vector cannot be bigger then 4 and the same size
            for(int i =0; i <firstVector.length; i++) {
                result.addElement(firstVector[i]+secondVector[i]);
            }
        }
        else if(firstVector.length!=secondVector.length){
            logger.error("The Vectors are different sizes!");
            return new ResponseEntity("The Vectors are different sizes!", HttpStatus.BAD_REQUEST);
        }
        else if (firstVector.length >= 5){
            logger.error("The first Vector is too big!");
            return new ResponseEntity("The first Vector is too big!", HttpStatus.BAD_REQUEST);
        }
        else if (secondVector.length >= 5){
            logger.error("The second Vector is too big!");
            return new ResponseEntity("The second Vector is too big!", HttpStatus.BAD_REQUEST);
        }
        logger.info("Perform add operation {} + {} = {}",firstVector,secondVector,result);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public ResponseEntity<Vector<Double>> sub(double firstVector[], double secondVector[]){
        Vector<Double> result = new Vector<Double>();
        if((firstVector.length <=4 ) && (secondVector.length < 5) && (firstVector.length==secondVector.length)){ // both vector cannot be bigger then 4 and the same size
            for(int i =0; i <firstVector.length; i++) {
                result.addElement(firstVector[i] - secondVector[i]);
            }
        }
        else if(firstVector.length!=secondVector.length){
            logger.error("The Vectors are different sizes!");
            return new ResponseEntity("The Vectors are different sizes!", HttpStatus.BAD_REQUEST);
        }
        else if (firstVector.length >= 5){
            logger.error("The first Vector is too big!");
            return new ResponseEntity("The first Vector is too big!", HttpStatus.BAD_REQUEST);
        }
        else if (secondVector.length >= 5){
            logger.error("The second Vector is too big!");
            return new ResponseEntity("The second Vector is too big!", HttpStatus.BAD_REQUEST);
        }
        logger.info("Perform mul operation {} * {} = {}",firstVector,secondVector,result);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public ResponseEntity<Vector<Vector<Double>>> mul(double number, double matrix[][]){
        Vector<Vector<Double>> result = new Vector<Vector<Double>>();
        Vector<Double> inside = new Vector<Double>();
        if(matrix.length <= matrixMaxCol){
            for(int i=0; i<matrix.length; i++){
                for(int j=0; j<matrix[0].length;j++){
                    inside.addElement(number * matrix[i][j]);
                }
                result.addElement(inside);
            }
        }
        logger.info("Everything went smooth");
        return new ResponseEntity(result,HttpStatus.OK);
    }

    double[][] firstMatrix = {
            new double[]{1d, 5d},
            new double[]{2d, 3d},
            new double[]{1d, 7d}
    };

    double[][] secondMatrix = {
            new double[]{1d, 2d, 3d, 7d},
            new double[]{5d, 2d, 8d, 1d}
    };
    double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }

        return result;
    }
    double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

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
