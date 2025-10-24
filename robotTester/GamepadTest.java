package org.firstinspires.ftc.teamcode.robotTester;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadTest {

   RobotTester opMode = null;
   int selectedIndex = 0;
   int selectedServoIndex = 0;

   public void performGamepadTest (RobotTester opMode)
   {
      GamepadButtons.ButtonType button;
      Boolean done = false;
      Boolean gamepad2Connected = (opMode.gamepad2.getGamepadId () > 0);
      String gamepadName = "";
      String gamepad1Name = "";
      Gamepad testGamepad;

      this.opMode = opMode;
      
      // if 2 gamepads connected, then need to display the name of each one
      if (gamepad2Connected)
      {
         gamepad1Name = " gamepad1";
      }

      opMode.telemetry.addData("Press gamepad buttons to test", "Dpad Left to exit");
      opMode.telemetry.update();

      while (!done && opMode.opModeIsActive())
      {
         testGamepad = opMode.gamepad1;
         button = opMode.gamepadButtons1.getButton ();

         if (button == GamepadButtons.ButtonType.None)
         {
            testGamepad = opMode.gamepad2;
            button = opMode.gamepadButtons2.getButton ();
            gamepadName = " gamepad2";
         }
         else
         {
            gamepadName = gamepad1Name;
         }
         
         switch (button)
         {
            case Left_Stick_X:
            case Left_Stick_Y:
               opMode.telemetry.addData(button.toString(), "");
               opMode.telemetry.addData("X", testGamepad.left_stick_x);
               opMode.telemetry.addData("Y", testGamepad.left_stick_y);
               opMode.telemetry.update();
               break;
            case Right_Stick_X:
            case Right_Stick_Y:
               opMode.telemetry.addData(button.toString() + gamepadName, "");
               opMode.telemetry.addData("X", testGamepad.right_stick_x);
               opMode.telemetry.addData("Y", testGamepad.right_stick_y);
               opMode.telemetry.update();
               break;
            case Left_Trigger:
               opMode.telemetry.addData(button.toString() + gamepadName, testGamepad.left_trigger);
               opMode.telemetry.update();
               break;
            case Right_Trigger:
               opMode.telemetry.addData(button.toString() + gamepadName, testGamepad.right_trigger);
               opMode.telemetry.update();
               break;
            default:
               if (button != GamepadButtons.ButtonType.None)
               {
                  opMode.telemetry.addData(button.toString() + gamepadName, "");
                  opMode.telemetry.update();

               // if gamepad 1 Dpad Left, exit after 1 second
               if (gamepadName == gamepad1Name  && button == GamepadButtons.ButtonType.Dpad_Left)
               {
                  opMode.sleep (1000);
                  done = true;
                  break;
               }
               // don't break, continue into normal button processing below
               }
               break;
         }
      }
   }

}
