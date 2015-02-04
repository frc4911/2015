package org.usfirst.frc.team4911.robot.commands;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.ContainerLiftSystem;

import edu.wpi.first.wpilibj.command.Command;

public class MoveContainerClamp extends Command {
	private ContainerLiftSystem containerLiftSystem;
	private ContainerLiftSystem.ContainerStatus val;
	private static final double THRESHOLD = 1.0;//Inches
	
    public MoveContainerClamp(ContainerLiftSystem.ContainerStatus val) {
    	//requires(...) not necessary because "MoveContainerLift" command
    	//	is already using it.
    	this.val = val;
    }

    protected void initialize() {
    	containerLiftSystem = Robot.containerLiftSystem;
    	if(containerLiftSystem.isClampBeingUsed()){
    		this.cancel();
    	}    		
    	containerLiftSystem.setClampBeingUsed(true);
    }

    protected void execute() {
    	if(RobotConstants.CONTAINERSYSTEM_CLAMP_CLOSING_DISTANCE - containerLiftSystem.getClampDistance() < 0) {
    		containerLiftSystem.driveClamp(RobotConstants.CONTAINERSYSTEM_CLAMP_SPEED);
    	} else {
    		containerLiftSystem.driveClamp(RobotConstants.CONTAINERSYSTEM_CLAMP_SPEED);
    	}
    }

    protected boolean isFinished() {
    	switch(this.val){
    		case CLOSE:
    			return Math.abs(containerLiftSystem.getClampDistance() - RobotConstants.CONTAINERSYSTEM_CLAMP_CLOSING_DISTANCE) <= THRESHOLD;
    		case CLAMP:
    			return Math.abs(containerLiftSystem.getClampDistance() - RobotConstants.CONTAINERSYSTEM_CLAMP_HOLDING_DISTANCE) <= THRESHOLD;
    		case OPEN:
    			return Math.abs(containerLiftSystem.getClampDistance() - RobotConstants.CONTAINERSYSTEM_CLAMP_OPENING_DISTANCE) <= THRESHOLD;
    	}
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}