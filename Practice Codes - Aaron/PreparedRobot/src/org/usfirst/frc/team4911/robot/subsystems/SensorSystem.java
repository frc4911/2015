package org.usfirst.frc.team4911.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Gyro;
import org.usfirst.frc.team4911.robot.RobotMap;

//import edu.wpi.first.wpilibj.CANTalon;

public class SensorSystem extends Subsystem {
	//public static AxisCamera camera = RobotMap.camera;
	//public static AnalogChannel ultraSonicSensor = RobotMap.ultraSonicSensor;
	//public static IMUAdvanced imu = RobotMap.imu;

	//public static CANTalon leftFront = RobotMap.leftFront;
	//public static CANTalon leftRear = RobotMap.leftRear;
	//public static CANTalon rightFront = RobotMap.rightFront;
	//public static CANTalon rightRear = RobotMap.rightRear;
	
	public static BuiltInAccelerometer accelerometer = RobotMap.accelerometer;
	public static Gyro gyro = RobotMap.gyro;
	
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
     * Accelerometer Methods
     ***************************************/
    public double getX(){
    	return accelerometer.getX();
    }
    public double getY(){
    	return accelerometer.getY();
    }
    public double getZ(){
    	return accelerometer.getZ();
    }
    
    /***************************************
     * Gyro Methods
     ***************************************/
    public double getAngle(){
    	return gyro.getAngle();
    }
    public double getRate(){
    	return gyro.getRate();
    }
    public void resetGyro(){
    	gyro.reset();
    }
    
    /***************************************
     * Encoder Methods
     ***************************************/
    public double getDistance(){
    	//"getEncPosition()": do not know what the units it will give me
    	//This method must be corrected to return the units in "INCHES"
        //return (leftFront.getEncPosition() + rightFront.getEncPosition()) / 2.0;
    	return 0.0;
    }
    
    public double getLeftDistance(){
        //return leftFront.getEncPosition();
    	return 0.0;
    }
    
    public double getRightDistance(){
        //return rightFront.getDistance();
    	return 0.0;
    }
}

