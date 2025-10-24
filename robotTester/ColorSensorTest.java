package org.firstinspires.ftc.teamcode.robotTester;

import java.util.List;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;


public class ColorSensorTest {

   RobotTester opMode = null;

   ColorSensor sensor;

   public void performTest (RobotTester opMode)
   {
      GamepadButtons.ButtonType button;
      Boolean testDone = false;

      while (!testDone && opMode.opModeIsActive())
      {
         // display sensor settings
         displaySensorValues (opMode);

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
         
         opMode.sleep(200);
      }
   }

   private void displaySensorValues (LinearOpMode opMode)
   {
      List<DeviceData> sensorList = RobotConfig.getColorSensors (opMode);

      if (sensorList.size() == 0)
      {
         opMode.telemetry.addData("", "No Color Sensors in robot configuration");
         opMode.telemetry.update();
         return;
      }

      for (DeviceData device : sensorList)
      {
         NormalizedColorSensor sensor = (NormalizedColorSensor)device.device;

         // Get the normalized colors from the sensor
         NormalizedRGBA colors = sensor.getNormalizedColors();

         opMode.telemetry.addLine()
              .addData(device.name, "")
              .addData("Red", "%.3f", colors.red)
              .addData("Green", "%.3f", colors.green)
              .addData("Blue", "%.3f", colors.blue);
      }

      opMode.telemetry.update();
   }

}
