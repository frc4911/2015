package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team4911.robot.subsystems.*;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.RobotConstants;

/**
 *
 */
public class DriveForTime extends Command {
	private MecanumDriveSystem mecanumDriveSystem;
	private SensorSystem sensorSystem;
	private OI oi;
	private OperatorDrive operatorDrive;
	
	private double x;
	private double y;
	private double time;
	private double startTime;
	
    public DriveForTime(double x, double y, double time) {
	this.x = x;
	this.y = y;
	this.time = time;
    }

    protected void initialize() {
    	oi = Robot.oi;
    	
    	startTime = Timer.getFPGATimestamp();
    	
    	operatorDrive = Robot.teleOp;
    	mecanumDriveSystem = Robot.mecanumDriveSystem;
    	sensorSystem = Robot.sensorSystem;
    	if(DriverStation.getInstance().isOperatorControl()){
    	    if(operatorDrive.driveSystemConflict){
    		this.cancel();
	    }
    	    operatorDrive.driveSystemConflict = true;
    	}
    }

    protected void execute() {
    	mecanumDriveSystem.driveWithPID(x, y, (double)sensorSystem.getYawWithCompensation());
    }

    protected boolean isFinished() {

    	if(DriverStation.getInstance().isOperatorControl()){
    	    return true;
    	}
    	else{
    	    //checks if we have moved for the designated time
    	    return ((Timer.getFPGATimestamp() - startTime) >= time);	
    	}
    }

    protected void end() {
    	if(DriverStation.getInstance().isOperatorControl()){
    	    operatorDrive.driveSystemConflict = false;  
    	}
    	Timer.delay(0.75);
    }

    protected void interrupted() {
    }
}
