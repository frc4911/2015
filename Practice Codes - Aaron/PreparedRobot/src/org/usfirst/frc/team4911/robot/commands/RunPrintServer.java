package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.CANTalon;

import org.usfirst.frc.team4911.robot.subsystems.PrintSystem;
import org.usfirst.frc.team4911.robot.RobotMap;
import org.usfirst.frc.team4911.robot.Robot;

/**
 *
 */
public class RunPrintServer extends Command {
	private PrintSystem printSystem;
	private CANTalon leftFront;
	private CANTalon leftRear;
	private CANTalon rightFront;
	private CANTalon rightRear;
	
    public RunPrintServer() {
    	leftFront = RobotMap.leftFront;
    	leftRear = RobotMap.leftRear;
    	rightFront = RobotMap.rightFront;
    	rightRear = RobotMap.rightRear;
    	printSystem = Robot.printSystem;
    	printSystem.createNewFile();
    }

    protected void initialize() {
    	printSystem.resetIteration();
    }

    protected void execute() {
    	//printSystem.print("Motor Output", leftFront.getOutputCurrent());
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
