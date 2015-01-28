package org.usfirst.frc.team4911.robot.subsystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;

public class PrintSystem extends Subsystem {
	private int autoFileNum;
	private int teleFileNum;
	private PrintWriter fileWriter;
	
	private Map<String, String> dataMap;
	private int frequency;
	private int numIteration;
	
	public PrintSystem(){
		autoFileNum = 1;
		teleFileNum = 1;
		this.numIteration = 0;
		this.frequency = RobotConstants.printFrequency;
		this.dataMap = new HashMap<String, String>();
		resetIteration();
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
    
    public void resetIteration() {
    	numIteration = 0;
    	this.frequency = RobotConstants.printFrequency;
    }
    
    public void updatePrint(){
    	if(numIteration >= frequency) {
    		if(RobotConstants.FLAG){
    			System.out.println("===========================================");
    			System.out.println("Time:\t" + Timer.getFPGATimestamp());
    		}	
    		fileWriter.println("===========================================");
    		fileWriter.println("Time:\t" + Timer.getFPGATimestamp());
    		for(String key : dataMap.keySet()){
    			String msg = dataMap.get(key);
    			if(RobotConstants.FLAG){
        			System.out.println(key + " :\t" + msg);
    			}
    			fileWriter.println(key + " :\t" + msg);
    		}    	
    		if(RobotConstants.FLAG)
    			System.out.println("===========================================");
    		fileWriter.println("===========================================");
    		resetIteration();
    	} else {
    		numIteration++;
    	}
    }
    
    public void print(String key, String msg){
    	dataMap.put(key, msg);
    }
}

