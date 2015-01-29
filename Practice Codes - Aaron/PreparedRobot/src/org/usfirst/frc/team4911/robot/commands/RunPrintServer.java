package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.subsystems.PrintSystem;

/**
 *
 */
public class RunPrintServer extends Command {
	private PrintSystem printSystem;
	
    public RunPrintServer() {
    	printSystem = Robot.printSystem;
    	printSystem.createNewFile();
    }

    protected void initialize() {
    	printSystem.resetIteration();
    }

    protected void execute() {
    	printSystem.updatePrint();
    }

    protected boolean isFinished() {
        return false;
    }


    protected void end() {
    }

    protected void interrupted() {
    }
}