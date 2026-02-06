package io.github.harpolewillow.turnedaxismecanum;

public class WheelConverter
{
    DoubleScaleConversion leftFrontConversion;
    DoubleScaleConversion leftBackConversion;
    DoubleScaleConversion rightFrontConversion;
    DoubleScaleConversion rightBackConversion;

    public WheelConverter
    (
        DoubleScaleConversion input_leftFrontConversion,
        DoubleScaleConversion input_leftBackConversion,
        DoubleScaleConversion input_rightFrontConversion,
        DoubleScaleConversion input_rightBackConversion
    )
    {
        leftFrontConversion = input_leftFrontConversion;
        leftBackConversion = input_leftBackConversion;
        rightFrontConversion = input_rightFrontConversion;
        rightBackConversion = input_rightBackConversion;
    }

    public WheelConverter
    (
        DoubleScale input_leftFrontOutputScale,
        DoubleScale input_leftBackOutputScale,
        DoubleScale input_rightFrontOutputScale,
        DoubleScale input_rightBackOutputScale
    )
    {
        DoubleScale nullScale = new DoubleScale(-1.0, 1.0);

        leftFrontConversion = new DoubleScaleConversion(nullScale,input_leftFrontOutputScale);
        leftBackConversion = new DoubleScaleConversion(nullScale,input_leftBackOutputScale);
        rightFrontConversion = new DoubleScaleConversion(nullScale,input_rightFrontOutputScale);
        rightBackConversion = new DoubleScaleConversion(nullScale,input_rightBackOutputScale);
    }

    public WheelConverter()
    {
        DoubleScaleConversion nullConversion = new DoubleScaleConversion
        (
            new DoubleScale(-1.0, 1.0),
            new DoubleScale(-1.0, 1.0)
        );

        leftFrontConversion = nullConversion.get();
        leftBackConversion = nullConversion.get();
        rightFrontConversion = nullConversion.get();
        rightBackConversion = nullConversion.get();
    }

    public WheelConverter get()
    {
        return (new WheelConverter
        (
            leftFrontConversion.get(),
            leftBackConversion.get(),
            rightFrontConversion.get(),
            rightBackConversion.get()
        ));
    }

    public double[][] convert(double[] leftWheels, double[] rightWheels)
    {
        double leftFrontWheel_input = leftWheels[0];
        double leftBackWheel_input = leftWheels[1];
        double rightFrontWheel_input = rightWheels[0];
        double rightBackWheel_input = rightWheels[1];

        double leftFrontWheel_newValue = leftFrontConversion.convert(leftFrontWheel_input);
        double leftBackWheel_newValue = leftBackConversion.convert(leftBackWheel_input);
        double rightFrontWheel_newValue = rightFrontConversion.convert(rightFrontWheel_input);
        double rightBackWheel_newValue = rightBackConversion.convert(rightBackWheel_input);

        double[] leftWheels_converted = {leftFrontWheel_newValue, leftBackWheel_newValue};
        double[] rightWheels_converted = {rightFrontWheel_newValue, rightBackWheel_newValue};

        return (new double[][] {leftWheels_converted, rightWheels_converted});
    }

    public static void main(String[] args)
    {
        DoubleScale inputScale = new DoubleScale(0.0, 100.0);
        DoubleScale outputScale = new DoubleScale(0.0, 1.0);

        DoubleScaleConversion wheelConversion = new DoubleScaleConversion(inputScale, outputScale);

        WheelConverter wheelConverter = new WheelConverter
        (
            wheelConversion,
            wheelConversion,
            wheelConversion,
            wheelConversion
        );

        double[] leftWheels = {50.0, 75.0};
        double[] rightWheels = {25.0, 100.0};

        double[][] convertedWheels = wheelConverter.convert(leftWheels, rightWheels);

        System.out.println("Converted Left Wheels: " + convertedWheels[0][0] + ", " + convertedWheels[0][1]);
        System.out.println("Converted Right Wheels: " + convertedWheels[1][0] + ", " + convertedWheels[1][1]);
    }

}