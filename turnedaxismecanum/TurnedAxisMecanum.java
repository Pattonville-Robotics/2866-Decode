package io.github.harpolewillow.turnedaxismecanum;

/**
 * Represents a Turned Axis Mecanum drive system with configurable wheel conversions.
 * This class manages the conversion of joystick input values to individual wheel motor speeds
 * for a mecanum-wheeled robot, supporting logarithmic scaling and wheel inversion.
 */
public class TurnedAxisMecanum
{
    /** Scaling conversion for the left front wheel */
    private DoubleScaleConversion leftFrontWheelScaleConversion;
    /** Scaling conversion for the left back wheel */
    private DoubleScaleConversion leftBackWheelScaleConversion;
    /** Scaling conversion for the right front wheel */
    private DoubleScaleConversion rightFrontWheelScaleConversion;
    /** Scaling conversion for the right back wheel */
    private DoubleScaleConversion rightBackWheelScaleConversion;

    /** Flag to enable logarithmic scaling for drive values */
    private boolean logarithmDrive;
    /** Flag to invert wheel output */
    private boolean invert;

    /** Shared modifier instance for calculating wheel values */
    /** Shared modifier instance for calculating wheel values */
    private static final Modifiers modifiers = new Modifiers();

    /**
     * Constructs a default TurnedAxisMecanum with no logarithm scaling or inversion.
     * All wheels use basic [-1, 1] to [-1, 1] scaling.
     */
    public TurnedAxisMecanum()
    {
        this.logarithmDrive = false;
        this.leftFrontWheelScaleConversion = makeBasicConversion();
        this.leftBackWheelScaleConversion = makeBasicConversion();
        this.rightFrontWheelScaleConversion = makeBasicConversion();
        this.rightBackWheelScaleConversion = makeBasicConversion();
        this.invert = false;
    }

    /**
     * Constructs a TurnedAxisMecanum with custom wheel conversions and logarithm scaling.
     *
     * @param logarithmDrive_input whether to enable logarithmic scaling
     * @param leftFrontWheelScaleConversion conversion settings for left front wheel
     * @param leftBackWheelScaleConversion conversion settings for left back wheel
     * @param rightFrontWheelScaleConversion conversion settings for right front wheel
     * @param rightBackWheelScaleConversion conversion settings for right back wheel
     */
    public TurnedAxisMecanum
    (
        boolean logarithmDrive_input,
        DoubleScaleConversion leftFrontWheelScaleConversion,
        DoubleScaleConversion leftBackWheelScaleConversion,
        DoubleScaleConversion rightFrontWheelScaleConversion,
        DoubleScaleConversion rightBackWheelScaleConversion
    )
    {
        this.logarithmDrive = logarithmDrive_input;
        this.leftFrontWheelScaleConversion = leftFrontWheelScaleConversion;
        this.leftBackWheelScaleConversion = leftBackWheelScaleConversion;
        this.rightFrontWheelScaleConversion = rightFrontWheelScaleConversion;
        this.rightBackWheelScaleConversion = rightBackWheelScaleConversion;
        this.invert = false;
    }

    /**
     * Constructs a TurnedAxisMecanum with custom wheel conversions, logarithm scaling, and inversion.
     *
     * @param logarithmDrive_input whether to enable logarithmic scaling
     * @param leftFrontWheelScaleConversion conversion settings for left front wheel
     * @param leftBackWheelScaleConversion conversion settings for left back wheel
     * @param rightFrontWheelScaleConversion conversion settings for right front wheel
     * @param rightBackWheelScaleConversion conversion settings for right back wheel
     * @param input_invert whether to invert wheel output
     */
    public TurnedAxisMecanum
    (
        boolean logarithmDrive_input,
        DoubleScaleConversion leftFrontWheelScaleConversion,
        DoubleScaleConversion leftBackWheelScaleConversion,
        DoubleScaleConversion rightFrontWheelScaleConversion,
        DoubleScaleConversion rightBackWheelScaleConversion,
        boolean input_invert
    )
    {
        this.logarithmDrive = logarithmDrive_input;
        this.leftFrontWheelScaleConversion = leftFrontWheelScaleConversion;
        this.leftBackWheelScaleConversion = leftBackWheelScaleConversion;
        this.rightFrontWheelScaleConversion = rightFrontWheelScaleConversion;
        this.rightBackWheelScaleConversion = rightBackWheelScaleConversion;
        this.invert = input_invert;
    }

