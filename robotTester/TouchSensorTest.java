package org.firstinspires.ftc.teamcode.robotTester;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DigitalChannelImpl;
import com.qualcomm.robotcore.hardware.TouchSensor;
import java.util.List;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.reflect.Type;
import com.qualcomm.robotcore.hardware.Gamepad;


public class TouchSensorTest {

   String[] testList = new String[]{
      "Joystick control",
      "0/1 control",
   };

   String[] joystickList = new String[]{
      "Joystick y",
      "Value",
   };

   RobotTester opMode = null;
   int selectedIndex = 0;
   int selectedServoIndex = 0;

   public void performTouchSensorTest (RobotTester opMode)
   {
      GamepadButtons.ButtonType button;
      Boolean done = false;

      this.opMode = opMode;
      
      selectedIndex = 0;
      
      Boolean testDone = false;

      while (!testDone && opMode.opModeIsActive())
      {
         // display sensor settings
         displayTouchSensorValues (opMode);

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

   private void displayTouchSensorValues (LinearOpMode opMode)
   {
      List<DeviceData> deviceList = RobotConfig.getDigitalChannels (RobotConfig.DigitalChannelType.TouchSensor, opMode);

      for (DeviceData device : deviceList)
      {
         // if device is listed in the configuration as a touch sensor
         if (device.device instanceof TouchSensor)
         {
            TouchSensor sensor = (TouchSensor)device.device;
            opMode.telemetry.addData(device.name, sensor.isPressed ());
         }
         else if (device.device instanceof DigitalChannelImpl)
         {
            DigitalChannelImpl sensor = (DigitalChannelImpl)device.device;

            if (sensor.getMode () == DigitalChannel.Mode.INPUT)
            {
               // Note that when using DigitalChannelImpl instead of TouchSensor,
               // touch sensor pressed is the false state, so invert it (!)
               opMode.telemetry.addData(device.name, !sensor.getState ());
            }
            else
            {
               // this is an output so it is not a touch sensor
               opMode.telemetry.addData(device.name, "[output]");
            }
         }
         else
         {
            opMode.telemetry.addData(device.name, "unknown");
         }
      }
      opMode.telemetry.update();
   }
}
