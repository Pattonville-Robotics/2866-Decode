package io.github.harpolewillow.complexkebabcalculator;

import java.util.Scanner;

public class BooleansToDoubles {
    
    public double minValue;
    
    public BooleansToDoubles(){minValue=0.5;}
    public BooleansToDoubles(double inputMinValue){minValue=inputMinValue;}
    
    //b1 controls if it is negative
    public double calculate(boolean b1, boolean b2, boolean b3, boolean b4)
    {
        int sign = b1 ? -1 : 1;
        
        if (!b2&&!b3&&b4){return(sign*0.0);}
        
        if (!b2&&b3&&!b4){return(sign*((0.0/8.0)*(1.0-minValue)+minValue));}
        
        if (!b2&&b3&&b4){return(sign*((2.0/8.0)*(1.0-minValue)+minValue));}
        
        if (b2&&!b3&&!b4){return(sign*((4.0/8.0)*(1.0-minValue)+minValue));}
        
        if (b2&&!b3&&b4){return(sign*((6.0/8.0)*(1.0-minValue)+minValue));}
        
        if (b2&&b3&&!b4){return(sign*((7.0/8.0)*(1.0-minValue)+minValue));}
        
        if (b2&&b3&&b4){return(sign*1.0);}
        
        if (b1&&!b2&&!b3&&!b4){return(-0.2);} // if the user presses the negative button and nothing else
        
        else
        {
            throw new IllegalArgumentException("Input must have at least one true value.");
        }
        
    }
    
    public void setMinValue(double inputMinValue)
    {
        minValue = inputMinValue;
        if ((minValue<=0)||(minValue>=1.0))
        {
            throw new IllegalArgumentException("Input must be between 0 and 1 (exclusive).");
        }
    }
    
    public static void main(String[] args)
    {
        Scanner myScanner = new Scanner(System.in);
        BooleansToDoubles booleansToDoubles = new BooleansToDoubles();
        
        System.out.print("Boolean 1: ");
        boolean firstBoolean  = myScanner.nextBoolean();
        System.out.print("Boolean 2: ");
        boolean secondBoolean = myScanner.nextBoolean();
        System.out.print("Boolean 3: ");
        boolean thirdBoolean  = myScanner.nextBoolean();
        System.out.print("Boolean 4: ");
        boolean fourthBoolean = myScanner.nextBoolean();
        
        double myValue = booleansToDoubles.calculate(firstBoolean,secondBoolean,thirdBoolean,fourthBoolean);
        
        System.out.println(myValue);
        
    }
}