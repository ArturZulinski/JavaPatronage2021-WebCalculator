package com.patronage.calculator.service;

import com.patronage.calculator.controler.model.MatricesForm;
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

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class CalculatorService {

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
        String message = String.format("Perform adding operation %.1f + %.1f = %.1f", firstNumber, secondNumber, result);
        historyInterface.saveHistory(message);      //toFile or database
        logger.info(message);                       // console
        return result;
    }

    public double subtractTwoNumbers(double firstNumber, double secondNumber) {
        double result = firstNumber - secondNumber;
        String message = String.format("Performing subtraction %.1f - %.1f = %.1f", firstNumber, secondNumber, result);
        historyInterface.saveHistory(message);
        logger.info(message);
        return result;
    }

    public double multiplyTwoNumbers(double firstNumber, double secondNumber) {
        double result = firstNumber * secondNumber;
        String message = String.format("Multiplying 2 numbers %.1f * %.1f = %.1f", firstNumber, secondNumber, result);
        historyInterface.saveHistory(message);
        logger.info(message);
        return result;
    }

    public ResponseEntity<Double> divideTwoNumbers(double firstNumber, double secondNumber) {
        double result = firstNumber / secondNumber;
        if (secondNumber == 0) {
            String message = "You cannot divide by 0 !!!";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else {
            String message = String.format("Dividing %.1f / %.1f = %.1f", firstNumber, secondNumber, result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(result, HttpStatus.OK);
        }
    }

    public double exponentiationNumber(double firstNumber, double secondNumber) {
        double result = Math.pow(firstNumber, secondNumber);
        String message = String.format("%.1f to power %.1f equals %.1f", firstNumber, secondNumber, result);
        historyInterface.saveHistory(message);
        logger.info(message);
        return result;
    }

    public ResponseEntity<Double> rootNumber(double firstNumber, double secondNumber) {
        double result = Math.pow(firstNumber, (1 / secondNumber));
        if (secondNumber == 0) {
            String message = "You cannot root by 0 !!!";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else {
            String message = String.format("%.1f root of %.1f equals %.1f", secondNumber, firstNumber, result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(result, HttpStatus.OK);
        }
    }

    public ResponseEntity<double[]> multiplyNumberAndVector(double number, double vector[]) {
        int sizeOfVector = vector.length;
        double[] result = new double[sizeOfVector];
        if (vector.length <= vectorMaxLength) {
            for (int i = 0; i < vector.length; i++) {
                result[i] = number * vector[i];
            }
            String message = "Perform multiplication operation " + number +" * " + Arrays.toString(vector)
                    + " = " + Arrays.toString(result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            String message = "The value of Vector is to big!";
            historyInterface.saveHistory(message);
            logger.error(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<double[]> addingTwoVectors(double firstVector[], double secondVector[]) {
        int sizeOfVector = firstVector.length;
        double[] result = new double[sizeOfVector];
        if (firstVector.length != secondVector.length) {
            String message = "The Vectors are not the same size!";
            historyInterface.saveHistory(message);
            logger.error(message);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else if (firstVector.length > vectorMaxLength) {
            String message = "The first Vector is too big";
            historyInterface.saveHistory(message);
            logger.error(message);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else if (secondVector.length > vectorMaxLength) {
            String message = "The second Vector is too big";
            historyInterface.saveHistory(message);
            logger.error(message);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            for (int i = 0; i < firstVector.length; i++) {
                result[i] = firstVector[i] + secondVector[i];
            }
            String message = "Perform multiplication operation " + Arrays.toString(firstVector) + " * "
                    + Arrays.toString(secondVector) + " = " + Arrays.toString(result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    public ResponseEntity<double[]> subtractTwoVectors(double firstVector[], double secondVector[]) {
        int sizeOfVector = firstVector.length;
        double[] result = new double[sizeOfVector];
        if (firstVector.length != secondVector.length) {
            String message = "The Vectors are not the same size!";
            historyInterface.saveHistory(message);
            logger.error(message);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else if (firstVector.length > vectorMaxLength) {
            String message = "The first Vector is too big";
            historyInterface.saveHistory(message);
            logger.error(message);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else if (secondVector.length > vectorMaxLength) {
            String message = "The second Vector is too big";
            historyInterface.saveHistory(message);
            logger.error(message);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            for (int i = 0; i < firstVector.length; i++) {
                result[i] = firstVector[i] - secondVector[i];
            }
            String message = "Perform subtract operation " + Arrays.toString(firstVector) + " - "
                    + Arrays.toString(secondVector) + " = " + Arrays.toString(result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    public ResponseEntity<double[][]> multiplyMatrixByNumber(double number, double[][] matrix) {
        int sizeOfRow = matrix.length;
        int sizeOfColumn = matrix[0].length;
        double[][] result = new double[sizeOfRow][sizeOfColumn];
        if (matrix.length > matrixMaxRow) {
            String message = "Matrix row is too big!!";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else if (matrix[0].length > matrixMaxCol) {
            String message = "Matrix column is too big!!";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else {
            for (int r = 0; r < matrix.length; r++) {
                for (int c = 0; c < matrix[0].length; c++) {
                    result[r][c] = number * matrix[r][c];
                }
            }
            String message = "Successfully multiply number and matrix " + number + " * " + Arrays.deepToString(matrix)
                    + " = " + Arrays.deepToString(result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(result, HttpStatus.OK);
        }

    }

    public ResponseEntity<double[][]> addingTwoMatrices(MatricesForm matricesForm) {
        int sizeOfRow = matricesForm.getFirstMatrix().length;
        int sizeOfColumn = matricesForm.getFirstMatrix()[0].length;
        double[][] result = new double[sizeOfRow][sizeOfColumn];
        if (matricesForm.getFirstMatrix().length > matrixMaxRow) {
            String message = "First matrix row is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else if (matricesForm.getFirstMatrix()[0].length > matrixMaxCol) {
            String message = "First matrix column is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else if (matricesForm.getSecondMatrix().length > matrixMaxRow) {
            String message = "Second matrix row is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else if (matricesForm.getSecondMatrix()[0].length > matrixMaxCol) {
            String message = "Second matrix column is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else if ((matricesForm.getFirstMatrix().length != matricesForm.getSecondMatrix().length) ||
                (matricesForm.getFirstMatrix()[0].length != matricesForm.getSecondMatrix()[0].length)) {
            String message = "The matrices are not the same size !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else {
            for (int r = 0; r < matricesForm.getFirstMatrix().length; r++) {
                for (int c = 0; c < matricesForm.getFirstMatrix()[0].length; c++) {
                    result[r][c] = matricesForm.getFirstMatrix()[r][c] + matricesForm.getSecondMatrix()[r][c];
                }
            }
            String message = "Successfully add 2 matrices " + sizeOfRow + "x" + sizeOfColumn +
                    ":" + Arrays.deepToString(matricesForm.getFirstMatrix()) + " + " +
                    Arrays.deepToString(matricesForm.getSecondMatrix()) + " = " + Arrays.deepToString(result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(result, HttpStatus.OK);
        }
    }

    public ResponseEntity<double[][]> subtractTwoMatrices(MatricesForm matricesForm){
        int sizeOfRow = matricesForm.getFirstMatrix().length;
        int sizeOfColumn = matricesForm.getFirstMatrix()[0].length;
        double[][] result = new double[sizeOfRow][sizeOfColumn];
        if (matricesForm.getFirstMatrix().length > matrixMaxRow) {
            String message = "First matrix row is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else if (matricesForm.getFirstMatrix()[0].length > matrixMaxCol) {
            String message = "First matrix column is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else if (matricesForm.getSecondMatrix().length > matrixMaxRow) {
            String message = "Second matrix row is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else if (matricesForm.getSecondMatrix()[0].length > matrixMaxCol) {
            String message = "Second matrix column is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else if ((matricesForm.getFirstMatrix().length != matricesForm.getSecondMatrix().length) ||
                (matricesForm.getFirstMatrix()[0].length != matricesForm.getSecondMatrix()[0].length)) {
            String message = "The matrices are not the same size !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else {
            for (int r = 0; r < matricesForm.getFirstMatrix().length; r++) {
                for (int c = 0; c < matricesForm.getFirstMatrix()[0].length; c++) {
                    result[r][c] = matricesForm.getFirstMatrix()[r][c] - matricesForm.getSecondMatrix()[r][c];
                }
            }
            String message = "Successfully subtract 2 matrices " + sizeOfRow + "x" + sizeOfColumn +
                    ":" + Arrays.deepToString(matricesForm.getFirstMatrix()) + " + " +
                    Arrays.deepToString(matricesForm.getSecondMatrix()) + " = " + Arrays.deepToString(result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(result, HttpStatus.OK);
        }
    }

    public ResponseEntity<double[][]> multiplyMatrices(MatricesForm matricesForm) {
        int sizeOfFirstMatrixRow = matricesForm.getFirstMatrix().length;
        int sizeOfSecondMatrixColumn = matricesForm.getSecondMatrix()[0].length;
        double[][] result = new double[sizeOfFirstMatrixRow][sizeOfSecondMatrixColumn];
        if (matricesForm.getFirstMatrix().length > matrixMaxRow) {
            String message = "First matrix row is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else if (matricesForm.getFirstMatrix()[0].length > matrixMaxCol) {
            String message = "First matrix column is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else if (matricesForm.getSecondMatrix().length > matrixMaxRow) {
            String message = "Second matrix row is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else if (matricesForm.getSecondMatrix()[0].length > matrixMaxCol) {
            String message = "Second matrix column is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else if ((matricesForm.getFirstMatrix().length != matricesForm.getSecondMatrix().length) || (matricesForm.getFirstMatrix()[0].length != matricesForm.getSecondMatrix()[0].length)) {
            String message = "The matrices are not the same size !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
        else {
            for (int i = 0; i < matricesForm.getFirstMatrix().length; i++) {
                for (int j = 0; j < matricesForm.getSecondMatrix()[0].length; j++) {
                    for (int k = 0; k < matricesForm.getFirstMatrix().length; k++) {
                        result[i][j] += matricesForm.getFirstMatrix()[i][k] * matricesForm.getSecondMatrix()[k][j];
                    }
                }
            }
            String message = "Successfully multiply 2 matrices " + sizeOfFirstMatrixRow + "x" + sizeOfSecondMatrixColumn +
                    ":" + Arrays.deepToString(matricesForm.getFirstMatrix()) + " + " +
                    Arrays.deepToString(matricesForm.getSecondMatrix()) + " = " + Arrays.deepToString(result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(result, HttpStatus.OK);
        }
    }

    public ResponseEntity<double[][]> multiplyMatrixByVector(double[][] matrix, double[] vector) {
        int sizeOfFirstMatrixRow = matrix.length;
        int sizeOfFirstMatrixColumn = matrix[0].length;
        double[] result = new double[sizeOfFirstMatrixRow];
        if (matrix.length > matrixMaxRow) {
            String message = "Matrix row is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
        else if (matrix[0].length > matrixMaxCol) {
            String message = "Matrix column is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
        else if (vector.length > vectorMaxLength) {
            String message = "Vector is too big !";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
        else if (vector.length != matrix.length) {
            String message = "Matrix and vector are different sizes!!";
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
        else{
            for(int i=0; i < sizeOfFirstMatrixRow; i++){
            double sum = 0;
            for (int j=0; j< sizeOfFirstMatrixColumn; j++){
                sum += matrix[i][i] * vector[j];
            }
            result[i] = sum;
        }
            String message = "Successfully multiply matrix by Vector " + Arrays.toString(vector) + "*" +
                    Arrays.deepToString(matrix) + "=" + Arrays.toString(result);
            historyInterface.saveHistory(message);
            logger.info(message);
            return new ResponseEntity(result,HttpStatus.OK);
        }
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
