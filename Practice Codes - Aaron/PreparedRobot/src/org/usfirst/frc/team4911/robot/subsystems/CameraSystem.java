package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.RobotMap;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.CameraServer;

/**
 *
 */
public class CameraSystem extends Subsystem {
    private CameraServer server = RobotMap.server;
    private int session;
    //private Image frame;
    
    
    public CameraSystem() {
    	/*
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        session = NIVision.IMAQdxOpenCamera("cam2",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        
        NIVision.IMAQdxConfigureGrab(session);
        */
    }
    
    public void initDefaultCommand() {
    
    }
    
    public void start() {
		//NIVision.IMAQdxStartAcquisition(session);
    	//server.startAutomaticCapture(RobotConstants.TOTE_CAMERA);
    }
    
    public void update(){
		//NIVision.IMAQdxGrab(session, frame, 1);
		//server.setImage(frame);
    }
    
    public CameraServer getServer() {
    	return server;
    }
   
}

