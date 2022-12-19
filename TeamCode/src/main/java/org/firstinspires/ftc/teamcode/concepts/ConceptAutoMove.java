package org.firstinspires.ftc.teamcode.concepts;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.Hard_Auto;
import org.firstinspires.ftc.teamcode.util.threads.auton.autoNavigate;
import org.firstinspires.ftc.teamcode.util.threads.crane.RobotArm;
@Disabled
@Autonomous(name="ConceptAutoMove", group = "Auto")
public class ConceptAutoMove extends LinearOpMode {
    Hard_Auto r = new Hard_Auto();
    autoNavigate a;
    RobotArm t;

    @Override
    public void runOpMode() throws InterruptedException {
        r.init_robot(this);
        a = new autoNavigate(this, r, 1, 5);
        t = new RobotArm(this, r);
        waitForStart();
        r.distance_move(5, Hard_Auto.direction.FORWARD, 0.5);
        a.MoveToPosition(3,5,0.5, true);


    }
}