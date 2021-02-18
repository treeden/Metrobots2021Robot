package frc.team3324.robot.autocommands

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.team3324.library.commands.MotorCommand
import frc.team3324.library.subsystems.MotorSubsystem
import frc.team3324.robot.shooter.Shooter
import frc.team3324.robot.shooter.commands.RunShooter

class ShooterAndStorageParallel(pivot: MotorSubsystem, shooter: Shooter, storage: MotorSubsystem, area: () -> Double): ParallelCommandGroup() {

    init {
        addCommands(RunShooter(shooter, area, true), WaitCommand(2.0).andThen(MotorCommand(storage,0.4)), MotorCommand(pivot, 0.5))
    }
}