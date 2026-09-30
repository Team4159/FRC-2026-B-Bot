package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase{
    private final TalonFX flywheelTopLeftMotor;
    private final TalonFX flywheelBottomLeftMotor;
    private final TalonFX flywheelTopRightMotor;
    private final TalonFX flywheelBottomRightMotor;

    private final TalonFX feederMotor;

    private final VelocityVoltage flywheel_request = new VelocityVoltage(0);

    //im not sure if the velocity is correct, plz change the double
    private final VelocityVoltage feeder_request = new VelocityVoltage(0);



    public Shooter(){
        flywheelTopLeftMotor = new TalonFX(ShooterConstants.FLYWHEEL_MOTOR_TOP_LEFT_ID);
        flywheelBottomLeftMotor = new TalonFX(ShooterConstants.FLYWHEEL_MOTOR_BOTTOM_LEFT_ID);
        flywheelTopRightMotor = new TalonFX(ShooterConstants.FLYWHEEL_MOTOR_TOP_RIGHT_ID);
        flywheelBottomRightMotor = new TalonFX(ShooterConstants.FLYWHEEL_MOTOR_BOTTOM_RIGHT_ID);

        feederMotor = new TalonFX(ShooterConstants.FEEDER_MOTOR_ID); 

        flywheelTopLeftMotor.getConfigurator().apply(ShooterConstants.FLYWHEEL_CONFIG);
        flywheelBottomLeftMotor.getConfigurator().apply(ShooterConstants.FLYWHEEL_CONFIG);
        flywheelTopRightMotor.getConfigurator().apply(ShooterConstants.FLYWHEEL_CONFIG);
        flywheelBottomRightMotor.getConfigurator().apply(ShooterConstants.FLYWHEEL_CONFIG);

    }

    public void runFlywheel() {
        flywheelTopLeftMotor.setControl(flywheel_request.withVelocity(ShooterConstants.FLYWHEEL_SPEED));
        flywheelBottomLeftMotor.setControl(flywheel_request.withVelocity(ShooterConstants.FLYWHEEL_SPEED));
        flywheelTopRightMotor.setControl(flywheel_request.withVelocity(ShooterConstants.FLYWHEEL_SPEED));
        flywheelBottomRightMotor.setControl(flywheel_request.withVelocity(ShooterConstants.FLYWHEEL_SPEED));
    }
    public void stopFlywheel(){
        flywheelTopLeftMotor.stopMotor();
        flywheelBottomLeftMotor.stopMotor();
        flywheelTopRightMotor.stopMotor();
        flywheelBottomRightMotor.stopMotor();
    }

    public void runFeeder(){
        feederMotor.setControl(feeder_request.withVelocity(ShooterConstants.FEEDER_SPEED));
    }
    public void stopFeeder(){
        feederMotor.stopMotor();
    }
}
