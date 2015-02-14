package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc.team4911.robot.subsystems.*;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.RobotConstants;

/**
 *
 */
public class PIDAxisDrive extends Command {
	private MecanumDriveSystem mecanumDriveSystem;
	private OI oi;
	private OperatorDrive operatorDrive;
	
	private double x;
	private double y;
	private double threshold = 1;
	private double distance;
	private Joystick joystick;
	
	public PIDAxisDrive(double x, double y, double distance, Joystick joystick) {
		this.x = x;
		this.y = y;
		this.distance = distance;
		this.joystick = joystick;
    }
	
    public PIDAxisDrive(double x, double y, double distance) {
		this.x = x;
		this.y = y;
		this.distance = distance;
    }

    protected void initialize() {
    	oi = Robot.oi;
    	
    	operatorDrive = Robot.teleOp;
    	mecanumDriveSystem = Robot.mecanumDriveSystem;
    	if(DriverStation.getInstance().isOperatorControl()){
	    	if(operatorDrive.driveSystemConflict){
	    		this.cancel();
	    	}    
    	}
    	operatorDrive.driveSystemConflict = true;
    	
    	mecanumDriveSystem.setDistanceEncoder(0.0);
    }

    protected void execute() {
    	mecanumDriveSystem.driveWithPID(x, y);
    	//mecanumDriveSystem.drive(x, y, 0.0);
    }

    protected boolean isFinished() {

    	if(DriverStation.getInstance().isOperatorControl()){
    		return true;
    	}
    	else{
    		return Math.abs(mecanumDriveSystem.getDistanceEncoder() - distance) < threshold;
    	}
    }

    protected void end() {
    	operatorDrive.driveSystemConflict = false;  
    }

    protected void interrupted() {
    }
}
