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

    protected void initialize() {
    }

    protected void execute() {
    	Robot.sensorSystem.zeroYaw();
    	Robot.mecanumDriveSystem.setGoalHeading(0.0);
    }

    protected boolean isFinished() {
        return !button.get();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
