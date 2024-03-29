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
    int session3;
    int session4;
    int[] sessions;
    int currSession;
    Image frame;
    Joystick joy1;
    boolean camOneOn;

    public void robotInit() {

        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        // the camera name (ex "cam0") can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera("cam2",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        
        /*session2 = NIVision.IMAQdxOpenCamera("cam2", 
        		NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        
        session3 = NIVision.IMAQdxOpenCamera("cam3", 
        		NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        
        session4 = NIVision.IMAQdxOpenCamera("cam4", 
        		NIVision.IMAQdxCameraControlMode.CameraControlModeController);*/
        
        joy1 = new Joystick(0);
        camOneOn = false;
        
        sessions = new int[4];
        sessions[0] = session;
        //sessions[1] = session2;
        //sessions[2] = session3;
        //sessions[3] = session4;
        
        int currSession = 0;
    }

    public void operatorControl() {
        NIVision.IMAQdxStartAcquisition(sessions[currSession]);

        /**
         * grab an image, draw the circle, and provide it for the camera server
         * which will in turn send it to the dashboard.
         */

        while (isOperatorControl() && isEnabled()) {
            
            NIVision.IMAQdxGrab(sessions[currSession], frame, 1);
   
            CameraServer.getInstance().setImage(frame);
            
            if(joy1.getTrigger()) {
            	NIVision.IMAQdxStopAcquisition(sessions[currSession++]);
            	if(currSession > 3) {
            	    currSession = 0;
            	}
            	NIVision.IMAQdxConfigureGrab(sessions[currSession]);
            	NIVision.IMAQdxStartAcquisition(sessions[currSession]);
            }
            /** robot code here! **/
            Timer.delay(0.005);		// wait for a motor update time
        }
        //NIVision.IMAQdxStopAcquisition(session);
    }

    public void test() {
    }
}
