package org.firstinspires.ftc.teamcode.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.Hard_Auto;
import org.firstinspires.ftc.teamcode.util.threads.auton.customDetectLibrary;
@Disabled
@Autonomous(name="Safe Auto With Junction", group = "Auto")
public class aprilJunction extends LinearOpMode {
    Hard_Auto r = new Hard_Auto();
    customDetectLibrary c;

    @Override
    public void runOpMode() throws InterruptedException {
        r.init_robot(this);
        r.claw.setPosition(0);
        c = new customDetectLibrary(this, r);
        int test = c.scanAprilTag(7000);
        waitForStart();
        r.distance_move(3, Hard_Auto.direction.FORWARD, 0.8);
        r.distance_move(24, Hard_Auto.direction.LEFT, 0.8);
        r.degree_rotate(120, Hard_Auto.direction.LEFT, 0.8);
        r.claw.setPosition(1);
        r.degree_rotate(120, Hard_Auto.direction.RIGHT, 0.8);
        r.distance_move(24, Hard_Auto.direction.RIGHT, 0.8);
        switch (test){
            case 1:
                r.distance_move(24, Hard_Auto.direction.LEFT,0.8);
                r.distance_move(27, Hard_Auto.direction.FORWARD, 0.8);
                break;
            case 2:
                r.distance_move(27, Hard_Auto.direction.FORWARD, 0.8);
                break;
            default:
                r.distance_move(24, Hard_Auto.direction.RIGHT, 0.8);
                r.distance_move(27, Hard_Auto.direction.FORWARD, 0.8);
                break;
        }


    }
}
