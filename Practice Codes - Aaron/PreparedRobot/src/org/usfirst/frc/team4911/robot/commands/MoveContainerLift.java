package org.usfirst.frc.team4911.robot.commands;

import org.usfirst.frc.team4911.robot.Robot;

import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.ContainerLiftSystem;

import edu.wpi.first.wpilibj.command.Command;

public class MoveContainerLift extends Command {
	private ContainerLiftSystem containerLiftSystem;
	private double toteLevel;
	private static double THRESHOLD = 1.0;//Inches

    public MoveContainerLift(double toteLvl) {
    	requires(containerLiftSystem);
    	toteLevel = toteLvl;
    }

    protected void initialize() {
    	containerLiftSystem = Robot.containerLiftSystem;
    	if(containerLiftSystem.isLiftBeingUsed()){
    		this.cancel();
    	}    		
    	containerLiftSystem.setLiftBeingUsed(true);
    	containerLiftSystem.liftViaTote(toteLevel);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return Math.abs(containerLiftSystem.getLiftDistance() - RobotConstants.TOTE_HEIGHT * toteLevel) < THRESHOLD ;
    }

    protected void end() {
    	containerLiftSystem.setLiftBeingUsed(false);
    }

    protected void interrupted() {
    }
}