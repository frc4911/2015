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
    	// For this command the robot tote lift must start in TOTE_ACQUIRE_POSITION ready to
    	// acquire a tote. It will move left towards the next tote and end in the
    	// TOTE_ACQUIRE_POSITION on the next tote to the left.
    	
	/*
    	addSequential(new MoveToteLift(RobotConstants.TOTE_CLEAR_CONTAINER_POSITION));
    	
    	addSequential(new PIDAxisDrive(-0.4,0.0, RobotConstants.HORIZONTAL_DISTANCE_BETWEEN_TOTES/2)); // drives across left
    	
    	addParallel(new MoveToteLift(RobotConstants.TOTE_STACK_POSITION));
    	
    	addSequential(new PIDAxisDrive(-0.4,0.0, RobotConstants.HORIZONTAL_DISTANCE_BETWEEN_TOTES/2)); // drives across left
    	
    	addSequential(new MoveToteLift(RobotConstants.TOTE_RELEASE_POSITION));
    	
    	addSequential(new PIDAxisDrive(0.0,-0.1, RobotConstants.CLEAR_TOTE_LIP_DISTANCE)); // drives backwards a short amount
    	
    	addSequential(new MoveToteLift(RobotConstants.TOTE_GROUND_POSITION));
    	
    	addSequential(new PIDAxisDrive(0.0, 0.1, RobotConstants.CLEAR_TOTE_LIP_DISTANCE)); // drives forwards a short amount
    	
    	addSequential(new MoveToteLift(RobotConstants.TOTE_ACQUIRE_POSITION));
    	*/
	double liftSpeed = 0.3;
	double driveSpeed = 0.3;
	
	addSequential(new MoveToteLiftForTime(2.0, liftSpeed));//Lifting Tote
	addSequential(new DriveForTime(-0.5, 0.0, 4.0));//Moving to Next Tote
	addSequential(new MoveToteLiftForTime(1.5, -liftSpeed));//Releasing Tote
	addSequential(new DriveForTime(0.0, -driveSpeed, 0.2));//Backing Up
	addSequential(new MoveToteLiftForTime(0.5, -liftSpeed));//Lowering lift
	addSequential(new DriveForTime(0.0,driveSpeed, 0.2));//MovingForward


	addSequential(new MoveToteLiftForTime(2.0, liftSpeed));//Lifting Tote
	addSequential(new DriveForTime(-0.5, 0.0, 4.0));//Moving to Next Tote
	addSequential(new MoveToteLiftForTime(1.5, -liftSpeed));//Releasing Tote
	addSequential(new DriveForTime(0.0, -driveSpeed, 0.2));//Backing Up
	addSequential(new MoveToteLiftForTime(0.5, -liftSpeed));//Lowering lift
	addSequential(new DriveForTime(0.0,driveSpeed, 0.2));//MovingForward
	

	addSequential(new MoveToteLiftForTime(1.0, liftSpeed));//Lifting Tote slightly
	addSequential(new RotateForTime(0.5, -0.3)); // MAKE PID ROTATE
	addSequential(new DriveForTime(0.0, driveSpeed, 4.0));//Drive into Auto zone (field oriented)
	}
}
