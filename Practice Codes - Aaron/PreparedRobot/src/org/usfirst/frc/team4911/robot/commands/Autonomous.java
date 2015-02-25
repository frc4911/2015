package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4911.robot.RobotConstants;


public class Autonomous extends CommandGroup {
	private int mode = 4;//[1 - 4]
	

	public Autonomous(){
		addSequential(new ZeroYaw());
		switch(mode){
			case 1: 
				//////////////////////////////////////////////////////////////
				// 
				// 	Moving One Container To AutoZone
				//
				//	Start far Left, Ready to Grab the Container
				//////////////////////////////////////////////////////////////		
				addSequential(new CloseContainerClamp(0.25));
				addSequential(new MoveContainerLiftForTime(0.25, 0.5));
				addSequential(new DriveForTime(-0.3, 0, 4));
				break;
				
			case 2: 
				//////////////////////////////////////////////////////////////
				//  
				// 	Moving One Tote To AutoZone
				//
				// Start far Left, Ready to Grab the Tote
				//////////////////////////////////////////////////////////////
				addSequential(new MoveToteLiftForTime(0.5, 0.5));
				addSequential(new DriveForTime(-0.3, 0, 4));     
				break;
				
			case 3:
				//////////////////////////////////////////////////////////////
				// 
				// 	Drive From Staging Zone Around Scoring Zone to AutoZone
				//
			 	//////////////////////////////////////////////////////////////		
				//	NEED ENCODERS
				double stagingZones = 2.0;//What is the Unit? Why Double?
				
				addSequential(new PIDAxisDrive(-0.5, 0.0, RobotConstants.DRIVE_ONE_STAGE_ZONE * stagingZones));
		    	addSequential(new SetGoalHeading(90));
		    	addSequential(new PIDAxisDrive(0.0, 0.5,  1024.0*30.5*3.0));
		    	addSequential(new SetGoalHeading(0));
		    	addSequential(new PIDAxisDrive(0.5, 0.0, RobotConstants.DRIVE_ONE_STAGE_ZONE * (2.0-stagingZones)));
				break;
				
			case 4: 
		    	//////////////////////////////////////////////////////////////
				// 
				// 	Stacking Three Totes
				//
			 	//////////////////////////////////////////////////////////////
			 	 // Start far Right between the Scoring Zone and the Staging Zone
		    	
		    	double liftSpeed = 1.0;
				double driveSpeed = 0.3;
				
				addSequential(new MoveToteLiftForTime(4.0, liftSpeed));//Lifting Tote
				addSequential(new DriveForTime(-0.5, 0.0, 2.25));//Moving to Next Tote
				addSequential(new MoveToteLiftForTime(2, -liftSpeed));//Releasing Tote
				addSequential(new DriveForTime(0.0, -driveSpeed, 1.0));//Backing Up
				addSequential(new MoveToteLiftForTime(2, -liftSpeed));//Lowering lift
				addSequential(new DriveForTime(0.0,driveSpeed, 1.0));//MovingForward
			
				//Re-Iterate
				addSequential(new MoveToteLiftForTime(4.0, liftSpeed));//Lifting Tote
				addSequential(new DriveForTime(-0.5, 0.0, 2.25));//Moving to Next Tote
				addSequential(new MoveToteLiftForTime(2, -liftSpeed));//Releasing Tote
				addSequential(new DriveForTime(0.0, -driveSpeed, 1.0));//Backing Up
				addSequential(new MoveToteLiftForTime(2, -liftSpeed));//Lowering lift
				addSequential(new DriveForTime(0.0,driveSpeed, 1.0));//MovingForwardd
				
			
				addSequential(new MoveToteLiftForTime(1.0, liftSpeed));//Lifting Tote slightly
				addSequential(new RotateForTime(1.20, -0.6)); // MAKE PID ROTATE
				//addSequential(new DriveForTime(0.0, -driveSpeed, 4.0));//Drive into Auto zone (field oriented)
				break;
		}
	}

}
