package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.Hard_Auto;
import org.firstinspires.ftc.teamcode.util.threads.auton.autoNavigate;

@Disabled
@Autonomous(name = "Playground", group = "Auto")
public class playground extends LinearOpMode {
    Hard_Auto r = new Hard_Auto();
    autoNavigate a;



    @Override
    public void runOpMode() throws InterruptedException {
        r.init_robot(this);
        a = new autoNavigate(this, r, 1, 1);
        waitForStart();
        //r.distance_move(24, Hard_Auto.direction.FORWARD, 0.5);
        //a.moveArm(90, 19, true, true);
        a.moveArm(0, 150, true, false, true);
        r.waiter(5000);

    }
}

