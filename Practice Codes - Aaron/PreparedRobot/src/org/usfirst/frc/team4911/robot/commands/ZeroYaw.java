package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4911.robot.subsystems.MecanumDriveSystem;
import org.usfirst.frc.team4911.robot.Robot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 *
 */
public class ZeroYaw extends Command {

	private JoystickButton button;
	
    public ZeroYaw(JoystickButton button) {
        this.button = button;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.sensorSystem.zeroYaw();
    	Robot.mecanumDriveSystem.setGoalHeading(0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !button.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
