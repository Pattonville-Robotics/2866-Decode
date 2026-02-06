package io.github.harpolewillow.turnedaxismecanum;

//converts a linear -1 to 1 scale to a logarithmic -1 to 1 scale.

public class LogConverter
{
    public LogConverter(){}

    public double convert(double value)
    {
        return(-Math.log10(-(value * .9)+1));
    }

    public static void main(String[] args)
    {
        LogConverter lc = new LogConverter();
        System.out.println(lc.convert(.5));
    }
}