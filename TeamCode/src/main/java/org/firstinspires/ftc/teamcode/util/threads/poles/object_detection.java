package org.firstinspires.ftc.teamcode.util.threads.poles;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.Hardware;
import org.firstinspires.ftc.teamcode.util.threads.crane.RobotArm;

public class object_detection extends Thread{

    OpMode opMode;
    int height;
    Hardware r = new Hardware();
    public object_detection(OpMode opMode, int height){
        this.opMode = opMode;
        this.height = height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean detect_object(String label){
        return true;
    }


    @Override
    public void run(){
        super.run();
        RobotArm ra = new RobotArm(opMode, r.arm1, r.arm2, r.arm3);
        ra.set_height(height);
        if (detect_object("pole")) {
            opMode.gamepad2.rumble(100);
            if (opMode.gamepad2.triangle) {
                ra.start();

            }
        }

    }
}
