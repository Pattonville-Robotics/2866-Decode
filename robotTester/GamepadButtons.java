package org.firstinspires.ftc.teamcode.robotTester;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


public class GamepadButtons {

   enum ButtonType {
      None,
      A,
      B,
      X,
      Y,
      Dpad_Up,
      Dpad_Down,
      Dpad_Right,
      Dpad_Left,
      Left_Stick_X,
      Left_Stick_Y,
      Right_Stick_X,
      Right_Stick_Y,
      Left_Stick_Button,
      Right_Stick_Button,
      Left_Bumper,
      Right_Bumper,
      Left_Trigger,
      Right_Trigger,
      Start,
      Guide,
      Back,
      Touchpad
   }

   Gamepad gamepad;
   LinearOpMode opMode;

   Boolean a_state = false;
   Boolean b_state = false;
   Boolean x_state = false;
   Boolean y_state = false;
   Boolean dpad_up_state = false;
   Boolean dpad_down_state = false;
   Boolean dpad_right_state = false;
   Boolean dpad_left_state = false;
   double left_stick_x_state = 0.0;
   double left_stick_y_state = 0.0;
   double right_stick_x_state = 0.0;
   double right_stick_y_state = 0.0;
   Boolean left_stick_button_state = false;
   Boolean right_stick_button_state = false;
   Boolean left_bumper_state = false;
   Boolean right_bumper_state = false;
   double left_trigger_state = 0.0;
   double right_trigger_state = 0.0;
   Boolean start_state = false;
   Boolean guide_state = false;
   Boolean back_state = false;
   Boolean touchpad_state = false;

   // constructor
   public GamepadButtons (Gamepad gamepad, LinearOpMode opMode)
   {
      this.gamepad = gamepad;
      this.opMode = opMode;
   }

   private void updateButtonStates (Gamepad gamepad)
   {
      // save the state of each button to check for changes between calls
      a_state = gamepad.a;
      b_state = gamepad.b;
      x_state = gamepad.x;
      y_state = gamepad.y;
      dpad_up_state = gamepad.dpad_up;
      dpad_down_state = gamepad.dpad_down;
      dpad_right_state = gamepad.dpad_right;
      dpad_left_state = gamepad.dpad_left;
      left_stick_x_state = gamepad.left_stick_x;
      left_stick_y_state = gamepad.left_stick_y;
      right_stick_x_state = gamepad.right_stick_x;
      right_stick_y_state = gamepad.right_stick_y;
      left_stick_button_state = gamepad.left_stick_button;
      right_stick_button_state = gamepad.right_stick_button;
      left_bumper_state = gamepad.left_bumper;
      right_bumper_state = gamepad.right_bumper;
      left_trigger_state = gamepad.left_trigger;
      right_trigger_state = gamepad.right_trigger;
      start_state = gamepad.start;
      guide_state = gamepad.guide;
      back_state = gamepad.back;
      touchpad_state = gamepad.touchpad;
   }

   public ButtonType getButton ()
   {
      ButtonType button = ButtonType.None;

      Gamepad gamepadCopy = new Gamepad ();

      // make a copy of current state of the gamepad so buttons are consistent
      // throughout the update
      gamepadCopy.copy (gamepad);

      if (gamepadCopy.a == true && a_state == false)
      {
        button = ButtonType.A;
      }
      else if (gamepadCopy.b == true && b_state == false)
      {
        button = ButtonType.B;
      }
      else if (gamepadCopy.x == true && x_state == false)
      {
        button = ButtonType.X;
      }
      else if (gamepadCopy.y == true && y_state == false)
      {
        button = ButtonType.Y;
      }
      else if (gamepadCopy.dpad_up == true && dpad_up_state == false)
      {
        button = ButtonType.Dpad_Up;
      }
      else if (gamepadCopy.dpad_down == true && dpad_down_state == false)
      {
        button = ButtonType.Dpad_Down;
      }
      else if (gamepadCopy.dpad_right == true && dpad_right_state == false)
      {
        button = ButtonType.Dpad_Right;
      }
      else if (gamepadCopy.dpad_left == true && dpad_left_state == false)
      {
        button = ButtonType.Dpad_Left;
      }
      else if (gamepadCopy.left_stick_x != left_stick_x_state)
      {
        button = ButtonType.Left_Stick_X;
      }
      else if (gamepadCopy.left_stick_y != left_stick_y_state)
      {
        button = ButtonType.Left_Stick_Y;
      }
      else if (gamepadCopy.right_stick_x != right_stick_x_state)
      {
        button = ButtonType.Right_Stick_X;
      }
      else if (gamepadCopy.right_stick_y != right_stick_y_state)
      {
        button = ButtonType.Right_Stick_Y;
      }
      else if (gamepadCopy.left_stick_button == true && left_stick_button_state == false)
      {
        button = ButtonType.Left_Stick_Button;
      }
      else if (gamepadCopy.right_stick_button == true && right_stick_button_state == false)
      {
        button = ButtonType.Right_Stick_Button;
      }
      else if (gamepadCopy.left_bumper == true && left_bumper_state == false)
      {
        button = ButtonType.Left_Bumper;
      }
      else if (gamepadCopy.right_bumper == true && right_bumper_state == false)
      {
        button = ButtonType.Right_Bumper;
      }
      else if (gamepadCopy.left_trigger != left_trigger_state)
      {
        button = ButtonType.Left_Trigger;
      }
      else if (gamepadCopy.right_trigger != right_trigger_state)
      {
        button = ButtonType.Right_Trigger;
      }
      else if (gamepadCopy.start == true && start_state == false)
      {
        button = ButtonType.Start;
      }
      else if (gamepadCopy.guide == true && guide_state == false)
      {
        button = ButtonType.Guide;
      }
      else if (gamepadCopy.back == true && back_state == false)
      {
        button = ButtonType.Back;
      }
      else if (gamepadCopy.touchpad == true && touchpad_state == false)
      {
        button = ButtonType.Touchpad;
      }

      updateButtonStates(gamepadCopy);
      
      return button;
   }

   public ButtonType waitForButton ()
   {
      ButtonType button = ButtonType.None;
      Boolean done = false;

      while (!done && opMode.opModeIsActive ())
      {
         button = getButton ();
         
         if (button != ButtonType.None)
         {
           done = true;
         }
      }

      return button;      
   }

   public void waitForNoButtonsPressed ()
   {
      ButtonType button = ButtonType.None;
      Boolean done = false;

      while (!done && opMode.opModeIsActive ())
      {
         button = getButton ();
         
         if (button == ButtonType.None)
         {
           done = true;
         }
      }
   }

}
