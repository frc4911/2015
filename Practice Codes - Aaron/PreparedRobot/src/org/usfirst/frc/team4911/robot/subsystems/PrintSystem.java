package org.usfirst.frc.team4911.robot.subsystems;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import edu.wpi.first.wpilibj.command.Subsystem;

public class PrintSystem extends Subsystem {
    PrintStream output;
	
	public PrintSystem(){
		try {
			new File("/home/lvuser/MatchLog/Autonomous").mkdir();
			new File("/home/lvuser/MatchLog/TeleOperated").mkdir();
		} catch (SecurityException e) {
			
		}
		try {
			output = new PrintStream(new BufferedOutputStream(
					new FileOutputStream(new File("/home/lvuser/MatchLog/autoLog.txt"))));
			System.setOut(output);
		} catch (FileNotFoundException e) {
			
		}
	}
	
    public void initDefaultCommand() {
        
    }
}
