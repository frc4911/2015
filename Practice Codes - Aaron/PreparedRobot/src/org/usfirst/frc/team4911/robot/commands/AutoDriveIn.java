package org.usfirst.frc.team4911.robot.commands;

import org.usfirst.frc.team4911.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveIn extends CommandGroup {
    
	
	////////////////////////////////////////////////////////////////////////
	//
	//	-----	------	-----	-----
	//    |	    |		|		  |
	//	  |		|----	-----	  |
	//    |		|			|	  |
	//	  |		|____	-----	  |
	//
	////////////////////////////////////////////////////////////////////////
	
    public  AutoDriveIn(double stagingZones) {
    	
    	// ======================= STARTING POSITION INSTRUCTIONS ===========================
    	// For this command the robot will slide down the staging zone, into the autonomous
    	// zone, and then back down the autonomous zone. For this the robot must be placed
    	// a certain distance down the staging zone from the left proportional to the parameter
    	// stagingZones 
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
    	
    	addSequential(new PIDAxisDrive(-0.5, 0.0, RobotConstants.DRIVE_ONE_STAGE_ZONE * stagingZones));
    	addSequential(new SetGoalHeading(90));
    	addSequential(new PIDAxisDrive(0.0, 0.5,  1024.0*30.5*3.0));
    	addSequential(new SetGoalHeading(0));
    	addSequential(new PIDAxisDrive(0.5, 0.0, RobotConstants.DRIVE_ONE_STAGE_ZONE * (2.0-stagingZones)));
    }
}
