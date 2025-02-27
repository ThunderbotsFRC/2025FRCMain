package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.MotorConstants.*;
import static frc.robot.Constants.ArmConstants.*;

public class ArmSubsystem extends SubsystemBase {
    private Spark ArmMotor;
    private AnalogEncoder ArmEncoder;
    private PIDController ArmPIDCont;
    private boolean isArmUp = false; // Track the arm position state

    private Spark Motor1, Motor2;
    private boolean toggle = false;
    private int lastPow = 0;    
    
    public ArmSubsystem() {
        ArmMotor = new Spark(kArmMotorPort); // Updated to use port 4
        ArmEncoder = new AnalogEncoder(kArmEncoderPort);
        ArmPIDCont = new PIDController(17, 0, 0.8);

        Motor1 = new Spark(kMotor1Port);
        Motor2 = new Spark(kMotor2Port);
    }

    public Command moveArm(Double pos) {
        return run(() -> {
            // Clamps the target position to safe bounds
            double target = MathUtil.clamp(pos, kArmLowerBound, kArmUpperBound);

            // Sets the motor to the result of the pid and clamped
            ArmMotor.set(MathUtil.clamp(ArmPIDCont.calculate(ArmEncoder.get(), target), -0.7, 0.7));
        });
    }

    public Command toggleArmPosition() {
        return run(() -> {
            double target = isArmUp ? 0.0 : 0.3; // Toggle between 0.0 and 0.3
            isArmUp = !isArmUp; // Update the state

            // Clamps the target position to safe bounds
            target = MathUtil.clamp(target, kArmLowerBound, kArmUpperBound);

            // Sets the motor to the result of the pid and clamped
            ArmMotor.set(MathUtil.clamp(ArmPIDCont.calculate(ArmEncoder.get(), target), -0.7, 0.7));
        });
    }

    public Command setArmMotors(double pow) {
        return run(() -> {
            Motor1.set(pow);
            Motor2.set(pow);
        });
    }

    public Command setMotors(int pow) {
        return run(() -> {
            if (lastPow == pow) {
                toggle = !toggle;
            } else {
                toggle = true;
            }
            if (toggle) {
                Motor1.set(pow);
                Motor2.set(pow);
            } else {
                Motor1.set(0);
                Motor2.set(0);
            }
            lastPow = pow;
        });
    }
}
