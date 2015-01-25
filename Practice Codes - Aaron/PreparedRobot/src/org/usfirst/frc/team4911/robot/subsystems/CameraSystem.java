package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.CameraServer;

/**
 *
 */
public class CameraSystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public static CameraServer camera = RobotMap.camera;
	
	public CameraSystem() {
		camera.startAutomaticCapture(RobotConstants.CAMERA_NAME);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void startStream() {
    	camera.startAutomaticCapture(RobotConstants.CAMERA_NAME);
    }
    
    public void setQuality(int quality) {
    	camera.setQuality(quality);
    }
    
    public int getQuality() {
    	return camera.getQuality();
    }
   
}