    /**
     * Constructs a TurnedAxisMecanum with logarithm scaling setting.
     *
     * @param logarithmDrive_input whether to enable logarithmic scaling
     */
    public TurnedAxisMecanum(boolean logarithmDrive_input)
    {
        this.logarithmDrive = logarithmDrive_input;
        this.leftFrontWheelScaleConversion = makeBasicConversion();
        this.leftBackWheelScaleConversion = makeBasicConversion();
        this.rightFrontWheelScaleConversion = makeBasicConversion();
        this.rightBackWheelScaleConversion = makeBasicConversion();
        this.invert = false;
    }

    /**
     * Constructs a TurnedAxisMecanum with logarithm scaling and inversion settings.
     *
     * @param logarithmDrive_input whether to enable logarithmic scaling
     * @param invert_input whether to invert wheel output
     */
    public TurnedAxisMecanum
    (
        boolean logarithmDrive_input,
        boolean invert_input
    )
    {
        this.logarithmDrive = logarithmDrive_input;
        this.leftFrontWheelScaleConversion = makeBasicConversion();
        this.leftBackWheelScaleConversion = makeBasicConversion();
        this.rightFrontWheelScaleConversion = makeBasicConversion();
        this.rightBackWheelScaleConversion = makeBasicConversion();
        this.invert = invert_input;
    }

    /**
     * Constructs a TurnedAxisMecanum with per-wheel inversion control.
     *
     * @param logarithmDrive_input whether to enable logarithmic scaling
     * @param invertLeftFront whether to invert the left front wheel
     * @param invertLeftBack whether to invert the left back wheel
     * @param invertRightFront whether to invert the right front wheel
     * @param invertRightBack whether to invert the right back wheel
     */
    public TurnedAxisMecanum
    (
        boolean logarithmDrive_input,
        boolean invertLeftFront,
        boolean invertLeftBack,
        boolean invertRightFront,
        boolean invertRightBack
    )
    {
        this.logarithmDrive = logarithmDrive_input;
        this.leftFrontWheelScaleConversion = invertIfTrue(invertLeftFront);
        this.leftBackWheelScaleConversion = invertIfTrue(invertLeftBack);
        this.rightFrontWheelScaleConversion = invertIfTrue(invertRightFront);
        this.rightBackWheelScaleConversion = invertIfTrue(invertRightBack);
        this.invert = false;
    }

    /**
     * Creates a basic scale conversion with [-1, 1] input and output ranges.
     *
     * @return a new DoubleScaleConversion configured for basic [-1, 1] scaling
     */
    public DoubleScaleConversion makeBasicConversion()
    {
        return new DoubleScaleConversion
        (
            new DoubleScale(-1,1),
            new DoubleScale(-1,1)
        );
    }

    /**
     * Creates an inverted scale conversion with output range reversed.
     *
     * @return a new DoubleScaleConversion configured for inverted [-1, 1] to [1, -1] scaling
     */
    public DoubleScaleConversion makeInvertedConversion()
    {
        return new DoubleScaleConversion
        (
            new DoubleScale(-1,1),
            new DoubleScale(1,-1)
        );
    }

    /**
     * Returns either an inverted or basic conversion based on the input flag.
     *
     * @param invert if true, returns inverted conversion; otherwise returns basic conversion
     * @return the appropriate DoubleScaleConversion instance
     */
    public DoubleScaleConversion invertIfTrue(boolean invert)
    {
        if (invert)
        {
            return makeInvertedConversion();
        }
        else
        {
            return makeBasicConversion();
        }
    }

    /**
     * Retrieves the current wheel conversion settings organized as a 2D array.
     *
     * @return a 2D array where [0] contains left wheels and [1] contains right wheels,
     *         each containing [0] front and [1] back wheel conversions
     */
    public DoubleScaleConversion[][] getWheelConversions()
    {
        return new DoubleScaleConversion[][]
        {
            new DoubleScaleConversion[]
            {
                leftFrontWheelScaleConversion,
                leftBackWheelScaleConversion,
            },
            new DoubleScaleConversion[]
            {
                rightFrontWheelScaleConversion,
                rightBackWheelScaleConversion
            }
        };
    }

    /**
     * Calculates individual wheel speeds from joystick input values.
     *
     * @param joystickValues a 2D array of joystick inputs [[x_values...], [y_values...]]
     * @return a 2D array of calculated wheel speeds [[left_speeds...], [right_speeds...]]
     */
    public double[][] calculate(double[][] joystickValues)
    {
        double[][] values = modifiers.calculate
        (
            joystickValues,
            getWheelConversions(),
            logarithmDrive,
            this.invert
        );
        return values;
    }

