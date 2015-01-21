package org.usfirst.frc.team4911.robot.subsystems;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team4911.robot.RobotConstants;

public class PrintSystem extends Subsystem {
    //PrintStream output;
	private int autoFileNum;
	private int teleFileNum;
	private PrintWriter fileWriter;
	
	public PrintSystem(){
		autoFileNum = 1;
		teleFileNum = 1;
		//autoWriter = new PrintWriter(new File("/home/lvuser/MatchLog/autoLog" + autoFileNum + ".txt"));
		//teleWriter = new PrintWriter(new File("/home/lvuser/MatchLog/TeleLog" + teleFileNum + ".txt"));
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
    
    public void print() {
    	if(!DriverStation.getInstance().isDisabled()) {
    		fileWriter.println("sup?");
    	}
    	if(RobotConstants.FLAG) {
    		System.out.println("nm");
    	}
    }
}

