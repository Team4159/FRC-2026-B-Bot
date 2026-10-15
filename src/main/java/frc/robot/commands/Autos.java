// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Shooter;
import choreo.auto.AutoFactory;
import choreo.auto.AutoRoutine;
import choreo.auto.AutoTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Autos {
    private final AutoFactory factory;
    private final Shooter shooter;

    public Autos(Drivetrain drivetrain, Shooter shooter) {
        this.shooter = shooter;
        factory = new AutoFactory(
            () -> drivetrain.getState().Pose,
            drivetrain::resetPose,
            drivetrain::followTrajectory,
            true, //flip path for red alliance
            drivetrain);
    }

    public AutoRoutine driveAndShoot() {
        AutoRoutine routine = factory.newRoutine("driveAndShoot");
        AutoTrajectory path = routine.trajectory("StartToShoot"); //file name add later idk what to do for it lwk

        routine.active().onTrue(Commands.sequence(path.resetOdometry(), path.cmd()));
        path.done().onTrue(shoot());
        return routine;
    }

    private Command shoot() {
        return Commands.sequence(
            Commands.runOnce(shooter::runFlywheelTower, shooter),
            Commands.waitSeconds(1.5), 
            Commands.runOnce(shooter::runFeeder),
            Commands.waitSeconds(1.0),
            Commands.runOnce(() -> { shooter.stopFeeder(); shooter.stopFlywheel(); }));
    }
}
