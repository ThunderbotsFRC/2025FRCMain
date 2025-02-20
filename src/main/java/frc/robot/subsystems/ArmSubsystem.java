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
    
    public ArmSubsystem() {
        ArmMotor = new Spark(kArmMotorPort);
        ArmEncoder = new AnalogEncoder(kArmEncoderPort);
        ArmPIDCont = new PIDController(17, 0, 0.8);
    }

    public Command moveArm(Double pos) {
        return run(() -> {
            // Clamps the target position to safe bounds
            double target = MathUtil.clamp(pos, kArmLowerBound, kArmUpperBound);

            // Sets the motor to the result of the pid and clamped
            ArmMotor.set(MathUtil.clamp(ArmPIDCont.calculate(ArmEncoder.get(), target), -0.7, 0.7));
        });
    }
}
