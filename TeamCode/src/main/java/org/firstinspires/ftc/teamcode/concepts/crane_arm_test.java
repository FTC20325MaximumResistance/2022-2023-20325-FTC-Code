package org.firstinspires.ftc.teamcode.concepts;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.Hardware;
@TeleOp(name = "crane_arm_test", group = "TeleOp")
public class crane_arm_test extends OpMode {

    Hardware r = new  Hardware();
    boolean arm1select = true;
    String aim = "Arm 1";
    boolean blank = true;
    @Override
    public void init() {
        r.init_robot(this);
        composeTelemetry();

    }

    @Override

    public void loop() {
        //Test 1
        //r.arm1.setTargetPosition(0);
        //r.arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //r.arm1.setPower(0.25);


        //Test 2
        /*
        double deflator = 0.9;
        if (gamepad1.b){arm1select = !arm1select;}
        if (arm1select){
            aim = "Arm 1";
            r.arm1.setPower(gamepad1.left_stick_x * deflator);
            r.arm2.setPower(0.0);

        }else{
            aim = "Arm 2";
            r.arm2.setPower(gamepad1.left_stick_x*deflator);
            r.arm1.setPower(0.0);

        }

         */

        //Test 3
        boolean done = false;


        if (blank){
            r.arm1.setPower(0.05);
            blank = false;
        }
        if (r.touch1.isPressed()){
            r.arm1.setPower(0);
        }
        telemetry.update();
        composeTelemetry();

    }
    void composeTelemetry(){
        //telemetry.addData("Touched: ", r.touch1.isPressed());
        //telemetry.addLine("Arm Selected: " + aim);
        //telemetry.update();


    }
}