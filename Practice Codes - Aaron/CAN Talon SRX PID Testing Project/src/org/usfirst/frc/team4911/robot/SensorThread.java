/**
 * 
 */
package org.usfirst.frc.team4911.robot;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import java.io.PrintStream;
import org.usfirst.frc.team4911.robot.Robot;
/**
 * @author pratz_000
 *
 */
public class SensorThread extends Thread {
	
	BuiltInAccelerometer accel;
	PrintStream output;
	
	double accelX;
	double accelY;
	double accelZ;
	
	public SensorThread(BuiltInAccelerometer accel) {
		this.accel = accel;
		accelX = 0.0;//accel.getX();
		accelY = 0.0;//accel.getY();
		accelZ = 0.0;//accel.getZ();
		
	}

	@Override
	public void run() {
		while(Robot.teleopRunning) {
			accelX = accel.getX();
			accelY = accel.getY();
			accelZ = accel.getZ();
		}
		
	}

}
