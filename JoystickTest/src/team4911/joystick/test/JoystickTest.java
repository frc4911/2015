package team4911.joystick.test;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickTest {

	public static Joystick stick1;
	
	public static void main(String[] args) {
		stick1 = new Joystick(0);
		while(true) {
			System.out.println("Joy y: " + stick1.getY());
		}
	}
	
}
