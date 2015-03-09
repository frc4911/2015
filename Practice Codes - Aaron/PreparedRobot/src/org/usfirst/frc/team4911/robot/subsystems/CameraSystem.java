package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.RobotMap;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.CameraServer;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

/**
 *
 */
public class CameraSystem extends Subsystem {
    private CameraServer server = RobotMap.server;
    
    public CameraSystem() {
    }
    
    public void initDefaultCommand() {
    
    }
    public void startServer() {
    	server.startAutomaticCapture(RobotConstants.TOTE_CAMERA);
    }
    
    public CameraServer getServer() {
    	return server;
    }
   
}

