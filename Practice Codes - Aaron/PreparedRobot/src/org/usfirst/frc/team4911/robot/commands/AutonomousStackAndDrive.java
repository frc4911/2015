package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4911.robot.commands.*;
import org.usfirst.frc.team4911.robot.RobotConstants;

/**
 *
 */
public class AutonomousStackAndDrive extends CommandGroup {
    
    public  AutonomousStackAndDrive() {
    	
    	// ======================= STARTING POSITION INSTRUCTIONS ===========================
    	// For this command the robot must start in TOTE_ACQUIRE_POSITION ready to
    	// acquire the tote on the FAR LEFT SIDE OF THE FIELD. It will move left and stack on
    	// the next two totes to the left, giving it a stack of three. It will then drive into
    	// the autonomous zone, deposit the totes, back up slightly, and stop.
    	
        addSequential(new AutoStackCrateLeft());
        addSequential(new AutoStackCrateLeft());

    	addSequential(new PIDAxisDrive(0.1, 0.0, RobotConstants.CLEAR_TOTE_LIP_DISTANCE)); // drives accross right 10 encoder pulses
    	addSequential(new PIDAxisDrive(0.0, 0.5, RobotConstants.FORWARD_DISTANCE_TO_AUTO_ZONE)); // drives forward 500 encoder pulses
    	
    	addSequential(new MoveToteLift(RobotConstants.TOTE_GROUND_POSITION));
    	addSequential(new PIDAxisDrive(0.0, -0.5, RobotConstants.CLEAR_TOTE_LIP_DISTANCE)); // drives backward 10 encoder pulses
    }
}
