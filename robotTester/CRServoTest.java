package org.firstinspires.ftc.teamcode.robotTester;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.text.DecimalFormat;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import java.util.List;
import java.lang.reflect.Type;


public class CRServoTest {

   String[] testList = new String[]{
      "Joystick control",
   };

   String[] joystickList = new String[]{
      "Joystick y",
      "Value",
   };

   RobotTester opMode = null;
   int selectedIndex = 0;
   int selectedServoIndex = 0;

   // print values to 3 decimal places
   DecimalFormat df = new DecimalFormat ("#.###");

   public void performServoTest (RobotTester opMode)
   {
      Boolean done = false;

      this.opMode = opMode;
      
      selectedIndex = 0;
      
      while (!done && opMode.opModeIsActive())
      {
         selectedIndex = Menu.selectFromMenu (testList, selectedIndex, opMode);

         switch (selectedIndex)
         {
            case -1:
               done = true;
               break;
            case 0:
               performJoystickTest ();
               break;
         }
      }
   }

   private void performJoystickTest ()
   {
      GamepadButtons.ButtonType button;
      Boolean done = false;

      while (!done && opMode.opModeIsActive())
      {
         CRServo servo = selectServo ();
      
         if (servo == null)
         {
            done = true;
         }
         else
         {
            Boolean testDone = false;

            while (!testDone && opMode.opModeIsActive())
            {
               servo.setPower (Math.abs(opMode.gamepad1.right_stick_y) * opMode.gamepad1.right_stick_y);
               
               joystickList[0] = "Joystick y: " + df.format(opMode.gamepad1.right_stick_y);
               joystickList[1] = "Power: " + df.format(servo.getPower());
               Menu.drawMenu (joystickList, 0);
               button = opMode.gamepadButtons1.getButton ();
            
               switch (button)
               {
                  case Dpad_Left:
                  case X:
                     testDone = true;
                     break;
                  default:
                     break;
               }
            }
         }
      }
   }

   private CRServo selectServo ()
   {
      CRServo servo = null;
      Boolean done = false;
      int localServoIndex = selectedServoIndex;

      List<DeviceData> servoList = RobotConfig.getCRServos (opMode);

      while (!done && opMode.opModeIsActive())
      {
         localServoIndex = Menu.selectFromMenu (servoList, localServoIndex, opMode);

         switch (localServoIndex)
         {
            case -1:
               servo = null;
               done = true;
               break;
            default:
               servo = (CRServo)servoList.get(localServoIndex).device;
               selectedServoIndex = localServoIndex;
               done = true;
               break;
         }
      }
      
      return servo;
   }
}
