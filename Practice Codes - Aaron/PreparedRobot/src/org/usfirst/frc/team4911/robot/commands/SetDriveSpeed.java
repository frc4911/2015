package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team4911.robot.subsystems.*;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.RobotConstants;

public class SetDriveSpeed extends Command {
    double speed;
    JoystickButton button;
	
    public SetDriveSpeed(double speed, JoystickButton button) {
	this.speed = speed;
        this.button = button;
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.teleOp.speed = speed;
    }

    protected boolean isFinished() {
        return !button.get();
    }

    protected void end() {
    	Robot.teleOp.speed = RobotConstants.STANDARD_DRIVE_SPEED;
    }

    protected void interrupted() {
    }
}
