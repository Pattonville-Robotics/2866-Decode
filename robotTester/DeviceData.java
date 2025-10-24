package org.firstinspires.ftc.teamcode.robotTester;

import com.qualcomm.robotcore.hardware.HardwareDevice;

public class DeviceData {

  public String name;
  public HardwareDevice device;

  public DeviceData(String name, HardwareDevice device)
  {
    this.name = name;
    this.device = device;
  }
}