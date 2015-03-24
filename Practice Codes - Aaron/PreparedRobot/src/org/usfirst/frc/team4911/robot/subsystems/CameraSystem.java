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
    //private Image frame = RobotMap.frame;
    //private int toteSession = RobotMap.toteSession;
    
    public CameraSystem() {
	//NIVision.IMAQdxConfigureGrab(toteSession);
    }
    
    public void initDefaultCommand() {
    
    }
    public void startServer() {
    	server.startAutomaticCapture(RobotConstants.TOTE_CAMERA);
    }
    
    public void start() {
	//NIVision.IMAQdxStartAcquisition(toteSession);
    }
    
    public void updateSession() {
	//NIVision.IMAQdxGrab(toteSession, frame, 1);
        //CameraServer.getInstance().setImage(frame);
    }

    public void stop() {
	//NIVision.IMAQdxStopAcquisition(toteSession);
    }
    public CameraServer getServer() {
    	return server;
    }
   
}

