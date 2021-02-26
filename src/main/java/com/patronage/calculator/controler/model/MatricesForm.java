package com.patronage.calculator.controler.model;

import java.util.Arrays;

public class MatricesForm {

    private double[][] firstMatrix;
    private double[][] secondMatrix;

    public double[][] getFirstMatrix() {
        return firstMatrix;
    }

    public void setFirstMatrix(double[][] firstMatrix) {
        this.firstMatrix = firstMatrix;
    }

    public double[][] getSecondMatrix() {
        return secondMatrix;
    }

    public void setSecondMatrix(double[][] secondMatrix) {
        this.secondMatrix = secondMatrix;
    }


    @Override
    public String toString() {
        return "MatricesForm{" +
                "firstMatrix=" + Arrays.toString(firstMatrix) +
                ", secondMatrix=" + Arrays.toString(secondMatrix) +
                '}';
    }
}
