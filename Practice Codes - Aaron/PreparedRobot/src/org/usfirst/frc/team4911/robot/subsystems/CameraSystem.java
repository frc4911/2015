package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.CameraServer;

/**
 *
 */
public class CameraSystem extends Subsystem {
    
    private CameraServer server = RobotMap.server;
    
    public CameraSystem() {
	
    }
    
    public void initDefaultCommand() {
    
    }
    
    public void start() {
	server.startAutomaticCapture(RobotConstants.TOTE_CAMERA);
    }
    
    public CameraServer getServer() {
	return server;
    }
   
}

