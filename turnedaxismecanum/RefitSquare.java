package io.github.harpolewillow.turnedaxismecanum;

public class RefitSquare {
    public RefitSquare(){}
    
    private double[] find_squareLimit(double[] pos)
    {
        double limit = Math.max(Math.abs(pos[0]), Math.abs(pos[1]));
        return (new double[] {pos[0] / limit, pos[1] / limit});
    }
    
    private double[] find_circleLimit(double[] pos)
    {
        double x = pos[0]/Math.sqrt(Math.pow(pos[0],2)+Math.pow(pos[1],2));
        double y = pos[1]/Math.sqrt(Math.pow(pos[0],2)+Math.pow(pos[1],2));
        return(new double[] {x,y});
    }
    
    private double find_distFromOrigin(double[] pos)
    {
        return(Math.sqrt(Math.pow(pos[0],2)+Math.pow(pos[1],2)));
    }
    
    private double find_squareCircleLimitRatio(double[] pos)
    {
        double[] squareLimit = find_squareLimit(pos);
        double[] circleLimit = find_circleLimit(pos);
        double squareLimitDistance = find_distFromOrigin(squareLimit);
        double circleLimitDistance = find_distFromOrigin(circleLimit);
        double ratio = squareLimitDistance/circleLimitDistance;
        return(ratio);
    }
    
    private double[] multiply_position(double[] pos, double coefficient)
    {
        return(new double[] {pos[0]*coefficient,pos[1]*coefficient});
    }
    
    public double[] convertPoint(double[] pos)
    {
        double[] new_position = multiply_position(pos,find_squareCircleLimitRatio(pos));
        if (Double.isNaN(new_position[0])){return(new double[] {0,0});}
        return(new_position);
    }
}

//Willow Harpole, 1/18/2026