package org.firstinspires.ftc.teamcode.robotTester;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Menu {

   public static Telemetry telemetry;
   
   public static void drawMenu (String headerText, String[] menuList, int selectedIndex)
   {
      String caption;
      
      if (headerText != "")
      {
         telemetry.addLine (headerText);
      }

      for (int index = 0; index < menuList.length; ++index)
      {
         if (index == selectedIndex)
         {
            caption = ">";
         }
         else
         {
            caption = "";
         }

         telemetry.addData (caption, menuList[index]);
      }
      
      telemetry.update ();
   }
   
   public static void drawMenu (String[] menuList, int selectedIndex)
   {
      drawMenu ("", menuList, selectedIndex);
   }

   public static int selectFromMenu (String headerText, String[] menuList, int selectedIndex, RobotTester opMode)
   {
      GamepadButtons.ButtonType button;
      int currentIndex = selectedIndex;
      Boolean done = false;

      while (!done && opMode.opModeIsActive())
      {
         drawMenu (headerText, menuList, currentIndex);
         button = opMode.gamepadButtons1.waitForButton ();
         
         switch (button)
         {
            case Dpad_Up:
            case Y:
               currentIndex = Math.max(currentIndex - 1, 0);
               break;
            case Dpad_Down:
            case A:
               currentIndex = Math.min(currentIndex + 1, menuList.length - 1);
               break;
            case Dpad_Right:
            case B:
               done = true;
               break;
            case Dpad_Left:
            case X:
               currentIndex = -1;
               done = true;
               break;
            default:
               break;
         }
      }
      
      return currentIndex;
   }

   public static int selectFromMenu (String[] menuList, int selectedIndex, RobotTester opMode)
   {
      return selectFromMenu ("", menuList, selectedIndex, opMode);
   }

   public static int selectFromMenu (String headerText, List<DeviceData> menuList, int selectedIndex, RobotTester opMode)
   {
      int index = 0;
      String[] menuStrings = new String[menuList.size()];
      
      // get list of menu items from DeviceData list
      for (DeviceData device : menuList) {
         menuStrings[index] = device.name;
         ++index;
      }

      return selectFromMenu (headerText, menuStrings, selectedIndex, opMode);
   }

   public static int selectFromMenu (List<DeviceData> menuList, int selectedIndex, RobotTester opMode)
   {
      return selectFromMenu ("", menuList, selectedIndex, opMode);
   }
}
