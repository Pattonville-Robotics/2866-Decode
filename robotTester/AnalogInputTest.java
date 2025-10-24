package org.firstinspires.ftc.teamcode.robotTester;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import java.util.List;
import java.lang.reflect.Type;
import com.qualcomm.robotcore.hardware.Gamepad;


public class AnalogInputTest {

   RobotTester opMode = null;

   public void performAnalogInputTest (RobotTester opMode)
   {
      GamepadButtons.ButtonType button;

      this.opMode = opMode;
      
      Boolean testDone = false;

      while (!testDone && opMode.opModeIsActive())
      {
         // display sensor settings
         displayAnalogInputValues (opMode);

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

   private void displayAnalogInputValues (LinearOpMode opMode)
   {
      List<DeviceData> deviceList = RobotConfig.getAnalogInputs (opMode);

      for (DeviceData device : deviceList)
      {
         AnalogInput sensor = (AnalogInput )device.device;

         opMode.telemetry.addData(device.name, sensor.getVoltage ());
      }
      opMode.telemetry.update();
   }
}
