package io.github.harpolewillow.complexkebabcalculator;

import java.util.Scanner;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class ComplexKebabCalculator {
    
    ButtonInterpreter buttonInterpreter;
    BooleansToDoubles booleansToDoubles;
    
    double minValue;
    
    public LinearOpMode opMode;
    
    public ComplexKebabCalculator()
    {
        minValue = 0.5;
        buttonInterpreter = new ButtonInterpreter(true);
        booleansToDoubles = new BooleansToDoubles(minValue);
    }
    
    public ComplexKebabCalculator(double inputMinValue)
    {
        minValue = inputMinValue;
        buttonInterpreter = new ButtonInterpreter(true);
        booleansToDoubles = new BooleansToDoubles(minValue);
    }
    
    public void setMinValue(double inputMinValue)
    {
        minValue=inputMinValue;
        booleansToDoubles.setMinValue(minValue);
    }
    
    public double getMinValue(){return(minValue);}
    
    public double calculate(boolean b1, boolean b2, boolean b3, boolean b4)
    {
        // ButtonInterpreter: handle inputs
        boolean[] buttons = buttonInterpreter.calculate(b1,b2,b3,b4);
        // BooleansToDoubles: get actual numbers
        double wheelNumber = booleansToDoubles.calculate(buttons[0],buttons[1],buttons[2],buttons[3]);
        // return values
        opMode.telemetry.addData("boolean1: ",b1);
        opMode.telemetry.addData("boolean2: ",b2);
        opMode.telemetry.addData("boolean3: ",b3);
        opMode.telemetry.addData("boolean4: ",b4);
        
        opMode.telemetry.addData("button1: ",buttons[0]);
        opMode.telemetry.addData("button2: ",buttons[1]);
        opMode.telemetry.addData("button3: ",buttons[2]);
        opMode.telemetry.addData("button4: ",buttons[3]);
        
        opMode.telemetry.addData("wheelPower: ",wheelNumber);
        return(wheelNumber);
    }
    
    public static void main(String[] args)
    {
        ComplexKebabCalculator complexKebabCalculator = new ComplexKebabCalculator(0.2);
        complexKebabCalculator.setMinValue(.68);
        Scanner myScanner = new Scanner(System.in);
        while (true)
        {
            System.out.print("Boolean 1: ");
            boolean firstBoolean  = myScanner.nextBoolean();
            System.out.print("Boolean 2: ");
            boolean secondBoolean = myScanner.nextBoolean();
            System.out.print("Boolean 3: ");
            boolean thirdBoolean  = myScanner.nextBoolean();
            System.out.print("Boolean 4: ");
            boolean fourthBoolean = myScanner.nextBoolean();
            
            double myValue = complexKebabCalculator.calculate(firstBoolean,secondBoolean,thirdBoolean,fourthBoolean);
            
            System.out.println(myValue);
        }
    }
}