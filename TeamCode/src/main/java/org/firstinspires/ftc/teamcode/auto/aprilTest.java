package org.firstinspires.ftc.teamcode.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.Hard_Auto;
import org.firstinspires.ftc.teamcode.util.threads.auton.customDetectLibrary;
@Disabled
@Autonomous(name="Safe Auto", group = "Auto")
public class aprilTest extends LinearOpMode {
    Hard_Auto r = new Hard_Auto();
    customDetectLibrary c;

    @Override
    public void runOpMode() throws InterruptedException {
        r.init_robot(this);
        c = new customDetectLibrary(this, r);
        int test = c.scanAprilTag(7000);
        waitForStart();
        r.distance_move(3, Hard_Auto.direction.FORWARD, 0.8);
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
