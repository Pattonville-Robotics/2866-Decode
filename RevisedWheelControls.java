package org.firstinspires.ftc.teamcode;

public class RevisedWheelControls {
    private double[] multipliers;
    private RefitSquare refitSquare;
    
    public RevisedWheelControls()
    {
        multipliers = new double[] {1,1,1,1};
        refitSquare = new RefitSquare();
    }
    
    public RevisedWheelControls(double[] input_multipliers)
    {
        multipliers = input_multipliers;
        refitSquare = new RefitSquare();
    }
    
    public RevisedWheelControls(double frontLeft, double frontRight, double backLeft, double backRight)
    {
        multipliers = new double[] {frontLeft, frontRight, backLeft, backRight};
        refitSquare = new RefitSquare();
    }
    
    private double[] rotateAngle(double angle, double[] pos)
    {
        double f = pos[0]*Math.cos(angle)-pos[1]*Math.sin(angle);
        double b = pos[0]*Math.sin(angle)+pos[1]*Math.cos(angle);
        double[] fb = {f,b};
        return(fb);
    }
    
    public double[] disentangle_uncorrected(double[] pos)
    {
        return(rotateAngle(-45*(Math.PI/180),new double[]{pos[0],pos[1]}));
    }
    
    public double[] disentangle_corrected(double[] pos)
    {
        return(refitSquare.convertPoint(disentangle_uncorrected(pos)));
    }
    
    public double[] calculate(double x1, double y1, double x2, double y2)
    {
        double[] leftJoystick = new double[] {x1,y1};
        double[] rightJoystick = new double[] {x2,y2};
        
        double[] leftWheels = disentangle_corrected(leftJoystick);
        double[] rightWheels = disentangle_corrected(rightJoystick);
        
        double frontLeft=leftWheels[1];
        double frontRight=leftWheels[0];
        double backLeft=rightWheels[0];
        double backRight=rightWheels[1];
        
        frontLeft*=multipliers[0];
        frontRight*=multipliers[1];
        backLeft*=multipliers[2];
        backRight*=multipliers[3];
        
        double[] return_list = new double[] {frontLeft,frontRight,backLeft,backRight};
        return(return_list);
    }
    
    public double[] calculate(double[] values)
    {
        return(calculate(values[0],values[1],values[2],values[3]));
    }
    
    public double[] get_multipliers()
    {
        return(multipliers);
    }
}

//Willow Harpole, 1/18/2026