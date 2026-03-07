package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Simple Auto (Pedro)")
public class SimpleAuto extends LinearOpMode {

    final boolean StopAtEnd = true;
    final boolean ContinueToNextPath = false;

    public static Follower follower;

    @Override
    public void runOpMode() {

        follower = Constants.createFollower(hardwareMap);

        //
        //  SET POSES HERE
        //

        Pose startPose = new Pose(72, 100, Math.toRadians(270)); // Start Pose of our robot.
        Pose shootPose = new Pose(72, 72, Math.toRadians(270));
        Pose endPose = new Pose(48, 72, Math.toRadians(270));

        //
        //  SET PATHS HERE
        //

        PathChain startToShoot = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();

        PathChain shootToEnd = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, endPose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), endPose.getHeading())
                .build();

        PathChain endToStart = follower.pathBuilder()
                .addPath(new BezierLine(endPose, startPose))
                .setLinearHeadingInterpolation(endPose.getHeading(), startPose.getHeading())
                .build();

        //
        //  END OF PATHS
        //

        follower.setStartingPose(startPose);
        follower.setMaxPower(0.3);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        //
        //  FOLLOW PATHS HERE
        //

        follower.followPath(startToShoot, StopAtEnd);

        while (follower.isBusy() && opModeIsActive()) {
            follower.update();
        }

        // do something here like shoot
        sleep(5000);

        follower.followPath(shootToEnd, StopAtEnd);

        while (follower.isBusy() && opModeIsActive()) {
            follower.update();
        }
        
        follower.followPath(endToStart, StopAtEnd);

        while (follower.isBusy() && opModeIsActive()) {
            follower.update();
        }
    }
}
