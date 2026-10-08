package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
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
    private final DutyCycleOut feeder_request = new DutyCycleOut(0);



    public Shooter(){
        flywheelTopLeftMotor = new TalonFX(ShooterConstants.FLYWHEEL_MOTOR_TOP_LEFT_ID);
        flywheelBottomLeftMotor = new TalonFX(ShooterConstants.FLYWHEEL_MOTOR_BOTTOM_LEFT_ID);
        flywheelTopRightMotor = new TalonFX(ShooterConstants.FLYWHEEL_MOTOR_TOP_RIGHT_ID);
        flywheelBottomRightMotor = new TalonFX(ShooterConstants.FLYWHEEL_MOTOR_BOTTOM_RIGHT_ID);

        feederMotor = new TalonFX(ShooterConstants.FEEDER_MOTOR_ID); 

        TalonFXConfiguration LEFT_CONFIG = ShooterConstants.FLYWHEEL_CONFIG.clone();
        LEFT_CONFIG.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);

        TalonFXConfiguration RIGHT_CONFIG = ShooterConstants.FLYWHEEL_CONFIG.clone();
        RIGHT_CONFIG.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);

        flywheelTopLeftMotor.getConfigurator().apply(LEFT_CONFIG);
        flywheelBottomLeftMotor.getConfigurator().apply(LEFT_CONFIG);
        flywheelTopRightMotor.getConfigurator().apply(RIGHT_CONFIG);
        flywheelBottomRightMotor.getConfigurator().apply(RIGHT_CONFIG);


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
        feederMotor.setControl(feeder_request.withOutput(ShooterConstants.FEEDER_SPEED));
    }
    public void runRevereseFeeder(){
        feederMotor.setControl(feeder_request.withOutput(-ShooterConstants.FEEDER_SPEED));
    }
    public void stopFeeder(){
        feederMotor.stopMotor();
    }
}
