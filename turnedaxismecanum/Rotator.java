package io.github.harpolewillow.turnedaxismecanum;

public class Rotator
{
    public Rotator() {}
    public double[] rotateLeft(double[] inputPoint)
    {
        double theta = (Math.PI / 4); // 45 degrees
        double sin_theta = Math.sin(theta);
        double cos_theta = Math.cos(theta);
        double x =  cos_theta*inputPoint[0]+sin_theta*inputPoint[1];
        double y = -sin_theta*inputPoint[0]+cos_theta*inputPoint[1];
        return (new double[] {x, y});
    }
    public double[] rotateRight(double[] inputPoint)
    {
        double theta = (Math.PI / 4); // 45 degrees
        double sin_theta = Math.sin(theta);
        double cos_theta = Math.cos(theta);
        double x =  cos_theta*inputPoint[0]+sin_theta*inputPoint[1];
        double y = -sin_theta*inputPoint[0]+cos_theta*inputPoint[1];
        return (new double[] {y, x});
    }
}