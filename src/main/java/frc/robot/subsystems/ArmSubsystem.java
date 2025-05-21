package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.MotorConstants.*;

public class ArmSubsystem extends SubsystemBase {
    public Spark ArmMotor;
    public Encoder ArmEncoder;
    public PIDController ArmPIDCont;
    public boolean isArmUp = false; // Track the arm position state
    public int target;
    public boolean pos = false;

    public Spark Motor1, Motor2;
    
    public ArmSubsystem() {
        ArmMotor = new Spark(kArmMotorPort); // Updated to use port 4
        ArmEncoder = new Encoder(0, 1, true);
        ArmPIDCont = new PIDController(0.002, 0, 7);

        Motor1 = new Spark(kMotor1Port);
        Motor1.setInverted(true);
        Motor2 = new Spark(kMotor2Port);
    }

    public Command SwitchMode(boolean mode) {
        return runOnce(() -> {
            pos = mode;
        });
    }

    public Command moveArm(double speed) {
        return runOnce(() -> {
            if (speed != 0)
                pos = false;
            if (!pos) {
                System.out.println(speed);
                ArmMotor.set(speed);
            }
        });
    }

    public Command moveArmTo(int posi) {
        return runOnce(() -> {
            pos = true;
            target = posi;
            System.out.println(posi);
        });
    }

    public Command setClawMotors(double pow) {
        return run(() -> {
            SmartDashboard.putNumber("M1P", pow);
            Motor1.set(pow);
            Motor2.set(pow);
        });
    }
}
