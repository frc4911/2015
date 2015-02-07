package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Joystick;

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
	private double goalHeading;//degrees;
	private Joystick joystick;
	private int povDir;
	
    public PIDAxisDrive(double x, double y, double goalHeading, Joystick joystick, int povDir) {
		this.x = x;
		this.y = y;
		this.goalHeading = goalHeading;
		this.joystick = joystick;
		this.povDir = povDir;
    }

    protected void initialize() {
    	oi = Robot.oi;
    	
    	operatorDrive = Robot.teleOp;
    	mecanumDriveSystem = Robot.mecanumDriveSystem;

    	if(operatorDrive.driveSystemConflict){
    		this.cancel();
    	}    		
    	operatorDrive.driveSystemConflict = true;
    }

    protected void execute() {
    	mecanumDriveSystem.driveWithPID(x, y);
    }

    protected boolean isFinished() {
        return joystick.getPOV(RobotConstants.JOYSTICK_POV_NUM) != povDir;
    }

    protected void end() {
    	operatorDrive.driveSystemConflict = false;  
    }

    protected void interrupted() {
    }
}
