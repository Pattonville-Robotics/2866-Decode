package io.github.harpolewillow.turnedaxismecanum;

public class Converter
{
    public final Rotator ROTATOR = new Rotator();
    public final RefitSquare REFITSQUARE = new RefitSquare();

    public Converter() {}

    public double[] left(double[] inputPoint)
    {
        // Example conversion: scale by a factor of 2
        double[] xy = REFITSQUARE.convertPoint(ROTATOR.rotateLeft(inputPoint));
        return (xy);
    }

    public double[] right(double[] inputPoint)
    {
        // Example conversion: scale by a factor of 2
        double[] xy = REFITSQUARE.convertPoint(ROTATOR.rotateRight(inputPoint));
        return (xy);
    }
}