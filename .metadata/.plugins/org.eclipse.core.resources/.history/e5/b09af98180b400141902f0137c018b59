package org.usfirst.frc.team4911.robot.commands;


import org.usfirst.frc.team4911.robot.commands.*;
import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.ContainerLiftSystem;
import org.usfirst.frc.team4911.robot.subsystems.HookLiftSystem;
import org.usfirst.frc.team4911.robot.subsystems.MecanumDriveSystem;
import org.usfirst.frc.team4911.robot.subsystems.PrintSystem;
import org.usfirst.frc.team4911.robot.subsystems.SensorSystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousStackCrates extends CommandGroup {
	private MecanumDriveSystem mecanumDriveSystem = Robot.mecanumDriveSystem;
	private SensorSystem sensorSystem = Robot.sensorSystem;
	private ContainerLiftSystem containerLiftSystem = Robot.containerLiftSystem;
	private HookLiftSystem hookLiftSystem = Robot.hookLiftSystem;
	private PrintSystem printSystem = Robot.printSystem;
	private OI oi = Robot.oi;
	
    public  AutonomousStackCrates() {

    	addParallel(new MoveToteLift(RobotConstants.TOTE_CLEAR_CONTAINER_POSITION));
    	addSequential(new PIDAxisDrive(0.4,0.0, 1000.0)); // drives across right 1000 encoder pulses
    	addSequential(new PIDAxisDrive(0.0,-0.1, 10.0)); // drives backwards 10 encoder pulses
    	addSequential(new MoveToteLift(RobotConstants.TOTE_STACK_POSITION));
    	addSequential(new PIDAxisDrive(0.0,0.1, 10.0)); // drives backwards 10 encoder pulses
    	addSequential(new PIDAxisDrive(0.1,0.0, 10.0)); // drives backwards 10 encoder pulses
    	addSequential(new MoveToteLift(RobotConstants.TOTE_RELEASE_POSITION));
    	addSequential(new PIDAxisDrive(0.0,-0.1, 10.0)); // drives backwards 10 encoder pulses
    	addSequential(new MoveToteLift(RobotConstants.TOTE_GROUND_POSITION));
    	addSequential(new PIDAxisDrive(0.0,0.1, 10.0)); // drives backwards 10 encoder pulses
    	addSequential(new MoveToteLift(RobotConstants.TOTE_ACQUIRE_POSITION));
    	

    	addParallel(new MoveToteLift(RobotConstants.TOTE_CLEAR_CONTAINER_POSITION));
    	addSequential(new PIDAxisDrive(0.4,0.0, 1000.0)); // drives across right 1000 encoder pulses
    	addSequential(new PIDAxisDrive(0.0,-0.1, 10.0)); // drives backwards 10 encoder pulses
    	addSequential(new MoveToteLift(RobotConstants.TOTE_STACK_POSITION));
    	addSequential(new PIDAxisDrive(0.0,0.1, 10.0)); // drives backwards 10 encoder pulses
    	addSequential(new PIDAxisDrive(0.1,0.0, 10.0)); // drives backwards 10 encoder pulses
    	addSequential(new MoveToteLift(RobotConstants.TOTE_RELEASE_POSITION));
    	addSequential(new PIDAxisDrive(0.0,-0.1, 10.0)); // drives backwards 10 encoder pulses
    	addSequential(new MoveToteLift(RobotConstants.TOTE_GROUND_POSITION));
    	addSequential(new PIDAxisDrive(0.0,0.1, 10.0)); // drives backwards 10 encoder pulses
    	addSequential(new MoveToteLift(RobotConstants.TOTE_ACQUIRE_POSITION));
    	
    	
    	
    	
    	
    	
    	
    	// Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
