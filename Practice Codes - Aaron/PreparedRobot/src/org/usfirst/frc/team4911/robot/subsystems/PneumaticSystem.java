package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;

/**
 *
 */
public class PneumaticSystem extends Subsystem {
    private Compressor compressor = RobotMap.compressor;
    private Solenoid testSolenoid = RobotMap.testSolenoid;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public PneumaticSystem(){
    	start();
    }

    public void initDefaultCommand() {
    	// Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void start() {
    	compressor.start();
    }
    
    public void stop() {
    	compressor.stop(); 
    }
    
    public void out() {
    	testSolenoid.set(true);
    }
    
    public void in() {
    	testSolenoid.set(false);
    }
    
    public boolean getSolenoidPosition(){
    	return testSolenoid.get(); // testSolenoid.get();
    }
}