    /**
     * Sets wheel conversion settings from a 2D array.
     *
     * @param newConversions a 2D array of DoubleScaleConversion objects
     */
    @SuppressWarnings("unused")
    public void setWheelConversions(DoubleScaleConversion[][] newConversions)
    {
        leftFrontWheelScaleConversion = newConversions[0][0];
        leftBackWheelScaleConversion = newConversions[0][1];
        rightFrontWheelScaleConversion = newConversions[1][0];
        rightBackWheelScaleConversion = newConversions[1][1];
    }

    /**
     * Sets wheel conversion settings from individual min and max scale values for each wheel.
     *
     * @param leftFrontMin minimum value for left front wheel scale
     * @param leftFrontMax maximum value for left front wheel scale
     * @param leftBackMin minimum value for left back wheel scale
     * @param leftBackMax maximum value for left back wheel scale
     * @param rightFrontMin minimum value for right front wheel scale
     * @param rightFrontMax maximum value for right front wheel scale
     * @param rightBackMin minimum value for right back wheel scale
     * @param rightBackMax maximum value for right back wheel scale
     */
    @SuppressWarnings("unused")
    public void setWheelConversions
    (
        double leftFrontMin,
        double leftFrontMax,
        double leftBackMin,
        double leftBackMax,
        double rightFrontMin,
        double rightFrontMax,
        double rightBackMin,
        double rightBackMax
    )
    {
        DoubleScale newLeftFrontScale = new DoubleScale(leftFrontMin, leftFrontMax);
        DoubleScale newLeftBackScale = new DoubleScale(leftBackMin, leftBackMax);
        DoubleScale newRightFrontScale = new DoubleScale(rightFrontMin, rightFrontMax);
        DoubleScale newRightBackScale = new DoubleScale(rightBackMin, rightBackMax);

        DoubleScaleConversion newLeftFrontConversion = new DoubleScaleConversion(newLeftFrontScale);
        DoubleScaleConversion newLeftBackConversion = new DoubleScaleConversion(newLeftBackScale);
        DoubleScaleConversion newRightFrontConversion = new DoubleScaleConversion(newRightFrontScale);
        DoubleScaleConversion newRightBackConversion = new DoubleScaleConversion(newRightBackScale);

        leftFrontWheelScaleConversion = newLeftFrontConversion;
        leftBackWheelScaleConversion = newLeftBackConversion;
        rightFrontWheelScaleConversion = newRightFrontConversion;
        rightBackWheelScaleConversion = newRightBackConversion;
    }

    /**
     * Sets whether logarithmic scaling should be applied to drive values.
     *
     * @param logarithmDrive_input true to enable logarithmic scaling, false to disable
     */
    @SuppressWarnings("unused")
    public void setLogarithmDrive(boolean logarithmDrive_input)
    {
        this.logarithmDrive = logarithmDrive_input;
    }

    /**
     * Gets the current logarithmic scaling setting.
     *
     * @return true if logarithmic scaling is enabled, false otherwise
     */
    @SuppressWarnings("unused")
    public boolean getLogarithmDrive()
    {
        return this.logarithmDrive;
    }

    /**
     * Creates a copy of this TurnedAxisMecanum instance with the same configuration.
     *
     * @return a new TurnedAxisMecanum instance with identical settings
     */
    @SuppressWarnings("unused")
    public TurnedAxisMecanum copy()
    {
        TurnedAxisMecanum copy = new TurnedAxisMecanum
        (
            this.logarithmDrive,
            this.leftFrontWheelScaleConversion,
            this.leftBackWheelScaleConversion,
            this.rightFrontWheelScaleConversion,
            this.rightBackWheelScaleConversion,
            this.invert
        );
        return copy;
    }

    /**
     * Sets whether to invert wheel output.
     *
     * @param invert_input true to invert wheel output, false for normal output
     */
    public void setInvert(boolean invert_input)
    {
        this.invert = invert_input;
    }

    /**
     * Main method demonstrating the TurnedAxisMecanum class functionality.
     * Creates a sample instance with logarithmic scaling enabled and calculates
     * wheel speeds for sample joystick input.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args)
    {
        System.out.println("Turned Axis Mecanum Module");
        TurnedAxisMecanum defaultTurnedAxisMecanum = new TurnedAxisMecanum(false);
        double[][] joystickValues = new double[][] {{.5,.5},{.5,.5}};
        double[][] defaultWheelValues = defaultTurnedAxisMecanum.calculate(joystickValues);
        System.out.println
        (
            "Wheel Values:" +
            defaultWheelValues[0][0] + ", " +
            defaultWheelValues[0][1] + ", " +
            defaultWheelValues[1][0] + ", " +
            defaultWheelValues[1][1]
        );
    }
}