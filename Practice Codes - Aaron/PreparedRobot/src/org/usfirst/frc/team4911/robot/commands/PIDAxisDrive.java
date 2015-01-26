package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team4911.robot.subsystems.*;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.OI;

/**
 *
 */
public class PIDAxisDrive extends Command {
	private MecanumDriveSystem mecanumDriveSystem;
	private SensorSystem sensorSystem;
	private OI oi;
	private OperatorDrive operatorDrive;
	
	private double x;
	private double y;
	private double goalHeading;//degrees;
	private JoystickButton button;
	
	private static double kP = 1.0 / 150.0;
	private static double kI = 0.0;
	private static double kD = 0.0;
	
	private double rotation;
	private double currError;
	private double lastError;
	private double integration;
	private double derivative;
	
    public PIDAxisDrive(double x, double y, double goalHeading, JoystickButton button) {
		this.x = x;
		this.y = y;
		this.goalHeading = goalHeading;
		this.button = button;
    }

    protected void initialize() {
    	oi = Robot.oi;
    	operatorDrive = Robot.teleOp;
    	mecanumDriveSystem = Robot.mecanumDriveSystem;
    	sensorSystem = Robot.sensorSystem;

    	if(operatorDrive.usingDriveSystem){
    		this.cancel();
    	}    		
    	operatorDrive.usingDriveSystem = true;
    }

    protected void execute() {
    	currError = goalHeading - sensorSystem.getYaw();//[-180 - 180] degrees
    	integration += currError;//[0 - 0.5] seconds
    	derivative = lastError - currError;// NO USE
    	lastError = currError;
    	rotation = kP * currError + kI * integration + kD * derivative;//[-1.0 - 1.0] percentage
    	mecanumDriveSystem.drive(x, y, rotation);
    }

    protected boolean isFinished() {
        return !button.get();
    }

    protected void end() {
    	operatorDrive.usingDriveSystem = false;  
    }

    protected void interrupted() {
    }
}