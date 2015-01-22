package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.RobotMap;

//import com.kauailabs.nav6.frc.BufferingSerialPort;
//import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.livewindow.LiveWindow;
//import edu.wpi.first.wpilibj.visa.VisaException;

public class MecanumDriveSystem extends Subsystem {
	private CANTalon frontLeft = RobotMap.leftFront;
	private CANTalon rearLeft = RobotMap.leftRear;
	private CANTalon frontRight = RobotMap.rightFront;
	private CANTalon rearRight = RobotMap.rightRear;
	
	//private AdvancedIMU imu = RobotMap.imu;
	
	private RobotDrive robot = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
	//BufferingSerialPort serial_port;		
	
	public MecanumDriveSystem(){
		super();
		/*
		try {
	          serial_port = new BufferingSerialPort(57600);
	          
	          // You can add a second parameter to modify the 
	          // update rate (in hz) from 4 to 100.  The default is 100.
	          // If you need to minimize CPU load, you can set it to a
	          // lower value, as shown here, depending upon your needs.
	          
	          // You can also use the IMUAdvanced class for advanced
	          // features.

	          byte update_rate_hz = 20;
	          //imu = new IMU(serial_port,update_rate_hz);
	          imu = new IMUAdvanced(serial_port,update_rate_hz);
	      } catch (VisaException ex) {
	          ex.printStackTrace();
	      }
	      
	      Timer.delay(0.3);
	      imu.zeroYaw();
	      */
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
	
	public void drive(double x, double y, double rotation){
		robot.mecanumDrive_Cartesian(x, y, rotation, 0.0);
		//robot.mecanumDrive_Cartesian(x, y, rotation, (double)imu.getYaw());
		
	}
	public void stop(){
		drive(0.0, 0.0, 0.0);
	}

}
