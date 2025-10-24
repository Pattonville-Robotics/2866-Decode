package org.firstinspires.ftc.teamcode.robotTester;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import java.lang.reflect.Array;
import java.util.ArrayList;
import com.qualcomm.robotcore.hardware.DigitalChannelImpl;
import java.util.List;
import java.lang.reflect.Type;


public class LEDTest {

   enum OutputState { Output_Low, Output_High, Input_Low, Input_High, Unknown };

   OutputState outputState[];
   RobotTester opMode = null;
   int selectedChannelIndex = 0;

   public void performTest (RobotTester opMode)
   {
      Boolean testDone = false;

      List<DeviceData> channelList = RobotConfig.getDigitalChannels (RobotConfig.DigitalChannelType.LED, opMode);
      List<OutputState> channelStateList;

      this.opMode = opMode;
      
      outputState = new OutputState[channelList.size()];

      // initialize output state to unknown
      for (int index = 0; index < outputState.length; index++)
      {
         outputState[index] = OutputState.Unknown;
      }

      while (!testDone && opMode.opModeIsActive())
      {
         // get current state of each channel
         channelStateList = getChannelStateList (channelList);

         int channelIndex = selectChannel (channelList, channelStateList);

         if (channelIndex == -1)
         {
            testDone = true;
         }
         else
         {
            HardwareDevice channel = channelList.get(channelIndex).device;

            // go to next state
            switch (getChannelState (channel, channelIndex))
            {
               case Output_Low:
                  // go to Output_High state
                  outputState[channelIndex] = OutputState.Output_High;
                  break;
               case Output_High:
                  if (channel instanceof LED)
                  {
                     // go to Output_Low state
                     outputState[channelIndex] = OutputState.Output_Low;
                  }
                  else
                  {
                     // go to Input_Low state
                     outputState[channelIndex] = OutputState.Input_Low;
                  }
                  break;
               case Input_Low:
               case Input_High:
                  // go to Output_Low state
                  outputState[channelIndex] = OutputState.Output_Low;
                  break;
               default:
                  outputState[channelIndex] = getChannelState (channel, channelIndex);
                  break;
            }
            
            setChannelState (channel, outputState[channelIndex]);
         }
      }
   }

   private int selectChannel (List<DeviceData> channelList, List<OutputState> channelStateList)
   {
      Boolean done = false;
      int localChannelIndex = selectedChannelIndex;

      String headerText = "Select output to cycle between\n" +
                          "   On and Off (and Disabled (input) if not an LED)\n";

      while (!done && opMode.opModeIsActive())
      {
         // get state of each digital channel
         String[] channelStringArray = new String[channelList.size()];

         for (int i = 0; i < channelStringArray.length; i++)
         {
            DeviceData device = channelList.get (i);

            HardwareDevice loopChannel = device.device;

            channelStringArray[i] = (getChannelString (device, getChannelState (loopChannel, i)));
         }

         localChannelIndex = Menu.selectFromMenu (headerText, channelStringArray, localChannelIndex, opMode);

         switch (localChannelIndex)
         {
            case -1:
               localChannelIndex = -1;
               done = true;
               break;
            default:
               selectedChannelIndex = localChannelIndex;
               done = true;
               break;
         }
      }
      
      return localChannelIndex;
   }

   private OutputState getChannelState (HardwareDevice channel, int channelIndex)
   {
      OutputState theOutputState = outputState[channelIndex];

      if (theOutputState == OutputState.Unknown ||
          theOutputState == OutputState.Input_Low ||
          theOutputState == OutputState.Input_High)
      {
         if (channel instanceof LED)
         {
            if (((LED)channel).isLightOn ())
            {
               theOutputState = OutputState.Output_High;
            }
            else
            {
               theOutputState = OutputState.Output_Low;
            }
         }
         else
         {
            // DigitalChannelImpl
            DigitalChannelImpl sensor = (DigitalChannelImpl)channel;

            if (sensor.getMode () == DigitalChannel.Mode.INPUT)
            {
               if (sensor.getState () == false)
               {
                  theOutputState = OutputState.Input_Low;
               }
               else
               {
                  theOutputState = OutputState.Input_High;
               }
            }
            else
            {
               if (sensor.getState () == false)
               {
                  theOutputState = OutputState.Output_Low;
               }
               else
               {
                  theOutputState = OutputState.Output_High;
               }
            }
         }
      }

      return theOutputState;
   }

   private List<OutputState> getChannelStateList (List<DeviceData> channelList)
   {
      int index = 0;
      List<OutputState> channelStateList = new ArrayList<OutputState>();

      for (DeviceData device : channelList)
      {
         HardwareDevice channel = device.device;

         channelStateList.add (getChannelState (channel, index++));
      }
      
      return channelStateList;
   }

   private String getChannelString (DeviceData device, OutputState outputState)
   {
      String channelString = device.name;

      switch (outputState)
      {
         case Input_Low:
            channelString += " Disabled (Input low)";
            break;
         case Input_High:
            channelString += " Disabled (Input high)";
            break;
         case Output_Low:
            if (device.device instanceof LED)
            {
               channelString += " Off";
            }
            else
            {
               channelString += " Low";
            }
            break;
         case Output_High:
            if (device.device instanceof LED)
            {
               channelString += " On";
            }
            else
            {
               channelString += " High";
            }
            break;
         default:
            channelString += " Unknown";
            break;
      }

      return channelString;
   }
   
   
   private void setChannelState (HardwareDevice channel, OutputState state)
   {
      switch (state)
      {
         case Output_Low:

            if (channel instanceof LED)
            {
               ((LED)channel).enable(false);
            }
            else
            {
               ((DigitalChannelImpl)channel).setMode (DigitalChannel.Mode.OUTPUT);
               ((DigitalChannelImpl)channel).setState (false);
            }
            break;

         case Output_High:

            if (channel instanceof LED)
            {
               ((LED)channel).enable(true);
            }
            else
            {
               ((DigitalChannelImpl)channel).setMode (DigitalChannel.Mode.OUTPUT);
               ((DigitalChannelImpl)channel).setState (true);
            }
            break;

         case Input_Low:
         case Input_High:
            if (channel instanceof LED)
            {
               // do nothing
            }
            else
            {
               ((DigitalChannelImpl)channel).setMode (DigitalChannel.Mode.INPUT);
            }
            break;
      }

   }
}
