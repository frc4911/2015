
package org.usfirst.frc.team4911.robot;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    private Joystick driverJoy = new Joystick(0);
    private DriveSystem robotDrive = new DriveSystem();
    private IMUAdvanced imu;
    private SerialPort serial_port;
    double rotation;
    
    public Robot() {
	try {
	    serial_port = new SerialPort(57600,SerialPort.Port.kOnboard);
	    
	    byte update_rate_hz = 20;
	    imu = new IMUAdvanced(serial_port,update_rate_hz);
	} catch (Exception ex) {
	    ex.printStackTrace();	
	}
	imu.initIMU();
	Timer.delay(0.3);
    }
    
    public void robotInit() {

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        while(isEnabled() && isOperatorControl()) {
            if(driverJoy.getTrigger()) {
        	rotation = driverJoy.getZ();
        	robotDrive.setGoalHeading(imu.getYaw());
            }
            else {
        	rotation = 0.0;
            }
            robotDrive.updateDrive(driverJoy.getX(), driverJoy.getY(), rotation, imu.getYaw(), driverJoy.getThrottle());
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
