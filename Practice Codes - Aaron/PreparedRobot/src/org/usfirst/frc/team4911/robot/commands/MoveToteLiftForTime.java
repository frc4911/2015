package org.usfirst.frc.team4911.robot.commands;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.HookLiftSystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class MoveToteLiftForTime extends Command {
	private HookLiftSystem hookLiftSystem;
	private OperatorDrive operatorDrive;

	private double time;
	private double startTime = Timer.getFPGATimestamp();
	
	private double power;

    public MoveToteLiftForTime(double time, double power) {
    	this.power = -power;
    	this.time = time;
    	
    }

    protected void initialize() {
    	hookLiftSystem = Robot.hookLiftSystem;
    	
    	operatorDrive = Robot.teleOp;
    	if (operatorDrive.hookSystemConflict){
    	    this.cancel();
    	}
    	operatorDrive.hookSystemConflict = true;
    	startTime = Timer.getFPGATimestamp();
    }

    protected void execute() {
	hookLiftSystem.runLiftManually(power);
    }

    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime >= time ;
    }

    protected void end() {
	operatorDrive.hookSystemConflict = false;
	hookLiftSystem.runLiftManually(0.0);
    }

    protected void interrupted() {
    }
}