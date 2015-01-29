package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Servo;

/**
 *
 */
public class CameraSystem extends Subsystem {
    
	public Servo cameraServo;
	
	public CameraSystem() {
		cameraServo = RobotMap.cameraServo;
	}
    public void initDefaultCommand() {
    }
    
    public void setServo(double pos) {
    	cameraServo.set(pos);
    }
   
}

