package io.github.harpolewillow.turnedaxismecanum;

public class DoubleScaleConversion
{
    private final DoubleScale inputScale;
    private final DoubleScale outputScale;
    
    public DoubleScaleConversion
    (
        DoubleScale input_inputScale,
        DoubleScale input_outputScale
    )
    {
        inputScale = input_inputScale.get();
        outputScale = input_outputScale.get();
    }

    public DoubleScaleConversion(DoubleScale output_scale)
    {
        inputScale = new DoubleScale(-1.0, 1.0);
        outputScale = output_scale;
    }

    public double convert(double inputValue)
    {
        double portion = inputScale.findPortion(inputValue);
        return outputScale.findValue(portion);
    }

    public DoubleScaleConversion get()
    {
        return (new DoubleScaleConversion(inputScale.get(), outputScale.get()));
    }

    public DoubleScale getInputScale()
    {
        return inputScale.get();
    }

    public DoubleScale getOutputScale()
    {
        return outputScale.get();
    }

    public static void main(String[] args)
    {
        DoubleScale inputScale = new DoubleScale(10.0, 20.0);
        DoubleScale outputScale = new DoubleScale(100.0, 200.0);
        DoubleScaleConversion converter = new DoubleScaleConversion(inputScale, outputScale);
        
        double inputValue = 15.0;
        double outputValue = converter.convert(inputValue);
        
        System.out.println("Input Value: " + inputValue);
        System.out.println("Converted Output Value: " + outputValue);
    }
}