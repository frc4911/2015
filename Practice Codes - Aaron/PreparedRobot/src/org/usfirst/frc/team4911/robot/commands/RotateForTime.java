package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.MecanumDriveSystem;
import org.usfirst.frc.team4911.robot.subsystems.SensorSystem;
import org.usfirst.frc.team4911.robot.subsystems.PrintSystem;
/**
 *
 */
public class RotateForTime extends Command {
	private MecanumDriveSystem mecanumDriveSystem;
	private SensorSystem sensorSystem;	
	private PrintSystem printSystem;	
	private OI oi;
	
	private int timeRotated;
	private int maxRotateTime;
	
	private double rotateSpeed;
	
	
    public RotateForTime(int maxRotateTime, double rotateSpeed) {
        this.maxRotateTime = maxRotateTime;
        timeRotated = 0;
        this.rotateSpeed = rotateSpeed;
		Robot.teleOp.driveSystemConflict = true;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	mecanumDriveSystem = Robot.mecanumDriveSystem;
    	sensorSystem = Robot.sensorSystem;
    	printSystem = Robot.printSystem;
    	oi = Robot.oi;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	mecanumDriveSystem.drive(0 , 0, rotateSpeed);
    	timeRotated ++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (timeRotated >= maxRotateTime);
    }

    // Called once after isFinished returns true
    protected void end() {
    	mecanumDriveSystem.setGoalHeading(Math.round(sensorSystem.getYaw()));
		Robot.teleOp.driveSystemConflict = false;
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
