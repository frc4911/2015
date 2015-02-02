package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.subsystems.*;

/**
 *
 */
public class RunPrintServer extends Command {
	private PrintSystem printSystem;
	private SensorSystem sensorSystem;
	private MecanumDriveSystem driveSystem;
	
    public RunPrintServer() {
    	printSystem = Robot.printSystem;
    	sensorSystem = Robot.sensorSystem;
    	driveSystem = Robot.mecanumDriveSystem;
    }

    protected void initialize() {
    	printSystem.resetIteration();//for Frequency
    	printSystem.createNewFile();
    }

    protected void execute() {
    	DriverStation driverStation = DriverStation.getInstance();
    	Runtime runtime = Runtime.getRuntime();
        double cpuUsage = (double)runtime.totalMemory() / (double)runtime.maxMemory();
    	
    	printSystem.print("Battery", PrintSystem.format(driverStation.getBatteryVoltage()), "V");
    	printSystem.print("BrownOut", driverStation.isBrownedOut()); 
    	printSystem.print("CPU Usage", PrintSystem.format(cpuUsage * 100.0), "%");
    	printSystem.print("MotorCurrent", 
    				PrintSystem.format(driveSystem.getLeftFrontCurrent()) 
    			+ " A __________________ " 
    			+ PrintSystem.format(driveSystem.getRightFrontCurrent())
    			+ " A\n\t\t\t\t\t\t\t\t\t\t  |                |"
    			+ "\n\t\t\t\t\t\t\t\t\t\t  |     ROBOT      |"
    			+ "\n\t\t\t\t\t\t\t\t\t  " 
    			+ PrintSystem.format(driveSystem.getLeftRearCurrent()) 
    			+ " A |________________| " 
    			+ PrintSystem.format(driveSystem.getRightRearCurrent())
    			+ " A");
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
