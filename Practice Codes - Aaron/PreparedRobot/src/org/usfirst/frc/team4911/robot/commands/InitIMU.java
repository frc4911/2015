package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.subsystems.SensorSystem;

public class InitIMU extends Command {
	private SensorSystem sensorSystem = Robot.sensorSystem;
	
    public InitIMU() {
        requires(sensorSystem);
    }

    protected void initialize() {
    	sensorSystem.init();
    }

    protected void execute() {
    }
    
    protected boolean isFinished() {
    	//return sensorSystem.isDoneCalibrating();
        return true;
    }

    protected void end() {
    	//sensorSystem.zeroYaw();
    }

    protected void interrupted() {
    }
}