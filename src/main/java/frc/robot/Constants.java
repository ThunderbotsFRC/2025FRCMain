// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

	// holds the port values for all the motors
	public static class MotorConstants {
		public static final int kLeftMotor1Port   = 0;
		public static final int kLeftMotor2Port   = 1;
		
		public static final int kRightMotor1Port  = 2;
		public static final int kRightMotor2Port  = 3;

		public static final int kArmMotorPort     = 4; // Updated to port 4
		public static final int kMotor1Port       = 5;
		public static final int kMotor2Port       = 6;
  	}

	// holds the positions of different values for the arms
	public static class ArmConstants {
		public static final int kArmEncoderPort = 0;
		public static final double kArmLowerBound = 0.0;
		public static final double kArmUpperBound = 1.0;
	}
}
