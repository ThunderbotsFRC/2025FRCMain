package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.MotorConstants.*;

import java.util.function.DoubleSupplier;

public class DriveSubsystem extends SubsystemBase {
    private Spark LeftFront, LeftBack, RightFront, RightBack;
    private DifferentialDrive DiffDrive;
    
    public DriveSubsystem() {
        LeftFront = new Spark(kLeftMotor1Port);
        LeftBack = new Spark(kLeftMotor2Port);
        RightFront = new Spark(kRightMotor1Port);
        RightBack = new Spark(kRightMotor2Port);
        LeftFront.addFollower(LeftBack);
        RightFront.addFollower(RightBack);
        RightFront.setInverted(true);
        DiffDrive = new DifferentialDrive(LeftFront, RightFront);
    }

    public void TankDriveNoC(double xSpeed, double zRotation) {
        DiffDrive.arcadeDrive(xSpeed*-0.8, zRotation*-0.8);
    }

    public Command TankDrive(DoubleSupplier xSpeed, DoubleSupplier zRotation) {
        return run(() -> {
            DiffDrive.arcadeDrive(xSpeed.getAsDouble()*-0.8, zRotation.getAsDouble()*-0.8);
            
        });
    }

    public Command TankDrive(double xSpeed, double zRotation) {
        return run(() -> {
            DiffDrive.arcadeDrive(xSpeed*-0.8, zRotation*-0.8);
            System.out.println(xSpeed);
        });
    }
}
