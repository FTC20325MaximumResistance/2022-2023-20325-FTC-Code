package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.Hardware;
import org.firstinspires.ftc.teamcode.util.threads.crane.RobotArm;
import org.firstinspires.ftc.teamcode.util.threads.lights.panicLights;

@SuppressWarnings("unused")
@TeleOp(name = "Drive", group = "TeleOp")
public class manualDrive extends OpMode {
    Hardware r = new Hardware();
    RobotArm a;
    RobotArm a1;
    double r1a = 0;
    double r2a = 360;
    double deflator;
    int heightSelect = 1;
    panicLights l;
    @Override
    public void init() {
        r.init_robot(this);
        a = new RobotArm(this, r);
        a1 = new RobotArm(this, r);
        l = new panicLights(r, this);

    }

    @Override
    public void loop() {

        deflator = gamepad1.left_bumper && gamepad1.right_bumper ? 0.9: gamepad1.left_bumper ? 0.4 : 0.7;
        double deflator2 = gamepad2.left_bumper ? -0.7: -0.5;
        double deflator3 = gamepad2.left_bumper ? 0.9: 0.5;


            /*Message to future Evan: Don't mess with this
            Everything down here you will not understand
            Let Ben mess with this and get to learning how to use java
            stop scrolling down here
            I said stop!
            No Evan...
            Don't
            This is painful down here
            Stop
            Do you like black holes?
            You will somehow create one if you
            mess with this
            code that runs our mecanum drive wheels
            */
            //this first section creates the variables that will be used later

            //first we must translate the rectangular values of the joystick into polar coordinates;
            double y = -gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double angle = 0;

            if (y > 0 && x > 0)//quadrant 1
                angle = Math.atan(y / x);
            else {
                double angle1 = Math.toRadians(180) + Math.atan(y / x);
                if (y > 0 && x < 0)//quadrant 2
                    angle = angle1;
                else if (y < 0 && x < 0)//quadrant 3
                    angle = angle1;
                else if (y < 0 && x > 0)//quadrant 4
                    angle = Math.toRadians(360) + Math.atan(y / x);
            }

            if (y == 0 && x > 1) {
                angle = 0;
            }
            if (y > 0 && x == 0) {
                angle = Math.PI / 2;
            }
            if (y == 0 && x < 0) {
                angle = Math.PI;
            }
            if (y < 0 && x == 0) {
                angle = 3 * Math.PI / 2;
            }

            double velocity = Math.sqrt(Math.pow(gamepad1.left_stick_y, 2) + Math.pow(gamepad1.left_stick_x, 2));
            double rotation = gamepad1.right_stick_x;

            //equations taking the polar coordinates and turing them into motor powers
            double v1 = velocity * Math.cos(angle + (Math.PI / 4));
            double v2 = velocity * Math.sin(angle + (Math.PI / 4));
            double power1 = v1 + rotation;
            double power2 = v2 + rotation;
            double power3 = v2 - rotation;
            double power4 = v1 -  rotation;

            r.frontLeft.setPower(power1 * deflator);
            r.frontRight.setPower(power2 * deflator);
            r.backLeft.setPower(power3 * deflator);
            r.backRight.setPower(power4 * deflator);

            //l.updateState();


        if (r.touch1.isPressed() && gamepad2.left_stick_x*deflator3 > 0.0){
            r.arm1.setPower(0);
        }else{
            r.arm1.setPower(gamepad2.left_stick_x*deflator3);
        }
        r.arm2.setPower(gamepad2.right_stick_x * deflator2);
        r.arm3.setPower(gamepad2.right_stick_x * deflator2);




        //Honestly not sure why this works, but I'm not going to try to understand
        int thing = gamepad2.right_trigger > 0 ? 360: gamepad2.left_trigger > 0 ? -360: 0;
        r1a += thing;
        if (r1a > 1){
            r1a = 1;
        }else if (r1a < 0){
            r1a = 0;
        }
        if (gamepad2.b){
            r.arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.arm3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        if (gamepad2.a){
            r.arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.arm1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        r.spin.setPosition(r1a);
        composeTelemetry();



    }

    public void composeTelemetry(){
        String height = "";
        switch (heightSelect){
            case 1:
                height = "Short";
                break;
            case 2:
                height = "Medium";
                break;
            case 3:
                height = "Tall";
                break;

        }
        telemetry.addData("Auto-Height", height);
        telemetry.addData("Precision Mode", deflator < 0.7);
        telemetry.addData("Speed Mode", deflator >0.9);
        String thing = r2a == 360 ? "Closed" : "Open";
        telemetry.addData("Claw", thing);
        telemetry.addData("Arm 1 Position", r.arm1.getCurrentPosition());
        telemetry.addData("Arm 2 Position", r.arm2.getCurrentPosition());
        telemetry.addData("Movement State", l.CurrentState);
        telemetry.update();
    }
}
