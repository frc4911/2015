package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
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
    	if(RobotConstants.STATIC_INFO_ALLOWED){
    		addStaticInfo();
    	}
    	printSystem.updatePrint();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
    public void addStaticInfo(){
    	DriverStation driverStation = DriverStation.getInstance();
    	Runtime runtime = Runtime.getRuntime();
        double memoryUsage = (double)runtime.totalMemory() / (double)runtime.maxMemory();

    	printSystem.print("Battery", PrintSystem.format(driverStation.getBatteryVoltage()), "Volts");
    	printSystem.print("LIDAR", sensorSystem.getIN(), "inches");
    	printSystem.print("GoalHeading", driveSystem.getGoalHeading(), "degree");
    	printSystem.print("IMU Yaw", PrintSystem.format(sensorSystem.getYawWithCompensation()), "Degrees");
    	printSystem.print("IMU Pitch", PrintSystem.format(sensorSystem.getPitch()), "Degrees");
    	printSystem.print("IMU Roll", PrintSystem.format(sensorSystem.getRoll()), "Degrees");
    	printSystem.print("IMU Accel X", PrintSystem.format(sensorSystem.getAccelX()), "m/s^2");
    	printSystem.print("IMU Accel Y", PrintSystem.format(sensorSystem.getAccelY()), "m/s^2");
    	printSystem.print("IMU Accel Z", PrintSystem.format(sensorSystem.getAccelZ()), "m/s^2");
    	printSystem.print("IMU Temp", PrintSystem.format(sensorSystem.getTemp()), "Celsius");
    	printSystem.print("BrownOut", driverStation.isBrownedOut()); 
    	printSystem.print("Memory Usage", PrintSystem.format(memoryUsage * 100.0), "%");
    	printSystem.print("MotorCurrent", 
    				PrintSystem.format(driveSystem.getLeftFrontCurrent()) 
    			+ " A __________________ " 
    			+ PrintSystem.format(driveSystem.getRightFrontCurrent())
    			+ " A\n\t\t\t\t\t\t\t\t\t\t  |2              3|"
    			+ "\n\t\t\t\t\t\t\t\t\t\t  |     ROBOT      |"
    			+ "\n\t\t\t\t\t\t\t\t\t  " 
    			+ PrintSystem.format(driveSystem.getLeftRearCurrent()) 
    			+ " A |1______________4| " 
    			+ PrintSystem.format(driveSystem.getRightRearCurrent())
    			+ " A");
    }
}
