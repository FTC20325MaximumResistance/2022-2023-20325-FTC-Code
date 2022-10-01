package org.firstinspires.ftc.teamcode.util.threads.crane;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.Hard_Auto;
import org.firstinspires.ftc.teamcode.util.Hardware;

import java.util.Random;

public class RobotArm extends Thread{
    double arm_offset = 1.0;
    double arm1_l = 17;
    double arm2_l = 17;
    int ticks_per_degrees = 1;
    double height_offset = 6;
    OpMode opMode;
    DcMotor arm1;
    DcMotor arm2;
    double arm1_angle;
    double arm2_angle;
    int height;
    public boolean folded = true;
    Hardware r = new Hardware();
    Hard_Auto a = new Hard_Auto();
    String[] splash_texts = {"please feed your local programmer.", "check on Evan's mental state.", "donations are appreciated, but only accepted in the form of drugs.", "lets hope it did it right!", "you stopped reading these by this point, right?", "crane is ready to go.", "I need drugs.", "How charged is the controller? It's low... isn't it?", "na na na na na na na na Robot!", "Jesse, we need to code."};
    Random rand = new Random();

    public void reset_arm_motor(int arm_num){
        switch (arm_num){
            case 1:
                arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                break;
            case 2:
                arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                break;
            case 3:
                arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                break;
        }

    }

    public void reset_arm_encoders(){
        while (!r.touch2.isPressed()){
            arm2.setPower(0.2);
        }
        arm2.setPower(0.0);
        reset_arm_motor(2);
        while (!r.touch1.isPressed()){
            arm1.setPower(0.2);
        }
        arm1.setPower(0.0);
        reset_arm_motor(1);
        String current_splash = splash_texts[rand.nextInt((splash_texts.length -1))];
        opMode.telemetry.addLine("Arm is reset, " + current_splash);
        folded = true;
        arm1_angle = 0;
        arm2_angle = 0;

    }
    public RobotArm (OpMode opMode, DcMotor arm1, DcMotor arm2){
        this.opMode = opMode;
        this.arm1 = arm1;
        this.arm2 = arm2;
    }
    public void set_height(int height){
        this.height = height;
    }
    public void fold_arm(){
        arm1.setTargetPosition(0);
        arm2.setTargetPosition(0);
        if (arm2.getTargetPosition() > arm2.getCurrentPosition()){
            while (arm2.getTargetPosition() > arm2.getCurrentPosition()) {
                arm2.setPower(0.9);
            }
        }else if (arm2.getTargetPosition() < arm2.getCurrentPosition()){
            while (arm2.getTargetPosition() < arm2.getCurrentPosition()) {
                arm2.setPower(-0.9);
            }
        }
        arm2.setPower(0.0);
        if (arm1.getTargetPosition() > arm1.getCurrentPosition()){
            while (arm1.getTargetPosition() > arm1.getCurrentPosition()) {
                arm1.setPower(0.9);
            }
        }else if (arm1.getTargetPosition() < arm1.getCurrentPosition()){
            while (arm1.getTargetPosition() < arm1.getCurrentPosition()) {
                arm1.setPower(-0.9);
            }
        }
        arm1.setPower(0.0);


    }

    public void degree_move_arm(int arm_num, double power, double degrees){
        degrees = degrees % 360;
        degrees = degrees * (double) ticks_per_degrees;

        switch (arm_num){
            case 1:
                //degrees += arm1.getCurrentPosition() * Math.floorDiv(arm1.getCurrentPosition(), ticks_per_degrees*360);
                arm1.setTargetPosition((int) degrees);
                if (arm1.getTargetPosition() > arm1.getCurrentPosition()){
                    while (arm1.getTargetPosition() > arm1.getCurrentPosition()){
                        arm1.setPower(power);
                    }
                }else if (arm1.getTargetPosition() < arm1.getCurrentPosition()){
                    while (arm1.getTargetPosition() < arm1.getCurrentPosition()){
                        arm1.setPower(-power);
                    }
                }
                arm1.setPower(0.0);
                arm1_angle = (arm1.getCurrentPosition() / ticks_per_degrees) % 360;
                break;
            case 2:
                //degrees += arm2.getCurrentPosition() * Math.floorDiv(arm2.getCurrentPosition(), ticks_per_degrees*360);
                arm2.setTargetPosition((int) degrees);
                if (arm2.getTargetPosition() > arm2.getCurrentPosition()){
                    while (arm2.getTargetPosition() > arm2.getCurrentPosition()){
                        arm2.setPower(power);
                    }
                }else if (arm2.getTargetPosition() < arm2.getCurrentPosition()){
                    while (arm2.getTargetPosition() < arm2.getCurrentPosition()){
                        arm2.setPower(-power);
                    }
                }
                arm2.setPower(0.0);
                arm2_angle = (arm2.getCurrentPosition() / ticks_per_degrees) % 360;
                break;
        }
    }


    @Override
    public void run(){
        super.run();
        double ph = height_offset;
        switch(this.height){
            case(1):
                ph += 13.5;
                break;
            case 2:
                ph +=23.5;
                break;
            case 3:
                ph += 33.5;
                break;
        }
        double td = arm_offset;
        double hr = ph;
        double lr = td;
        double arm1_angle;
        double arm2_angle;
        boolean too_big;
        if (ph < arm1_l){
            arm1_angle = Math.asin(ph/arm1_l);
            ph = 0;
        }else{
            arm1_angle = 45;
            ph -= td * Math.sin(45);
        }
        if (lr - td * Math.cos(arm1_angle) < arm2_l){
            arm1_angle += 90;
            lr += td * Math.cos(arm1_angle);
            too_big = true;
        } else{lr -= td * Math.cos(arm1_angle);
        too_big = false;}
        if (hr > 0){
            arm2_angle = Math.asin(hr/arm2_l);
            lr -= lr * (Math.cos(arm2_angle));
        }else{
            arm2_angle = 0;
            lr -= arm2_l;
        }
        if (too_big){
            arm2_angle += arm1_angle;
        }else{arm2_angle += 270-arm1_angle;}
        degree_move_arm(1, 0.4, arm1_angle);
        degree_move_arm(2, 0.4, arm2_angle);
        r.waiter(3000);

        fold_arm();

    }
}
