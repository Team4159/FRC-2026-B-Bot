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

    private static final double STATOR_CURRENT_LIMIT = 80.0;
    private static final double SUPPLY_CURRENT_LIMIT = 80.0;


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

        configureCurrentLimits(RIGHT_CONFIG);
        configureCurrentLimits(LEFT_CONFIG);

        flywheelTopLeftMotor.getConfigurator().apply(LEFT_CONFIG);
        flywheelBottomLeftMotor.getConfigurator().apply(LEFT_CONFIG);
        flywheelTopRightMotor.getConfigurator().apply(RIGHT_CONFIG);
        flywheelBottomRightMotor.getConfigurator().apply(RIGHT_CONFIG);


    }

    private void configureCurrentLimits(TalonFXConfiguration config) {
        config.CurrentLimits.StatorCurrentLimit = STATOR_CURRENT_LIMIT;
        config.CurrentLimits.StatorCurrentLimitEnable = true;

        config.CurrentLimits.SupplyCurrentLimit = SUPPLY_CURRENT_LIMIT;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;
    }

    public void runFlywheelTower() {
        flywheelTopLeftMotor.setControl(flywheel_request.withVelocity(ShooterConstants.FLYWHEEL_TOWER_SPEED));
        flywheelBottomLeftMotor.setControl(flywheel_request.withVelocity(ShooterConstants.FLYWHEEL_TOWER_SPEED));
        flywheelTopRightMotor.setControl(flywheel_request.withVelocity(ShooterConstants.FLYWHEEL_TOWER_SPEED));
        flywheelBottomRightMotor.setControl(flywheel_request.withVelocity(ShooterConstants.FLYWHEEL_TOWER_SPEED));
    }
    public void runFlyWheelHub() {
        flywheelTopLeftMotor.setControl(flywheel_request.withVelocity(ShooterConstants.FLYWHEEL_HUB_SPEED));
        flywheelBottomLeftMotor.setControl(flywheel_request.withVelocity(ShooterConstants.FLYWHEEL_HUB_SPEED));
        flywheelTopRightMotor.setControl(flywheel_request.withVelocity(ShooterConstants.FLYWHEEL_HUB_SPEED));
        flywheelBottomRightMotor.setControl(flywheel_request.withVelocity(ShooterConstants.FLYWHEEL_HUB_SPEED));
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
