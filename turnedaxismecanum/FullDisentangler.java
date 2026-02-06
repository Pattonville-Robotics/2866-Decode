package io.github.harpolewillow.turnedaxismecanum;
/*
Returns this:

    value[0]: left wheels paired front-back
    left[1]: right wheels paired front-back

    value[0][0]: left front wheel
    value[0][1]: left back wheel
    value[1][0]: right front wheel
    value[1][1]: right back wheel
*/

public class FullDisentangler
{
    public FullDisentangler() {}
    public double[][] disentangle(double[] leftPoint, double[] rightPoint)
    {
        double[] newLeftPoint = new Converter().left(leftPoint);
        double[] newRightPoint = new Converter().right(rightPoint);
        return(new double[][] {newLeftPoint, newRightPoint});
    }
}