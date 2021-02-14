package com.patronage.calculator.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Vector;

@Component
public class CalculatorService{

    public int sum(int firstNumber, int secondNumber) {
        return (firstNumber + secondNumber);
    }
    public int mul(int firstNumber, int seconfNumber){
        return (firstNumber * seconfNumber);
    }

    public ResponseEntity<Vector<Double>> mul(double firstNumber, double secondVector[]){
        Vector<Double> result = new Vector<Double>();
        if(secondVector.length < 5){
            for (int i = 0; i < secondVector.length; i++) {
                    result.addElement(firstNumber*secondVector[i]);
            }
        }
        else{
            return new ResponseEntity("The value of Vector is to big!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
