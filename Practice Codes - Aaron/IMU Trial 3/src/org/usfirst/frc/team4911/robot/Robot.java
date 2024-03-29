package org.usfirst.frc.team4911.robot;


import java.io.BufferedOutputStream;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SerialPort;

import com.kauailabs.nav6.frc.IMUAdvanced;
import com.kauailabs.nav6.frc.*;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 * This is a short sample program demonstrating how to use the basic throttle
 * mode of the new CAN Talon.
 */
@SuppressWarnings("unused")
public class Robot extends SampleRobot {

	CANTalon leftFront;
	CANTalon leftRear;
	CANTalon rightFront;
	CANTalon rightRear;
	
	Joystick stick1;
	Joystick stick2;

	PrintStream output;
  
	SerialPort serial_port;
	IMUAdvanced imu;
  boolean first_iteration;

  public Robot() {
	  stick1 = new Joystick(0);
	  stick2 = new Joystick(1);
	  
	  leftFront = new CANTalon(0); // Initialize the CanTalonSRX on device 1.
      leftFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      leftFront.changeControlMode(CANTalon.ControlMode.PercentVbus);
      leftFront.setPID(1.0, 0.0, 0.0);
	  
      leftRear = new CANTalon(2); // Initialize the CanTalonSRX on device 1.
      leftRear.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      leftRear.changeControlMode(CANTalon.ControlMode.PercentVbus);
      leftRear.setPID(1.0, 0.0, 0.0);
	  
      rightFront = new CANTalon(1); // Initialize the CanTalonSRX on device 1.
      rightFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      rightFront.changeControlMode(CANTalon.ControlMode.PercentVbus);
      rightFront.setPID(1.0, 0.0, 0.0);
	  
      rightRear = new CANTalon(3); // Initialize the CanTalonSRX on device 1.
      rightRear.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      rightRear.changeControlMode(CANTalon.ControlMode.PercentVbus);
      rightRear.setPID(1.0, 0.0, 0.0);
      
	  
      try {
    	  serial_port = new SerialPort(57600,SerialPort.Port.kUSB);
          
          // You can add a second parameter to modify the 
          // update rate (in hz) from 4 to 100.  The default is 100.
          // If you need to minimize CPU load, you can set it to a
          // lower value, as shown here, depending upon your needs.
          
          // You can also use the IMUAdvanced class for advanced
          // features.

          byte update_rate_hz = 100;
          imu = new IMUAdvanced(serial_port,update_rate_hz);
      } catch (Exception ex) {
          ex.printStackTrace();
      }
      first_iteration = true;
  }

  /**
    * Runs the motor.
    */
  public void operatorControl() {
	  try {
			output = new PrintStream(new BufferedOutputStream(new FileOutputStream("/home/lvuser/natinst/teleLog.txt")));
			System.setOut(output);
		} catch (FileNotFoundException e) {
			
		} 
	  
    while (isOperatorControl() && isEnabled()) {
      leftFront.set(stick1.getRawAxis(1));
      leftRear.set(stick1.getRawAxis(1));

      rightFront.set(-stick2.getRawAxis(1));
      rightRear.set(-stick2.getRawAxis(1));
    	
      
      boolean is_calibrating = imu.isCalibrating();
      if ( first_iteration && !is_calibrating ) {
          Timer.delay( 0.3 );
          imu.zeroYaw();
          first_iteration = false;
      } else {
		  System.out.println("===============================================================");
		  System.out.println("TIMERt:" + Timer.getFPGATimestamp());
		  System.out.println("IMU_Connected\t: " + imu.isConnected());
		  System.out.println("IMU_IsCalibrating\t: " + imu.isCalibrating());
		  System.out.println("IMU_Yaw\t: " + imu.getYaw());
		  System.out.println("IMU_Pitch\t: " + imu.getPitch());
		  System.out.println("IMU_Roll\t: " + imu.getRoll());
		  System.out.println("IMU_CompassHeading\t: " + imu.getCompassHeading());
		  System.out.println("IMU_Update_Count\t: " + imu.getUpdateCount());
		  System.out.println("IMU_Byte_Count\t: " + imu.getByteCount());
		
		  System.out.println("IMU_Accel_X\t: "+ imu.getWorldLinearAccelX());
		  System.out.println("IMU_Accel_Y\t: "+ imu.getWorldLinearAccelY());
		  System.out.println("IMU_IsMoving\t: "+ imu.isMoving());
		  System.out.println("IMU_Temp_C\t: "+ imu.getTempC());
		  //System.out.println("===============================================================");
      }
    }
    
    leftFront.disable();
    leftRear.disable();
    rightFront.disable();
    rightRear.disable();
    
    output.close();
  }
}
