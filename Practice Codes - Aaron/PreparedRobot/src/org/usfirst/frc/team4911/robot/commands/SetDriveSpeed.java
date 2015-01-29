package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team4911.robot.subsystems.*;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.RobotConstants;

/**
 *
 */
public class SetDriveSpeed extends Command {
	double speed;
	JoystickButton button;
	
    public SetDriveSpeed(double speed, JoystickButton button) {
        this.speed = speed;
        this.button = button;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.teleOp.speed = speed;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !button.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.teleOp.speed = RobotConstants.STANDARD_DRIVE_SPEED;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
