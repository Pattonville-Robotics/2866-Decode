package org.firstinspires.ftc.teamcode.robotTester;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;

@TeleOp
public class CheckEncoders extends LinearOpMode {

   @Override
   public void runOpMode() {
   
      boolean passed = false;
      String results = "";
      List<DcMotor> motorList;

      telemetry.addData ("This will test each motor encoder ", "");
      telemetry.addData ("and display Pass/Fail for each one", "");
      telemetry.update ();

      waitForStart();

      // get list of motors
      motorList = hardwareMap.getAll(DcMotor.class);

      for (DcMotor motor : motorList) {
      
         // name = first in set
         String name = hardwareMap.getNamesOf(motor).iterator().next();

         // reset encoder to 0 and run using power setting, can still read encoder value
         motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

         // test the encoder by running the motor slightly
         motor.setPower(0.1);
         sleep(50);
         motor.setPower(0.0);
         sleep(100);

         if (motor.getCurrentPosition() != 0)
         {
            passed = true;
            results = "Passed";
         }
         else
         {
            passed = false;
            results = "Failed";
         }

         // set motor back to original position
         motor.setPower(-0.1);
         sleep(50);
         motor.setPower(0.0);

         // if it didn't change
         if (passed == false)
         {
            // use a higher power setting on the second try
            motor.setPower(0.2);
            sleep(50);
            motor.setPower(0.0);
            sleep(100);

            if (motor.getCurrentPosition() != 0)
            {
               passed = true;
               results = "Passed 2nd try";
            }
            else
            {
               passed = false;
               results = "Failed";
            }

            // set motor back to original position
            motor.setPower(-0.2);
            sleep(50);
            motor.setPower(0.0);
         }

         telemetry.addData (name, results);
      }

      telemetry.update ();

      // wait a long time (10 minutes)
      sleep (600000);
   }
   
}
