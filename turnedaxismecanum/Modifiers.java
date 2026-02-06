package io.github.harpolewillow.turnedaxismecanum;

/**
 * Applies modifiers to transform joystick input values into wheel motor speeds.
 * Supports logarithmic scaling and wheel output inversion for mecanum drive systems.
 * This class utilizes a 2D array structure where indices correspond to:
 * [0] = left wheels, [1] = right wheels; within each, [0] = front, [1] = back.
 */
public class Modifiers
{
    /** Instance used for disentangling coordinate values */
    private final FullDisentangler FULLDISENTANGLER = new FullDisentangler();

    /**
     * Constructs a new Modifiers instance.
     */
    /**
     * Constructs a new Modifiers instance.
     */
    public Modifiers() {}
    /**
     * Calculates wheel motor speeds from joystick input values using DoubleScale objects.
     *
     * @param input_points a 2D array of joystick input values [[x_values...], [y_values...]]
     * @param newScales a 2D array of DoubleScale objects for each wheel [left/right][front/back]
     * @param enable_logarithm whether to apply logarithmic scaling to the values
     * @param invert whether to swap front and back wheel outputs
     * @return a 2D array of calculated wheel speeds [[left_front, left_back], [right_front, right_back]]
     */
    public double[][] calculate
    (
        double[][] input_points,
        DoubleScale[][] newScales,
        boolean enable_logarithm,
        boolean invert
    )
    {
        double[][] input_points_2 = FULLDISENTANGLER.disentangle(input_points[0], input_points[1]);
        double leftFront = input_points_2[0][0];
        double leftBack = input_points_2[0][1];
        double rightFront = input_points_2[1][0];
        double rightBack = input_points_2[1][1];
        if (enable_logarithm)
        {
            LogConverter logConverter = new LogConverter();
            leftFront = logConverter.convert(leftFront);
            leftBack = logConverter.convert(leftBack);
            rightFront = logConverter.convert(rightFront);
            rightBack = logConverter.convert(rightBack);
        }
        DoubleScaleConversion doubleScaleConversionLeftFront = new DoubleScaleConversion(newScales[0][0]);
        DoubleScaleConversion doubleScaleConversionLeftBack = new DoubleScaleConversion(newScales[0][1]);
        DoubleScaleConversion doubleScaleConversionRightFront = new DoubleScaleConversion(newScales[1][0]);
        DoubleScaleConversion doubleScaleConversionRightBack = new DoubleScaleConversion(newScales[1][1]);
        leftFront = doubleScaleConversionLeftFront.convert(leftFront);
        leftBack = doubleScaleConversionLeftBack.convert(leftBack);
        rightFront = doubleScaleConversionRightFront.convert(rightFront);
        rightBack = doubleScaleConversionRightBack.convert(rightBack);

        double[] leftWheels = new double[] {leftFront, leftBack};
        double[] rightWheels = new double[] {rightFront, rightBack};
        if (invert)
        {
            leftWheels = new double[] {leftBack, leftFront};
            rightWheels = new double[] {rightBack, rightFront};
        }
        double[][] outputWheelValues = new double[][] {leftWheels, rightWheels};

        return(outputWheelValues);
    }

    /**
     * Calculates wheel motor speeds from joystick input values using DoubleScaleConversion objects.
     *
     * @param input_points a 2D array of joystick input values [[x_values...], [y_values...]]
     * @param newScales a 2D array of DoubleScaleConversion objects for each wheel [left/right][front/back]
     * @param enable_logarithm whether to apply logarithmic scaling to the values
     * @param invert whether to swap front and back wheel outputs
     * @return a 2D array of calculated wheel speeds [[left_front, left_back], [right_front, right_back]]
     */
    public double[][] calculate
    (
        double[][] input_points,
        DoubleScaleConversion[][] newScales,
        boolean enable_logarithm,
        boolean invert
    )
    {
        double[][] input_points_2 = FULLDISENTANGLER.disentangle(input_points[0], input_points[1]);
        double leftFront = input_points_2[0][0];
        double leftBack = input_points_2[0][1];
        double rightFront = input_points_2[1][0];
        double rightBack = input_points_2[1][1];
        if (enable_logarithm)
        {
            LogConverter logConverter = new LogConverter();
            leftFront = logConverter.convert(leftFront);
            leftBack = logConverter.convert(leftBack);
            rightFront = logConverter.convert(rightFront);
            rightBack = logConverter.convert(rightBack);
        }
        DoubleScaleConversion doubleScaleConversionLeftFront = newScales[0][0];
        DoubleScaleConversion doubleScaleConversionLeftBack = newScales[0][1];
        DoubleScaleConversion doubleScaleConversionRightFront = newScales[1][0];
        DoubleScaleConversion doubleScaleConversionRightBack = newScales[1][1];
        leftFront = doubleScaleConversionLeftFront.convert(leftFront);
        leftBack = doubleScaleConversionLeftBack.convert(leftBack);
        rightFront = doubleScaleConversionRightFront.convert(rightFront);
        rightBack = doubleScaleConversionRightBack.convert(rightBack);

        double[] leftWheels = new double[] {leftFront, leftBack};
        double[] rightWheels = new double[] {rightFront, rightBack};
        if (invert)
        {
            leftWheels = new double[] {leftBack, leftFront};
            rightWheels = new double[] {rightBack, rightFront};
        }
        double[][] outputWheelValues = new double[][] {leftWheels, rightWheels};

        return(outputWheelValues);
    }

    /**
     * Main method demonstrating the Modifiers class functionality.
     * Creates a sample input and applies default logarithmic scaling and inversion.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args)
    {
        Modifiers modifiers = new Modifiers();
        DoubleScale scale = new DoubleScale(-1000.0, 1000.0);
        DoubleScale[][] scales = new DoubleScale[][] {{scale, scale}, {scale, scale}};
        double[][] input_points = new double[][] {{0.5, 0.5}, {0.5, 0.5}};
        double[][] output = modifiers.calculate(input_points, scales, true, false);
        System.out.println("Output Wheel Values:");
        for (double[] side : output)
        {
            for (double value : side)
            {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
