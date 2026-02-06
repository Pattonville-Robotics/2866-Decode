package io.github.harpolewillow.turnedaxismecanum;

public class DoubleScale
{
    private final double min;
    private final double max;

    public DoubleScale(double min_input, double max_input)
    {
        min = min_input;
        max = max_input;
    }

    public double getMin(){return min;}
    public double getMax(){return max;}
    public DoubleScale get(){return (new DoubleScale(min,max));}
    public double findRange(){return(max - min);}
    @Override
    public String toString(){return (min + " to " + max);}

    //converts to portion completed
    public double findPortion(double value)
    {
        return (value - min) / findRange();
    }

    //converts to raw value
    public double findValue(double portion)
    {
        return (min + portion * findRange());
    }

    public static void main(String[] args)
    {
        DoubleScale scale = new DoubleScale(10.0, 20.0);
        System.out.println("Scale: " + scale);
        double portion = scale.findPortion(15.0);
        System.out.println("Portion for value 15.0: " + portion);
        double value = scale.findValue(0.5);
        System.out.println("Value for portion 0.5: " + value);
    }

}