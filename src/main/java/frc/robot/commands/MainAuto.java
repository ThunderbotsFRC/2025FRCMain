// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/** An example command that uses an example subsystem. */
public class MainAuto extends Command {
  private final DriveSubsystem m_driveSubsystem;
  private final ArmSubsystem m_armSubsystem;

  private Timer timer = new Timer();

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public MainAuto(DriveSubsystem subsystem, ArmSubsystem subsystem2) {
    m_driveSubsystem = subsystem;
    m_armSubsystem = subsystem2;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem, subsystem2);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
    m_driveSubsystem.TankDrive(0.1, 0)
    .andThen(m_armSubsystem.moveArm(0.3))
    .andThen(new WaitCommand(2))
    .andThen(m_driveSubsystem.TankDrive(0, 0))
    .andThen(new WaitCommand(2))
    .andThen(m_armSubsystem.setArmMotors(-1));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Autonomous that just drives forward for 2 seconds (hopefully)
    // m_driveSubsystem.TankDrive(0.1, 0.0);
    
    // V worse code of the one in the initialize method V
    /*if (timer.get() < 2) {
      m_driveSubsystem.TankDrive(0.1, 0.0);
    } else if (timer.get() < 4) {
      m_driveSubsystem.TankDrive(0, 0);
      m_armSubsystem.moveArm(0.3);
    } else {
      m_armSubsystem.setArmMotors(-1);
    }*/
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_driveSubsystem.TankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // VV Autonomous that just drives forward for 2 seconds
    // return timer.get() > 2;
    return timer.get() > 5;
  }
}
