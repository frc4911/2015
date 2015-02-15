package org.usfirst.frc.team4911.robot;


import java.io.File;


import java.io.PrintStream;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;


import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;




public class Robot extends SampleRobot {
	CANTalon leftFront3;
	CANTalon leftRear4;
	CANTalon rightFront7;
	CANTalon rightRear8;
	RobotDrive robot;
	PrintStream output;


	public Robot() {
		leftFront3 = new CANTalon(3); // Initialize the CanTalonSRX on device 1.
		leftFront3.changeControlMode(CANTalon.ControlMode.PercentVbus);
		leftFront3.setPID(1.0, 0.0, 0.0);
		//1  
		leftRear4 = new CANTalon(4); // Initialize the CanTalonSRX on device 1.
		leftRear4.changeControlMode(CANTalon.ControlMode.PercentVbus);
		//3  
		rightFront7 = new CANTalon(7); // Initialize the CanTalonSRX on device 1.
		rightFront7.changeControlMode(CANTalon.ControlMode.PercentVbus);
		//4  
		rightRear8 = new CANTalon(8); // Initialize the CanTalonSRX on device 1.
		rightRear8.changeControlMode(CANTalon.ControlMode.PercentVbus);
		
		robot = new RobotDrive(leftFront3, leftRear4, rightFront7, rightRear8);
	}

	public void operatorControl() {
		try {
			output = new PrintStream(new File("/home/lvuser/natinst/teleLog.txt"));
			System.setOut(output);
		} catch (Exception e){
			
		}
		
		
		while (isOperatorControl() && isEnabled()) {
			`
		}
		
		output.close();
	}
}
