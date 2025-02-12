package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MotorConstants;

/**
 * This is the Driving subsystem that handles the robot's drive logic.
 */
public class Driving extends SubsystemBase {
    
    private final Spark leftMotor = new Spark(MotorConstants.kLeftMotor1Port);
    private final Spark rightMotor = new Spark(MotorConstants.kRightMotor1Port);
    
    private final double speedScale = 1.0;
    private final double deadzone = 0.07;

    public Driving() {
        // Constructor logic if needed (e.g., motor initialization).
    }

    public void setMotors(double leftPower, double rightPower) {
        leftMotor.set(leftPower);
        rightMotor.set(rightPower);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        leftSpeed = applyDeadzone(leftSpeed);
        rightSpeed = applyDeadzone(rightSpeed);
        setMotors(leftSpeed, rightSpeed);
    }

    private double applyDeadzone(double value) {
        if (Math.abs(value) < deadzone) {
            return 0;
        }
        return value * speedScale; // You can adjust the scale if needed
    }
    
    public void stop() {
        setMotors(0, 0); // Stop both motors
    }
}
