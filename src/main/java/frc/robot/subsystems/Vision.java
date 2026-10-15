package frc.robot.subsystems;

import com.ctre.phoenix6.Utils;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.generated.CommandSwerveDrivetrain;

public class Vision extends SubsystemBase{
    NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    Pose2d visionPose;
    CommandSwerveDrivetrain drivetrain;
    boolean doRejectUpdate, mt1rotation;
    ShuffleboardTab visionTab;
    Field2d visionField;

    public Vision(CommandSwerveDrivetrain drivetrain){
        this.drivetrain = drivetrain;
        visionTab = Shuffleboard.getTab("Vision");
        visionField = new Field2d();
        mt1rotation = false;
        Shuffleboard.getTab("Vision").add("visionPose", visionField);
        Shuffleboard.getTab("Vision").add("Megatag Yaw", mt1rotation);
    }

    @Override
    public void periodic(){
        // LimelightHelpers.SetRobotOrientation("limelight", drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
        // LimelightHelpers.PoseEstimate mt2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight");
        // if(Math.abs(drivetrain.getState().Speeds.omegaRadiansPerSecond) > 720*Math.PI/180) // if our angular velocity is greater than 720 degrees per second, ignore vision updates
        // {
        //     doRejectUpdate = true;
        // }
        // if(mt2.tagCount == 0)
        // {
        //     doRejectUpdate = true;
        // }
        // if(!doRejectUpdate)
        // {
        //     drivetrain.setVisionMeasurementStdDevs(VecBuilder.fill(0.7, 0.7, 9999999));
        //     drivetrain.addVisionMeasurement(mt2.pose, Timer.getFPGATimestamp() - mt2.timestampSeconds);
        //     visionField.setRobotPose(mt2.pose);
        //     Shuffleboard.getTab("Vision").add("Vision Pose", visionField);
        // }
        Rotation2d robotRotation = drivetrain.getState().Pose.getRotation();
        LimelightHelpers.SetRobotOrientation("limelight", robotRotation.getDegrees(), 0, 0, 0, 0, 0);
        double[] visionData;
        double area = limelight.getEntry("ta").getDouble(0);
        //if nearby use mt1 instead of mt2
        if(area >= 1){
            SmartDashboard.putBoolean("mt1", true);
            //System.out.println("mt1");
            if(!limelight.getEntry("botpose_wpiblue").exists()){
                visionPose = new Pose2d();
                return;
            }
            visionData = limelight.getEntry("botpose_wpiblue").getDoubleArray(new double[6]);
            visionPose = new Pose2d(visionData[0], visionData[1], new Rotation2d(Units.degreesToRadians(visionData[5])));
        }
        // else if(area >= 0.8){
        //     SmartDashboard.putBoolean("mt1", false);
        //     if(!limelight.getEntry("botpose_orb_wpiblue").exists()){
        //         visionPose = new Pose2d();
        //         return;
        //     }
        //     visionData = limelight.getEntry("botpose_orb_wpiblue").getDoubleArray(new double[6]);
        //     visionPose = new Pose2d(visionData[0], visionData[1], drivetrain.getState().Pose.getRotation());
        // }
        else{
            SmartDashboard.putBoolean("mt1", false);
            visionPose = new Pose2d();
            return;
        }
        
        if(visionPose != null && !visionPose.getTranslation().equals(new Translation2d(0, 0))){
            drivetrain.setVisionMeasurementStdDevs(VecBuilder.fill(1 - area * 0.3, 1 - area * 0.3, 1-area * 0.1));
            drivetrain.addVisionMeasurement(visionPose, Utils.getCurrentTimeSeconds() - Units.millisecondsToSeconds(
                limelight.getEntry("cl").getDouble(0) +
                limelight.getEntry("tl").getDouble(0)));
        }
        visionField.setRobotPose(visionPose);
    }
    public void forceVision(){
        if(visionPose != null && !visionPose.getTranslation().equals(new Translation2d(0, 0))){
            drivetrain.resetPose(visionPose);
        }
    }
}