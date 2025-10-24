package org.firstinspires.ftc.teamcode.robotTester;

import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.HashMap;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.List;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.reflect.Type;
import com.qualcomm.robotcore.hardware.Gamepad;


public class ServoTest {

   String[] testList = new String[]{
      "Joystick control",
      "",
      "WARNING: Servo may move when starting test!",
   };

   String[] joystickList = new String[]{
      "Joystick y",
      "Value",
   };

   RobotTester opMode = null;
   int selectedIndex = 0;
   int selectedServoIndex = 0;

   // list of servos that have been initialized
   List<String> servoInitList = new ArrayList<>();

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
      // Joystick at 0 (center) keeps servo at same position.
      // Up moves servo one way, down the other

      GamepadButtons.ButtonType button;
      Boolean done = false;
      float joystickValue;

      while (!done && opMode.opModeIsActive())
      {
         Servo servo = selectServo ();
      
         if (servo == null)
         {
            done = true;
         }
         else
         {
            // set initial position if not already set
            initializeServo (servo);

            Boolean testDone = false;
            Double currentPosition = servo.getPosition();

            while (!testDone && opMode.opModeIsActive())
            {
               joystickValue = opMode.gamepad1.right_stick_y;

               // make joystick more sensitive at lower values
               if (joystickValue > 0)
               {
                  joystickValue = joystickValue * joystickValue;
               }
               else
               {
                  // negative value
                  joystickValue = -joystickValue * joystickValue;
               }

               currentPosition += joystickValue * 0.005;

               if (currentPosition > 1.0) currentPosition = 1.0;
               if (currentPosition < 0.0) currentPosition = 0.0;

               servo.setPosition (currentPosition);
               
               joystickList[0] = "Joystick y: " + df.format(opMode.gamepad1.right_stick_y);
               joystickList[1] = "Value: " + df.format(servo.getPosition());
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

               opMode.sleep (20);
            }
         }
      }
   }

   private void initializeServo (Servo servo)
   {
      // if servo has not been initialized, set it to middle value and add to list
      // of initialized servos
      if (!servoInitList.contains (servo.getConnectionInfo ()))
      {
         servo.setPosition (0.5);
         servoInitList.add (servo.getConnectionInfo ());
      }
   }

   private Servo selectServo ()
   {
      Servo servo = null;
      Boolean done = false;
      int localServoIndex = selectedServoIndex;

      List<DeviceData> servoList = RobotConfig.getServos (opMode);

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
               servo = (Servo)servoList.get(localServoIndex).device;
               selectedServoIndex = localServoIndex;
               done = true;
               break;
         }
      }
      
      return servo;
   }
}
