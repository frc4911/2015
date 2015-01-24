package org.usfirst.frc.team4911.robot.subsystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.RobotMap;

public class PrintSystem extends Subsystem {
	private int autoFileNum;
	private int teleFileNum;
	private PrintWriter fileWriter;
	private SensorSystem sensorSystem = Robot.sensorSystem;
	public PrintSystem(){
		autoFileNum = 1;
		teleFileNum = 1;
	}
	
    public void initDefaultCommand() {
        
    }
    
    public void createNewFile() {
    	if(DriverStation.getInstance().isAutonomous()) {
    		try {
    			if(autoFileNum >= 10) {
    				fileWriter = new PrintWriter(new File("/home/lvuser/natinst/autoLog" + (autoFileNum++) + ".txt"));
    			}
    			else {
    				fileWriter = new PrintWriter(new File("/home/lvuser/natinst/autoLog" + 0 + (autoFileNum++) + ".txt"));
    			}
    		} 
    		catch (FileNotFoundException e) {
    		}
    	}
    	else if(DriverStation.getInstance().isOperatorControl()) {
    		try {
    			if(teleFileNum >= 10) {
    				fileWriter = new PrintWriter(new File("/home/lvuser/natinst/teleLog" + (teleFileNum++) + ".txt"));
    			}
    			else {
    				fileWriter = new PrintWriter(new File("/home/lvuser/natinst/teleLog" + 0 + (teleFileNum++) + ".txt"));
    			}
    		}
        	catch(FileNotFoundException e) {
        	}
    	}
    }
    
    public void closeOutput() {
    	if(fileWriter != null) {
    		fileWriter.close();
    	}
    }
    
    public void print(String method) {
    	if(!DriverStation.getInstance().isDisabled()) {
    		fileWriter.println("====================");
    		fileWriter.println("Method: " + method);
    		fileWriter.println("Time: " + Timer.getFPGATimestamp());    		
    		fileWriter.println("Left Encoder: " /*+ getLeftEncoder*/);
    		fileWriter.println("Right Encoder: " /*getRightEncoder*/);
    		fileWriter.println("Heading: " /* + getGyro*/);
    		fileWriter.println("Distance: " + sensorSystem.getIN() + " in");
    		fileWriter.println("====================");
    	}
    	
    	if(RobotConstants.FLAG) {
    		System.out.println("====================");
    		System.out.println("Method: " + method);
    		System.out.println("Time: " + Timer.getFPGATimestamp());    		
    		System.out.println("Left Encoder: " /*+ getLeftEncoder*/);
   			System.out.println("Right Encoder: " /*getRightEncoder*/);
   			System.out.println("Heading: " /* + getGyro*/);
   			System.out.println("Distance: " + sensorSystem.getIN() + " in");
   			System.out.println("====================");
    	}
    	
    }
    
    public void printMessage(String message) {
    	System.out.println("====================");
    	System.out.println(message);
    	System.out.println("====================");
    	
    	if(!DriverStation.getInstance().isDisabled()) {
    		fileWriter.println("====================");
    		fileWriter.println(message);
    		fileWriter.println("====================");
    	}
    }
}

