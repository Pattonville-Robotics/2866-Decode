/*
Copyright 2025 FIRST Tech Challenge Team 2866B

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains a minimal example of a Linear "OpMode". An OpMode is a 'program' that runs
 * in either the autonomous or the TeleOp period of an FTC match. The names of OpModes appear on
 * the menu of the FTC Driver Station. When an selection is made from the menu, the corresponding
 * OpMode class is instantiated on the Robot Controller and executed.
 *
 * Remove the @Disabled annotation on the next line or two (if present) to add this OpMode to the
 * Driver Station OpMode list, or add a @Disabled annotation to prevent this OpMode from being
 * added to the Driver Station.
 */
@Autonomous

public class phillip extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotorEx kebab = null; // this is the launcher thingamajig
    private DcMotorEx intake = null;
    private double kebabSpeed = 0.0;
    private final double COUNTS_PER_MOTOR_REV = 537.7;
    private final double WHEEL_DIAMETER_INCHES = 2.95;
    private final double PI = 3.1415;
    private final double COUNTS_PER_INCH=COUNTS_PER_MOTOR_REV / (WHEEL_DIAMETER_INCHES * PI);
    
    private void drivestraight(double distance){
        int moveCounts = (int)(distance * COUNTS_PER_INCH);
        int leftFrontTarget = leftFrontDrive.getCurrentPosition() + moveCounts;
        int rightFrontTarget = rightFrontDrive.getCurrentPosition() + moveCounts;
        int leftBackTarget = leftBackDrive.getCurrentPosition() + moveCounts;
        int rightBackTarget = rightBackDrive.getCurrentPosition() + moveCounts;
        
        telemetry.addData("leftFrontTarget", "%d", leftFrontTarget);
        telemetry.update();
        
        leftFrontDrive.setTargetPosition(leftFrontTarget);
        rightFrontDrive.setTargetPosition(rightFrontTarget);
        leftBackDrive.setTargetPosition(leftFrontTarget);
        rightBackDrive.setTargetPosition(rightFrontTarget);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        leftFrontDrive.setPower(.5);
        rightFrontDrive.setPower(.5);
        leftBackDrive.setPower(.5);
        rightBackDrive.setPower(.5);
        
        while (opModeIsActive() &&
               (leftFrontDrive.isBusy() && 
               rightFrontDrive.isBusy() && 
               leftBackDrive.isBusy() && 
               rightBackDrive.isBusy()
               )) {
           //waiting for drives to finish
           telemetry.addData("Driving:", "Yes");
           telemetry.addData("CPI:", "%5.2f", COUNTS_PER_INCH);
           telemetry.addData("LFP:", "%d", leftFrontDrive.getCurrentPosition());
           telemetry.addData("leftFrontTarget", "%d", leftFrontTarget);
           telemetry.update();
           sleep(1);
        }
        
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
        
        
        telemetry.addData("Driving:", "No");
        telemetry.update();
        
        
    }

    @Override
    public void runOpMode() {

        leftFrontDrive  = hardwareMap.get(DcMotor.class, "left_front_drive");
        leftBackDrive  = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
        kebab = hardwareMap.get(DcMotorEx.class, "kebab_launcher");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
 
        telemetry.addData("Status", "Ready to play");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        
        // Set the encoders for closed loop speed control, and reset the heading.
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       
        
        // lifespan code
        //for (int i = 0; i < 100; i++) {
        //leftFrontDrive.setPower(.7);
        // leftBackDrive.setPower(.7);
        // rightFrontDrive.setPower(1);
        // rightBackDrive.setPower(1);
        // sleep(3000);
        // leftFrontDrive.setPower(0);
        // leftBackDrive.setPower(0);
        // rightFrontDrive.setPower(.5);
        // rightBackDrive.setPower(0);

        
        drivestraight(85);
        
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        leftFrontDrive.setPower(-.5);
        leftBackDrive.setPower(-.5);
        rightFrontDrive.setPower(.5);
        rightBackDrive.setPower(.5);
        sleep(600);
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
        sleep(300);
        
        telemetry.addData("Status", "Driving straight");
        telemetry.update();
        kebab.setVelocity(-2150);
        sleep(4000);
        intake.setVelocity(-2000);
        sleep(3500);
        kebab.setPower(0);
        intake.setPower(0);
        sleep(4000);
        
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        
        leftFrontDrive.setPower(.5);
        leftBackDrive.setPower(.5);
        rightFrontDrive.setPower(-.5);
        rightBackDrive.setPower(-.5);
        sleep(600);
        leftFrontDrive.setPower(.5);
        leftBackDrive.setPower(.5);
        rightFrontDrive.setPower(.5);
        rightBackDrive.setPower(.5);
        sleep(1500);
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
        
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Waiting");
            telemetry.update();

        }
    }
}
