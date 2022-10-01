package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Hardware;
@TeleOp(name = "Playground", group = "TeleOp")
public class playground extends OpMode {
    Hardware r = new Hardware();
    double deflator;
    double r1a = 0;
    double r2a = 0;
    @Override
    public void init() {
        r.init_robot(this);
    }

    @Override
    public void loop() {
        r1a += gamepad1.left_stick_x * 360;
        if (r1a > 360){
            r1a = 360;
        }else if (r1a < 0){
            r1a = 0;
        }
        if (gamepad1.left_trigger > 0){
            r2a = 0;
        }else if (gamepad1.right_trigger > 0){
            r2a = 360;
        }
        r.claw.setPosition(r2a);
        r.spin.setPosition(r1a);


        
    }
}
