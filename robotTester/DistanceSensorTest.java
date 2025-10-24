package org.firstinspires.ftc.teamcode.robotTester;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class DistanceSensorTest {

   RobotTester opMode = null;

   DistanceSensor sensor;

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
      List<DeviceData> sensorList = RobotConfig.getDistanceSensors (opMode);

      if (sensorList.size() == 0)
      {
         opMode.telemetry.addData("", "No Distance Sensors in robot configuration");
         opMode.telemetry.update();
         return;
      }

      for (DeviceData device : sensorList)
      {
         DistanceSensor sensor = (DistanceSensor)device.device;

         opMode.telemetry.addLine()
              .addData(device.name, "%.1f inch", sensor.getDistance(DistanceUnit.INCH));
      }

      opMode.telemetry.update();
   }

}
