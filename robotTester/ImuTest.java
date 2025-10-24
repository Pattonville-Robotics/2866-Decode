package org.firstinspires.ftc.teamcode.robotTester;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
import java.text.DecimalFormat;
import java.util.List;


public class ImuTest {

   RobotTester opMode = null;
   int selectedIndex = 0;
   
   IMU commonImu;

   // print values to 1 decimal place
   DecimalFormat df = new DecimalFormat ("#.#");

   public void performTest (RobotTester opMode)
   {
      /* The next two lines define Hub orientation.
      * The Default Orientation (shown) is when a hub is mounted horizontally with the printed logo pointing UP and the USB port pointing FORWARD.
      *
      * To Do:  EDIT these two lines to match YOUR mounting configuration.
      */
      RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
      RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.RIGHT;
      RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

      this.opMode = opMode;
      
      try {
         // Now initialize the Control Hub IMU with this mounting orientation
         // This sample expects the IMU to be in a REV Hub and named "imu".
         commonImu = opMode.hardwareMap.get(IMU.class, "imu");
         commonImu.initialize(new IMU.Parameters(orientationOnRobot));
      } catch(Exception e) {
      }

      // try to initialize an expansion hub IMU. If there is not an expansion hub,
      // the catch will just ignore any errors.
      try {
         // Now initialize the Expansion Hub IMU with this mounting orientation
         // This sample expects the IMU to be in a REV Hub and named "imu".
         commonImu = opMode.hardwareMap.get(IMU.class, "imu2");
         commonImu.initialize(new IMU.Parameters(orientationOnRobot));
      } catch(Exception e) {
      }

      performAnglesTest ();
   }

   private void performAnglesTest ()
   {
      GamepadButtons.ButtonType button;
      Boolean testDone = false;
      int index = 0;

      List<DeviceData> imuList = RobotConfig.getIMUs (opMode);

      // 6 lines of text per IMU
      String[] textList = new String[imuList.size () * 6];

      while (!testDone && opMode.opModeIsActive())
      {
         index = 0;

         // get list of IMUs from DeviceData list
         for (DeviceData device : imuList)
         {
            YawPitchRollAngles orientation = ((IMU)device.device).getRobotYawPitchRollAngles();

            // print 6 lines for each IMU
            textList[index++] = device.name;
            textList[index++] = "";
            textList[index++] = "Yaw (Heading): " + df.format (orientation.getYaw(AngleUnit.DEGREES));
            textList[index++] = "Pitch: " + df.format (orientation.getPitch(AngleUnit.DEGREES));
            textList[index++] = "Roll:  " + df.format (orientation.getRoll(AngleUnit.DEGREES));
            textList[index++] = "";
         }

         // -100 prevents menu selection cursor
         Menu.drawMenu (textList, -100);
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

}
