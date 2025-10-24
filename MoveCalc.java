package org.firstinspires.ftc.teamcode;

public class MoveCalc {
    
    static boolean LEFT_RIGHT_CONSTANT;
    
    public MoveCalc() {
        LEFT_RIGHT_CONSTANT = true;
    }
    
    public void test() {System.out.println("Testing123");}
    
    public static double[] rotatePoint(double px, double py, double cx, double cy, double angleRadians) {
        // Translate point to origin
        double tempX = px - cx;
        double tempY = py - cy;

        // Apply rotation
        double rotatedX = tempX * Math.cos(angleRadians) - tempY * Math.sin(angleRadians);
        double rotatedY = tempX * Math.sin(angleRadians) + tempY * Math.cos(angleRadians);

        // Translate point back
        double newX = rotatedX + cx;
        double newY = rotatedY + cy;

        return new double[]{newX, newY};
    }
    
    public double degrees_to_radians(double degrees) {return(degrees*(Math.PI/180));}
    public double[] rotateCoordinates_45(double x, double y) {return(rotatePoint(x,y,0,0,degrees_to_radians(45)));}
    public double[] rotateCoordinates_neg45(double x, double y) {return(rotatePoint(x,y,0,0,degrees_to_radians(-45)));}
    public double[] get_side(double joystickX, double joystickY) {return(rotateCoordinates_45(joystickX, joystickY));}
    public double double_fix(double input_double) {if (input_double < 0.01 & input_double>-0.01) {return(0);} else {return(input_double);}}
    
    public double[] merge_sides(double[] left_side, double[] right_side) {
        double frontLeft;
        double frontRight;
        double backLeft;
        double backRight;
        
        frontLeft = double_fix(-left_side[0]);
        backLeft = double_fix(left_side[1]);
            
        frontRight = double_fix(right_side[1]);
        backRight = double_fix(-right_side[0]);
        
        double[] nums_to_return = new double[]{frontLeft,backLeft,frontRight,backRight};
        return(nums_to_return);
    }
    
    public double[] get_wheel_movements(double left_joystickX, double left_joystickY, double right_joystickX, double right_joystickY) {
        double[] left_side = get_side(left_joystickX, left_joystickY);
        double[] right_side = get_side(right_joystickX, right_joystickY);
        double[] movements = merge_sides(left_side,right_side);
        return(movements);
    }
    
}