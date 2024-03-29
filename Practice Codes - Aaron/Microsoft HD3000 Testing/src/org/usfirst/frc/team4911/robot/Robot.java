package org.usfirst.frc.team4911.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;

/**
 * This is a demo program showing the use of the NIVision class to do vision processing. 
 * The image is acquired from the USB Webcam, then a circle is overlayed on it. 
 * The NIVision class supplies dozens of methods for different types of processing. 
 * The resulting image can then be sent to the FRC PC Dashboard with setImage()
 */
public class Robot extends SampleRobot {
    int session;
    Image frame;
    CameraServer camServer;

    public void robotInit() {
        session = 1;
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        session = NIVision.IMAQdxOpenCamera("cam1",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        
        camServer = CameraServer.getInstance();
        camServer.setQuality(50);
    }

    public void operatorControl() {
        NIVision.IMAQdxStartAcquisition(session);

        while (isOperatorControl() && isEnabled()) {
            
            NIVision.IMAQdxGrab(session, frame, 1);
   
            camServer.setImage(frame);
        }
    }

    public void test() {
    }
}
