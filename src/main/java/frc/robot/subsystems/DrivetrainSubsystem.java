package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DrivetrainSubsystem extends SubsystemBase {
    private final Spark leftMotor = new Spark(0); // Replace with actual port
    private final Spark rightMotor = new Spark(1); // Replace with actual port
    private final DifferentialDrive drive = new DifferentialDrive(leftMotor, rightMotor);

    private final Encoder leftEncoder = new Encoder(0, 1);
    private final Encoder rightEncoder = new Encoder(2, 3);

    private static final double WHEEL_DIAMETER_METERS = 0.155; // Example: 15 cm diameter
    private static final double ENCODER_COUNTS_PER_REVOLUTION = 360;
    public DrivetrainSubsystem() {
        rightMotor.setInverted(true); // Invert one side if needed
        leftEncoder.setDistancePerPulse((Math.PI * WHEEL_DIAMETER_METERS) / ENCODER_COUNTS_PER_REVOLUTION);
        rightEncoder.setDistancePerPulse((Math.PI * WHEEL_DIAMETER_METERS) / ENCODER_COUNTS_PER_REVOLUTION);
    }

    public void arcadeDrive(double forward, double rotation) {
        drive.arcadeDrive(forward, rotation);
    }

    public void stop() {
        drive.stopMotor();
    }

    public double getAverageEncoderPosition() {
        // Example value for average encoder position
        return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2.0;
    }

    public double getEncoderCountsPerMeter() {
        // Example value for encoder counts per meter
        return ENCODER_COUNTS_PER_REVOLUTION / (Math.PI * WHEEL_DIAMETER_METERS);
    }
}