package org.firstinspires.ftc.teamcode.util.threads.crane;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.Hardware;

import java.util.Random;

public class RobotArm extends Thread{
    double arm_offset = 2.0;
    double arm1_l = 14.5;
    double arm2_l = 20;
    double ticks_per_degrees1 = 53.33;
    double ticks_per_degrees2 = 1.94;
    double height_offset = 4;
    OpMode opMode;
    int target;
    boolean degree;

    double arm1_angle;
    double arm2_angle;
    int height;
    int armNum;
    boolean full;
    public boolean folded = true;
    Hardware r;
    String[] splash_texts = {"please feed your local programmer.", "check on Evan's mental state.", "donations are appreciated, but only accepted in the form of drugs.", "lets hope it did it right!", "you stopped reading these by this point, right?", "crane is ready to go.", "I need drugs.", "how charged is the controller? It's low... isn't it?", "na na na na na na na na Robot!", "Jesse, we need to code."};
    Random rand = new Random();







    public RobotArm (OpMode opMode,Hardware r){
        this.opMode = opMode;
        this.r = r;
    }
    public void set_height(int height){
        this.height = height;
    }
    public void fold_arm(){
        target = 154;

        start();

    }

    public void moveArm(int armNum, int degrees, boolean degree, boolean full){
        this.armNum = armNum;
        target = degrees;
        this.degree = degree;
        this.full = full;
        start();
    }

    public void move_here(int arm1, int arm2, boolean wait){
        target = arm1;
        armNum = 1;
        start();
        r.waiter(5000);
        target = arm2;
        armNum = 2;
        start();
        while (this.isAlive() && wait);
    }

    public void setTicks(int position, double power, boolean full){
        boolean done = false;
        switch (armNum){
            case 1:
                if (full){
                    while(!r.touch1.isPressed()){
                        r.arm1.setPower(power);
                    }
                    r.arm1.setPower(0);
                }else {
                    r.arm1.setTargetPosition(position);
                    r.arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    while (!done) {
                        r.arm1.setPower(power);
                        done = r.getTolerance(r.arm1.getTargetPosition(), r.arm1.getCurrentPosition(), 10);
                    }
                    r.arm1.setPower(0);
                }
                break;
            case 2:

                while (!done){
                    r.arm2.setTargetPosition(position);
                    r.arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    r.arm3.setTargetPosition(position);
                    r.arm3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    r.arm2.setPower(power);
                    r.arm3.setPower(power);
                    done = r.getTolerance(r.arm2.getTargetPosition(), r.arm2.getCurrentPosition(),3);
                }
                r.arm2.setPower(0);
                r.arm3.setPower(0);
                break;
        }
    }


    public void degree_move_arm(int arm_num, double power, double degrees){
        degrees = degrees % 360;
        boolean done = false;
        switch (arm_num){
            case 1:
                degrees = degrees * ticks_per_degrees1;
                r.arm1.setTargetPosition((int) degrees);
                r.arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                r.arm1.setPower(power);

                while (!done){done = r.getTolerance(r.arm1.getCurrentPosition(), r.arm1.getTargetPosition(), 10);}
                r.arm1.setPower(0.0);
                arm1_angle = (r.arm1.getCurrentPosition() / ticks_per_degrees1) % 360;
                break;
            case 2:
                degrees -= 19;
                while (!done){

                    r.arm2.setTargetPosition((int) ((int) (degrees+(90-(r.arm1.getCurrentPosition()/ticks_per_degrees1))) * ticks_per_degrees2));
                    r.arm3.setTargetPosition((int) ((int) (degrees+(90-(r.arm1.getCurrentPosition()/ticks_per_degrees1))) * ticks_per_degrees2));
                    r.arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    r.arm3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    r.arm3.setPower(power);
                    r.arm2.setPower(power);
                    done = r.getTolerance(r.arm2.getCurrentPosition(), r.arm2.getTargetPosition(), 3);
                }
                r.arm3.setPower(0.0);
                r.arm2.setPower(0.0);
                arm2_angle = (r.arm2.getCurrentPosition() / ticks_per_degrees2) % 360;
                break;
        }
    }


    @Override
    public void run(){
        super.run();
        if (degree) {
            switch (armNum) {
                case 1:
                    degree_move_arm(1, 0.9, target);
                    break;
                case 2:
                    degree_move_arm(2, 0.5, target);
                    break;
            }
        }else{
            switch (armNum){
                case 1:
                    setTicks(target,0.9, full);
                    break;
                case 2:
                    setTicks(target, 0.5, full);
                    break;
            }
        }
    }

}
