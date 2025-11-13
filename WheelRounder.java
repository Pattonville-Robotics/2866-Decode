/*

This makes two wheel values equal if they are close enough together. This
also ensures that they are not above 1.

*/
package org.firstinspires.ftc.teamcode;

public class WheelRounder {
    double tolerance;
    
    public WheelRounder(double tolerance_input) {
        tolerance = tolerance_input;
    }
    
    public void scream() {
        System.out.println("AAAAAA!!!");
    }
    
    public double truncateNumber(double number) {
        int number_processing = (int) (number * 100);
        double number_final_processing = (number_processing/100.0);
        return(number_final_processing);
    }
    
    //if this is close enough to 1 to be within its tolerance.
    public boolean toleratesOne(double number) {
        return(Math.abs(1-number)<=tolerance);
    }
    
    public boolean toleratesZero(double number) {
        return(Math.abs(number)<=tolerance);
    }
    
    public boolean toleratesNegativeOne(double number) {
        return(Math.abs(1+number)<=tolerance);
    }
    
    //this rounds, based on tolerance, to -1, 0, or 1. Also caps the range
    //of values, preventing them from going above 1 or below -1.
    public double ends(double number) {
        if (toleratesOne(number)) {
            return(1);
        }
        if (toleratesZero(number)) {
            return(0);
        }
        if (toleratesNegativeOne(number)) {
            return(-1);
        }
        if (number > 1) {
            return(1);
        }
        if (number < -1) {
            return (-1);
        }
        return(number);
    }
    
    public double[] roundWheel(double wheel1, double wheel2) {
        double wheel1_return = wheel1;
        double wheel2_return = wheel2;
        
        boolean close_to_opposites = false;
        
        if (Math.abs(wheel1-wheel2)<tolerance) {
            wheel1_return = (wheel1+wheel2)/2;
            wheel2_return = (wheel1+wheel2)/2;
        }
        
        wheel1_return = ends(wheel1_return);
        wheel2_return = ends(wheel2_return);
        
        double[] wheels_return = {truncateNumber(wheel1_return), truncateNumber(wheel2_return)};
        return(wheels_return);
    }
}