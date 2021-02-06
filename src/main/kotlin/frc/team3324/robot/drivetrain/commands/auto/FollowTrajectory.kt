package frc.team3324.robot.drivetrain.commands.auto

import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.controller.RamseteController
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward
import edu.wpi.first.wpilibj.trajectory.Trajectory
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint
import edu.wpi.first.wpilibj2.command.RamseteCommand
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.robot.util.Consts
import frc.team3324.robot.drivetrain.DriveTrain


class FollowTrajectory(val driveTrain: DriveTrain, val trajectory: Trajectory) {
    val autoVoltageConstraint = DifferentialDriveVoltageConstraint(
            SimpleMotorFeedforward(Consts.DriveTrain.ksVolts,
                    Consts.DriveTrain.LOW_GEAR_KV,
                    Consts.DriveTrain.LOW_GEAR_KA),
            Consts.DriveTrain.kDriveKinematics, 10.0)

    val config = TrajectoryConfig(Consts.DriveTrain.LOW_GEAR_MAX_VELOCITY,
            Consts.DriveTrain.LOW_GEAR_MAX_ACCELERATION).setKinematics(Consts.DriveTrain.kDriveKinematics).addConstraint(autoVoltageConstraint)

    val ramseteCommand = RamseteCommand(
            trajectory,
            {driveTrain.pose},
            RamseteController(Consts.DriveTrain.kRamseteB, Consts.DriveTrain.kRamseteZeta),
            SimpleMotorFeedforward(Consts.DriveTrain.ksVolts, Consts.DriveTrain.LOW_GEAR_KV, Consts.DriveTrain.LOW_GEAR_KA),
            Consts.DriveTrain.kDriveKinematics,
            {driveTrain.wheelSpeeds},
            PIDController(Consts.DriveTrain.kP, 0.0, 0.0),
            PIDController(Consts.DriveTrain.kP, 0.0, 0.0),
            driveTrain::tankDriveVolts,
            arrayOf(driveTrain) // cursed
    )



}