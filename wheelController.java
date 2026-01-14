package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


public class wheelController {

    // todo: write your code here
    
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;
    
    public wheelController (DcMotorEx constructor_leftFront, DcMotorEx constructor_rightFront, DcMotorEx constructor_leftBack, DcMotorEx constructor_rightBack) {
        leftFront = constructor_leftFront;
        rightFront = constructor_rightFront;
        leftBack = constructor_leftBack;
        rightBack = constructor_rightBack;
    }
    
    public void act (double velocity_leftFront, double velocity_rightFront, double velocity_leftBack, double velocity_rightBack) {
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setVelocity(velocity_leftFront);
        rightFront.setVelocity(velocity_rightFront);
        leftBack.setVelocity(velocity_leftBack);
        rightBack.setVelocity(velocity_rightBack);
    }
}