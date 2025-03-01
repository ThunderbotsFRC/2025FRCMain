// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class MoveArm extends Command {
  private final ArmSubsystem m_armSubsystem;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public MoveArm(ArmSubsystem subsystem) {
    m_armSubsystem = subsystem;
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.setDefaultNumber("P", 0.002);
    SmartDashboard.setDefaultNumber("I", 0);
    SmartDashboard.setDefaultNumber("D", 7);
 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Sets the motor to the result of the pid and clamped
    //SmartDashboard.putNumber("actTar", m_armSubsystem.target);
    //SmartDashboard.putNumber("tar", -m_armSubsystem.ArmPIDCont.calculate(m_armSubsystem.ArmEncoder.getDistance(), m_armSubsystem.target));
    m_armSubsystem.ArmPIDCont.setP(SmartDashboard.getNumber("P", 0.002));
    m_armSubsystem.ArmPIDCont.setI(SmartDashboard.getNumber("I", 0));
    m_armSubsystem.ArmPIDCont.setD(SmartDashboard.getNumber("D", 7));
    if (m_armSubsystem.pos) {
      System.out.println(m_armSubsystem.pos);
      double armSpeed = -MathUtil.clamp(m_armSubsystem.ArmPIDCont.calculate(m_armSubsystem.ArmEncoder.getDistance(), m_armSubsystem.target), -0.6, 0.6);
      SmartDashboard.putNumber("spd", armSpeed);
      m_armSubsystem.ArmMotor.set(armSpeed);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
