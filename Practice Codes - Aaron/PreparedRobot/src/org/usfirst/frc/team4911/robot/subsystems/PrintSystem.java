package org.usfirst.frc.team4911.robot.subsystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team4911.robot.RobotConstants;

public class PrintSystem extends Subsystem {
	private int autoFileNum;
	private int teleFileNum;
	private PrintWriter fileWriter;
	
	public PrintSystem(){
		autoFileNum = 1;
		teleFileNum = 1;
	}
	
    public void initDefaultCommand() {
        
    }
    
    public void createNewFile() {
    	if(DriverStation.getInstance().isAutonomous()) {
    		try {
    			fileWriter = new PrintWriter(new File("/home/lvuser/natinst/autoLog" + (autoFileNum++) + ".txt"));
    		} 
    		catch (FileNotFoundException e) {
    		}
    	}
    	else if(DriverStation.getInstance().isOperatorControl()) {
    		try {
        		fileWriter = new PrintWriter(new File("/home/lvuser/natinst/teleLog" + (teleFileNum++) + ".txt"));
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
    		fileWriter.println("Time: " + Timer.getFPGATimestamp());
    		fileWriter.println("Method: " + method);
    		fileWriter.println("Left Encoder: " /*+ getLeftEncoder*/);
    		fileWriter.println("Right Encoder: " /*getRightEncoder*/);
    		fileWriter.println("Heading: " /* + getGyro*/);
    		fileWriter.println("====================");
    		
    	}
    	if(RobotConstants.FLAG) {
    		System.out.println("====================");
    		System.out.println("Time: " + Timer.getFPGATimestamp());
    		System.out.println("Method: " + method);
    		System.out.println("Left Encoder: " /*+ getLeftEncoder*/);
    		System.out.println("Right Encoder: " /*getRightEncoder*/);
    		System.out.println("Heading: " /* + getGyro*/);
    		System.out.println("====================");
    	}
    }
}

