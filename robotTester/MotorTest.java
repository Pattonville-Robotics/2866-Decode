package org.firstinspires.ftc.teamcode.robotTester;

import com.qualcomm.robotcore.hardware.DcMotorController;
import java.text.DecimalFormat;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.hardware.HardwareDeviceManager;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import java.util.List;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImpl;
import java.lang.reflect.Type;
import com.qualcomm.robotcore.hardware.Gamepad;


public class MotorTest {

   String[] testList = new String[]{
      "Joystick control",
      "Encoder wiggle test",
      "Encoder readout"
   };

   String[] joystickList = new String[]{
      "Joystick y",
      "Power",
      "Encoder"
   };

   RobotTester opMode = null;
   int selectedIndex = 0;
   int selectedMotorIndex = 0;

   // print values to 2 decimal places
   DecimalFormat df = new DecimalFormat ("#.##");

   public void performMotorTest (RobotTester opMode)
   {
      Boolean Wiggle = true;
      Boolean NoWiggle = false;

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
            case 1:
               performEncoderTest (Wiggle);
               break;
            case 2:
               performEncoderTest (NoWiggle);
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
         DcMotor motor = selectMotor ();
      
         if (motor == null)
         {
            done = true;
         }
         else
         {
            Boolean testDone = false;

            while (!testDone && opMode.opModeIsActive())
            {
               // make joystick more sensitive at lower values
               motor.setPower (Math.abs(opMode.gamepad1.right_stick_y) * opMode.gamepad1.right_stick_y);
               
               joystickList[0] = "Joystick y: " + df.format(opMode.gamepad1.right_stick_y);
               joystickList[1] = "Power: " + df.format(motor.getPower());
               joystickList[2] = "Encoder: " + motor.getCurrentPosition();
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

   private void performEncoderTest (Boolean wiggle)
   {
      GamepadButtons.ButtonType button;
      Boolean done = false;
      int previousEncoder = 0;
      int currentEncoder = 0;
      

      while (!done && opMode.opModeIsActive())
      {
         DcMotor motor = selectMotor ();
      
         if (motor == null)
         {
            done = true;
         }
         else
         {
            Boolean testDone = false;

            if (wiggle)
            {
               previousEncoder = motor.getCurrentPosition();

               motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
               motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
               motor.setPower(0.2);
               opMode.sleep(50);
               motor.setPower(0.0);
            }

            while (!testDone && opMode.opModeIsActive())
            {
               currentEncoder = motor.getCurrentPosition();
               if (wiggle)
               {
                  joystickList[0] = "Encoder: " + previousEncoder + " -> " + currentEncoder;
                  
                  // if encoder value didn't change
                  if (previousEncoder == currentEncoder)
                  {
                     joystickList[2] = "Test Failed";
                  }
                  else
                  {
                     joystickList[2] = "Test Passed";
                  }
               }
               else
               {
                  joystickList[0] = "Encoder: " + currentEncoder;
                  joystickList[2] = "";
               }
               joystickList[1] = "";
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

   private DcMotor selectMotor ()
   {
      DcMotor motor = null;
      Boolean done = false;
      int localMotorIndex = selectedMotorIndex;

      List<DeviceData> motorList = RobotConfig.getMotors (opMode);

      while (!done && opMode.opModeIsActive())
      {
         localMotorIndex = Menu.selectFromMenu (motorList, localMotorIndex, opMode);

         switch (localMotorIndex)
         {
            case -1:
               motor = null;
               done = true;
               break;
            default:
               motor = (DcMotor)motorList.get(localMotorIndex).device;
               selectedMotorIndex = localMotorIndex;
               done = true;
               break;
         }
      }
      
      return motor;
   }
}