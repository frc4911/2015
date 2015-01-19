package org.usfirst.frc.team4911.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;

public class SensorSystem extends Subsystem {
    //public static Encoder leftEncoder = RobotMap.leftEncoder;
    //public static Encoder rightEncoder = RobotMap.rightEncoder;
	
	//public static AxisCamera camera = RobotMap.camera;
	//public static AnalogChannel ultraSonicSensor = RobotMap.ultraSonicSensor;
	//public static IMUAdvanced imu = RobotMap.imu;
	
    public void initDefaultCommand() {
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /***************************************
     * IMU METHODS
     ***************************************/
    public void init(){
    	//imu.initIMU();
    }
    public boolean isDoneCalibrating(){
    	//return !imu.isCalibrating();
    	return false;
    }
    public void zeroYaw(){
    	//imu.zeroYaw();
    }
    
    public float getPitch(){
    	//return imu.getPitch();
    	return 0.0f;
    }
    public float getRoll(){
    	//return imu.getRoll();
    	return 0.0f;
    }
    public float getYaw(){
    	//return imu.getYaw();
    	return 0.0f;
    }
    public float getTemp(){
    	//return imu.getTempC();
    	return 0.0f;
    }
    public float getAccelX(){
    	//return imu.getWorldLinearAccelX();
    	return 0.0f;
    }
    public float getAccelY(){
    	//return imu.getWorldLinearAccelY();
    	return 0.0f;
    }
    public float getAccelZ(){
    	//return imu.getWorldLinearAccelZ();
    	return 0.0f;
    }
    public float getCompass(){
    	//return imu.getCompassHeading();
    	return 0.0f;
    }
    
    /***************************************
     * Encoder Methods
     ***************************************/
    public Encoder getLeftEncoder(){
    	return null;
    }
    public Encoder getRightEncoder(){
    	return null;
    }

    public double getDistance(){
        //return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2.0;
    	return 0.0;
    }
    
    public double getLeftDistance(){
        //return leftEncoder.getDistance();
    	return 0.0;
    }
    
    public double getRightDistance(){
        //return rightEncoder.getDistance();
    	return 0.0;
    }
}

