package com.patronage.calculator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

@Component
public class CalculatorService{

    Logger logger = LoggerFactory.getLogger(CalculatorService.class);

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
        if(vector.length < 5){
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
        if((firstVector.length < 5) && (secondVector.length < 5) && (firstVector.length==secondVector.length)){ // both vector cannot be bigger then 4 and the same size
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
