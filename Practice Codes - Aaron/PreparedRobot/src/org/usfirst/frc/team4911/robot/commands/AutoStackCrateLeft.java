package org.usfirst.frc.team4911.robot.commands;


import org.usfirst.frc.team4911.robot.commands.*;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.MecanumDriveSystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoStackCrateLeft extends CommandGroup {
	private MecanumDriveSystem mecanumDriveSystem = Robot.mecanumDriveSystem;
	
    public  AutoStackCrateLeft() {
    	// This Command Group will pick up a tote, lift it up over a container, 
    	// drive left, and place it on the next stack.
    	
    	// ======================= STARTING POSITION INSTRUCTIONS ===========================
    	// For this command the robot must start in TOTE_ACQUIRE_POSITION ready to
    	// acquire a tote. It will move left towards the next tote and end in the
    	// TOTE_ACQUIRE_POSITION on the next tote to the left.
    	
    	addSequential(new MoveToteLift(RobotConstants.TOTE_CLEAR_CONTAINER_POSITION));
    	
    	addSequential(new PIDAxisDrive(-0.4,0.0, RobotConstants.HORIZONTAL_DISTANCE_BETWEEN_TOTES/2)); // drives across left
    	
    	addParallel(new MoveToteLift(RobotConstants.TOTE_STACK_POSITION));
    	
    	addSequential(new PIDAxisDrive(-0.4,0.0, RobotConstants.HORIZONTAL_DISTANCE_BETWEEN_TOTES/2)); // drives across left
    	
    	addSequential(new MoveToteLift(RobotConstants.TOTE_RELEASE_POSITION));
    	
    	addSequential(new PIDAxisDrive(0.0,-0.1, RobotConstants.CLEAR_TOTE_LIP_DISTANCE)); // drives backwards a short amount
    	
    	addSequential(new MoveToteLift(RobotConstants.TOTE_GROUND_POSITION));
    	
    	addSequential(new PIDAxisDrive(0.0, 0.1, RobotConstants.CLEAR_TOTE_LIP_DISTANCE)); // drives forwards a short amount
    	
    	addSequential(new MoveToteLift(RobotConstants.TOTE_ACQUIRE_POSITION));
    }
}
