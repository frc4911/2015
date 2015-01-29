package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.subsystems.CameraSystem;

public class SetCameraServo extends Command {
	
	private CameraSystem cameraSystem;
	private double pos;
	
    public SetCameraServo(double pos) {
    	this.pos = pos;
    }

    
    protected void initialize() {
    	cameraSystem = Robot.cameraSystem;
    	requires(cameraSystem);
    }

    
    protected void execute() {
    }

    
    protected boolean isFinished() {
        return true;
    }

    
    protected void end() {
    	cameraSystem.setServo(pos);
    }

    
    protected void interrupted() {
    }
}
