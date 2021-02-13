package com.patronage.calculator.controler;

import com.patronage.calculator.service.CalculatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculator")
@Api("This application let User to do simple calculations like addition, subtraction and others, on real numbers, vectors and " +
        "matrices depending on the configurations of the two.")
public class CalculatorControler {

    @Autowired
    private CalculatorServiceInterface calculatorServiceInterface;

    //@PostMapping
    @GetMapping("/firstChoice")
    @ApiOperation("This method ")
    public String method(@RequestParam String choice1 ){
     return choice1;
    }
    //@DeleteMapping
}
