package org.firstinspires.ftc.teamcode.robotTester;

import com.qualcomm.robotcore.hardware.RobotCoreLynxModule;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;


public class HubTest {

   RobotTester opMode = null;
   int selectedIndex = 0;
   ImuTest imuTest = new ImuTest ();

   // print values to 3 decimal places
   DecimalFormat df = new DecimalFormat ("#.###");

   public void performHubTest (RobotTester opMode)
   {
      GamepadButtons.ButtonType button;
      Boolean done = false;
      
      List<DeviceData> hubList = RobotConfig.getVoltageSensors (opMode);

      this.opMode = opMode;
      
      int numHubs = hubList.size();
      int numTests = 2;
      String[] testList = new String[numHubs + numTests + 1];
      
      while (!done && opMode.opModeIsActive())
      {
         int index = 0;

         testList[index++] = "IMU Test";
         testList[index++] = "Debug Hardware Map Info";
         testList[index++] = "";

         // get list of menu items from DeviceData list
         for (DeviceData device : hubList) {
            testList[index++] = device.name + "  Voltage " + df.format (((VoltageSensor)device.device).getVoltage ());
         }

         Menu.drawMenu (testList, selectedIndex);

         // use getButton instead of waitForButton to continuously update the voltage
         button = opMode.gamepadButtons1.getButton ();
         
         switch (button)
         {
            case Dpad_Up:
            case Y:
               selectedIndex = Math.max(selectedIndex - 1, 0);
               break;
            case Dpad_Down:
            case A:
               selectedIndex = Math.min(selectedIndex + 1, numTests - 1);
               break;
            case Dpad_Right:
            case B:
               switch (selectedIndex)
               {
                  case 0:
                     imuTest.performTest(opMode);
                     break;
                  case 1:
                     printHubInfo ();

                     // wait for any button
                     button = opMode.gamepadButtons1.waitForButton ();

                     break;
               }
               break;
            case Dpad_Left:
            case X:
               done = true;
               break;
            default:
               break;
         }
      }
   }

   public void printHubInfo ()
   {
      int size = opMode.hardwareMap.size();
      opMode.telemetry.addData("size", size);

      // loop through each item in the hardware map
      for (Iterator<HardwareDevice> iter = opMode.hardwareMap.iterator(); iter.hasNext(); )
      {
        HardwareDevice element = iter.next();

        opMode.telemetry.addData("name", opMode.hardwareMap.getNamesOf(element).iterator().next());
        opMode.telemetry.addData("item", element.getDeviceName() + "(" + element.getClass().getName());
        opMode.telemetry.addData("ConnInfo", (element.getConnectionInfo()));
        opMode.telemetry.addData("", "");
      }

      opMode.telemetry.update ();
   }

}
