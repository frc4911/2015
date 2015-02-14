package org.usfirst.frc.team4911.robot.commands;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.HookLiftSystem;

import edu.wpi.first.wpilibj.command.Command;

public class MoveToteLift extends Command {
	private HookLiftSystem hookLiftSystem;
	private double toteLevel;
	private static double THRESHOLD = 1.0;//Inches

    public MoveToteLift(double height) {
    	requires(hookLiftSystem);
    	toteLevel = height;
    }

    protected void initialize() {
    	hookLiftSystem = Robot.hookLiftSystem;
    	if(hookLiftSystem.isBeingUsed()){
    		this.cancel();
    	}    		
    	hookLiftSystem.setBeingused(true);
    	
    	hookLiftSystem.setLiftToPoint(toteLevel);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return Math.abs(hookLiftSystem.getDistance() - toteLevel) < THRESHOLD ;
    }

    protected void end() {
    	hookLiftSystem.setBeingused(false);
    }

    protected void interrupted() {
    }
}
