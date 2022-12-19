package org.firstinspires.ftc.teamcode.judging;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.Hardware;
import org.firstinspires.ftc.teamcode.util.threads.crane.RobotArm;
@Disabled

public class manual_arm_control extends OpMode {

    Hardware r = new Hardware();
    boolean var_init = true;
    RobotArm ra;
    boolean select_1 = true;
    int degrees1 = 0;
    int degrees2 = 0;

    public void init(){
        r.init_robot(this);
    }

    @Override
    public void loop() {
        if (var_init){
            ra = new RobotArm(this, r);
        }
        if (gamepad1.b){
            select_1 = !select_1;
        }


        if (select_1) {
            if (gamepad1.dpad_up){degrees1 += 1;}
            if (gamepad1.dpad_down){degrees1-=1;}
            degrees1 = degrees1 % 360;
            ra.degree_move_arm(1, 0.7, degrees1);
        } else{
            if (gamepad1.dpad_up){degrees2 += 1;}
            if (gamepad1.dpad_down){degrees2-=1;}
            degrees2 = degrees2 % 360;
            ra.degree_move_arm(2, 0.7, degrees2);
        }


    }
}
