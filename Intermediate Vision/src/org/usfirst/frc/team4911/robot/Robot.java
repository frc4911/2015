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
    int session2;
    Image frame;
    Joystick joy1;
    boolean camOneOn;

    public void robotInit() {

        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        // the camera name (ex "cam0") can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera("cam2",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        
        session2 = NIVision.IMAQdxOpenCamera("cam1", 
        		NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        //NIVision.IMAQdxConfigureGrab(id);
        //NIVision.IMAQdxConfigureGrab(session2);
        
        joy1 = new Joystick(0);
        camOneOn = false;
    }

    public void operatorControl() {
        NIVision.IMAQdxStartAcquisition(session);
        //NIVision.IMAQdxStartAcquisition(session2);

        /**
         * grab an image, draw the circle, and provide it for the camera server
         * which will in turn send it to the dashboard.
         */

        while (isOperatorControl() && isEnabled()) {
        	if(!camOneOn) {
        		NIVision.IMAQdxGrab(session, frame, 1);
        	}
        	else {
        		NIVision.IMAQdxGrab(session2, frame, 1);
        	}
            CameraServer.getInstance().setImage(frame);
            
            if(joy1.getTrigger()) {
            	if(camOneOn) {
            		NIVision.IMAQdxStopAcquisition(session2);
            		NIVision.IMAQdxConfigureGrab(session);
            		NIVision.IMAQdxStartAcquisition(session);
            	}
            	else {
            		NIVision.IMAQdxStopAcquisition(session);
            		NIVision.IMAQdxConfigureGrab(session2);
            		NIVision.IMAQdxStartAcquisition(session2);
            	}
            	camOneOn = !camOneOn;
            }

            /** robot code here! **/
            Timer.delay(0.005);		// wait for a motor update time
        }
        //NIVision.IMAQdxStopAcquisition(session);
    }

    public void test() {
    }
}