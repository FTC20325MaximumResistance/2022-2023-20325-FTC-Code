package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Hardware;
@Disabled
@TeleOp(name = "myNormalChildren", group = "TeleOp")
public class myNormalChildren extends LinearOpMode {
    Hardware r = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {
        r.init_robot(this);
        waitForStart();
        while (!isStopRequested()){
            r.waiter(1667/2);
            r.backright.setState(!r.backright.getState());
            r.backleft.setState(!r.backleft.getState());
        }
    }


}
