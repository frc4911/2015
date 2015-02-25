package org.usfirst.frc.team4911.robot.commands;


import org.usfirst.frc.team4911.robot.commands.*;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.MecanumDriveSystem;

/**
 *
 */
public class AutoZoneTote extends CommandGroup {
    
    public  AutoZoneTote() {
	addSequential(new MoveToteLiftForTime(0.5, 0.3));
        addSequential(new DriveForTime(-0.3, 0, 4));
    }
}
