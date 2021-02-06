package frc.team3324.robot.drivetrain.commands.auto

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.util.Consts

class MeterForward(val driveTrain: DriveTrain, endState: TrapezoidProfile.State): CommandBase() {
    val constrainsts = TrapezoidProfile.Constraints(Consts.DriveTrain.LOW_GEAR_MAX_VELOCITY, Consts.DriveTrain.LOW_GEAR_MAX_ACCELERATION)
    val initialState = TrapezoidProfile.State(0.0, 0.0)
    val profile = TrapezoidProfile(constrainsts, initialState, endState)

    init {
        addRequirements(driveTrain)
    }

    var startTime = 0.0
    override fun initialize() {
        startTime = Timer.getFPGATimestamp()
    }

    override fun execute() {
        val time = Timer.getFPGATimestamp()
        val desiredState = profile.calculate(time - startTime)
        val acceleration = profile.calculate((time + 0.02) - startTime).velocity - desiredState.velocity
        val volt = desiredState.velocity * Consts.DriveTrain.LOW_GEAR_KV + acceleration * Consts.DriveTrain.LOW_GEAR_KA + Consts.DriveTrain.ksVolts
        SmartDashboard.putNumber("Volt", volt)
        driveTrain.tankDriveVolts(volt , volt)
    }

    override fun isFinished(): Boolean {
        return profile.isFinished(Timer.getFPGATimestamp() - startTime)
    }
}