package org.firstinspires.ftc.teamcode.robotTester;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import java.lang.reflect.Array;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import java.util.ArrayList;
import com.qualcomm.robotcore.hardware.DigitalChannelImpl;
import java.util.List;
import java.lang.reflect.Type;
import com.qualcomm.robotcore.hardware.Gamepad;


public class DigitalOutputTest {

   enum OutputState { Output_Low, Output_High, Input_Low, Input_High };

   RobotTester opMode = null;
   int selectedChannelIndex = 0;

   public void performDigitalOutputTest (RobotTester opMode)
   {
      Boolean testDone = false;

      List<DeviceData> channelList = RobotConfig.getDigitalChannels (RobotConfig.DigitalChannelType.All, opMode);
      List<OutputState> channelStateList;

      this.opMode = opMode;
      
      while (!testDone && opMode.opModeIsActive())
      {
         // get current state of each channel
         channelStateList = getChannelStateList (channelList);

         DigitalChannelImpl channel = selectChannel (channelList, channelStateList);

         if (channel == null)
         {
            testDone = true;
         }
         else
         {
            // go to next state
            switch (getChannelState (channel))
            {
               case Output_Low:
                  // go to Output_High state
                  channel.setState (true);
                  break;
               case Output_High:
                  // go to Input_Low state
                  channel.setMode (DigitalChannel.Mode.INPUT);
                  channel.setState (false);
                  break;
               case Input_Low:
               case Input_High:
                  // go to Output_Low state
                  channel.setMode (DigitalChannel.Mode.OUTPUT);
                  channel.setState (false);
                  break;
            }
         }
      }
   }

   private DigitalChannelImpl selectChannel (List<DeviceData> channelList, List<OutputState> channelStateList)
   {
      DigitalChannelImpl channel = null;
      Boolean done = false;
      int localChannelIndex = selectedChannelIndex;

      String headerText = "Select output to cycle between\n" +
                          "   Low, High, and Disabled (input)\n";

      while (!done && opMode.opModeIsActive())
      {
         // get state of each digital channel
         String[] channelStringArray = new String[channelList.size()];

         for (int i = 0; i < channelStringArray.length; i++)
         {
            DeviceData device = channelList.get (i);

            DigitalChannelImpl loopChannel = (DigitalChannelImpl)device.device;

            channelStringArray[i] = (getChannelString (device.name, getChannelState (loopChannel)));
         }

         localChannelIndex = Menu.selectFromMenu (headerText, channelStringArray, localChannelIndex, opMode);

         switch (localChannelIndex)
         {
            case -1:
               channel = null;
               done = true;
               break;
            default:
               channel = (DigitalChannelImpl)channelList.get(localChannelIndex).device;
               selectedChannelIndex = localChannelIndex;
               done = true;
               break;
         }
      }
      
      return channel;
   }

   private OutputState getChannelState (DigitalChannelImpl channel)
   {
      OutputState outputState = OutputState.Input_Low;

      if (channel.getMode () == DigitalChannel.Mode.INPUT)
      {
         if (channel.getState () == false)
         {
            outputState = OutputState.Input_Low;
         }
         else
         {
            outputState = OutputState.Input_High;
         }
      }
      else
      {
         if (channel.getState () == false)
         {
            outputState = OutputState.Output_Low;
         }
         else
         {
            outputState = OutputState.Output_High;
         }
      }

      return outputState;
   }

   private List<OutputState> getChannelStateList (List<DeviceData> channelList)
   {
      List<OutputState> channelStateList = new ArrayList<OutputState>();

      for (DeviceData device : channelList)
      {
         DigitalChannelImpl channel = (DigitalChannelImpl)device.device;

         channelStateList.add (getChannelState (channel));
      }
      
      return channelStateList;
   }

   private String getChannelString (String name, OutputState outputState)
   {
      String channelString = name;

      switch (outputState)
      {
         case Input_Low:
            channelString += " Disabled (Input low)";
            break;
         case Input_High:
            channelString += " Disabled (Input high)";
            break;
         case Output_Low:
            channelString += " Low";
            break;
         case Output_High:
            channelString += " High";
            break;
      }

      return channelString;
   }
   
   
   
}
