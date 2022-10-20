package org.firstinspires.ftc.teamcode.concepts;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Hardware;
@Disabled

@TeleOp(name = "crane_arm_test", group = "TeleOp")
public class crane_arm_test extends OpMode {

    Hardware r = new  Hardware();
    boolean arm1select = true;
    String aim = "Arm 1";
    boolean blank = true;
    int target=0;
    @Override
    public void init() {
        r.init_robot(this);
        composeTelemetry();



    }

    @Override

    public void loop() {
        double deflator = 0.7;
        //target = r.arm2.getCurrentPosition();
        r.arm2.setPower(gamepad1.right_stick_x* deflator);
        r.arm3.setPower(gamepad1.right_stick_x* deflator);

        r.arm1.setPower(gamepad1.left_stick_x*deflator);




        telemetry.addData("Power", r.arm2.getPower());
        telemetry.addData("Position", r.arm2.getCurrentPosition());
        telemetry.update();
    }
    void composeTelemetry(){
        //telemetry.addData("Touched: ", r.touch1.isPressed());
        //telemetry.addLine("Arm Selected: " + aim);
        //telemetry.update();


    }
}
