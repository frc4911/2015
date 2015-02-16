package org.usfirst.frc.team4911.robot.commands;


import org.usfirst.frc.team4911.robot.commands.*;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.MecanumDriveSystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoStackCrateRight extends CommandGroup {
	private MecanumDriveSystem mecanumDriveSystem = Robot.mecanumDriveSystem;
	
    public  AutoStackCrateRight() {
    	// This Command Group will pick up a tote, lift it up over a container, 
    	// and place it on the next stack.
    	
    	// ======================= STARTING POSITION INSTRUCTIONS ===========================
    	// For this command the robot tote lift must start in TOTE_ACQUIRE_POSITION ready to
    	// acquire a tote. It will move right towards the next tote and end in the
    	// TOTE_ACQUIRE_POSITION on the next tote to the right.
    	
    	addParallel(new MoveToteLift(RobotConstants.TOTE_CLEAR_CONTAINER_POSITION));
    	
    	addSequential(new PIDAxisDrive(0.4,0.0, RobotConstants.HORIZONTAL_DISTANCE_BETWEEN_TOTES)); // drives across right 1000 encoder pulses
    	
    	addSequential(new MoveToteLift(RobotConstants.TOTE_STACK_POSITION));
    	
    	addSequential(new PIDAxisDrive(-0.4,0.0, RobotConstants.CLEAR_TOTE_LIP_DISTANCE)); // drives across left 10 encoder pulses
    	
    	addSequential(new MoveToteLift(RobotConstants.TOTE_RELEASE_POSITION));
    	
    	addSequential(new PIDAxisDrive(0.0,-0.1, RobotConstants.CLEAR_TOTE_LIP_DISTANCE)); // drives backwards 10 encoder pulses
    	
    	addSequential(new MoveToteLift(RobotConstants.TOTE_GROUND_POSITION));
    	
    	addSequential(new PIDAxisDrive(0.0,0.1, RobotConstants.CLEAR_TOTE_LIP_DISTANCE)); // drives forwards 10 encoder pulses
    	
    	addSequential(new MoveToteLift(RobotConstants.TOTE_ACQUIRE_POSITION));
    }
}
