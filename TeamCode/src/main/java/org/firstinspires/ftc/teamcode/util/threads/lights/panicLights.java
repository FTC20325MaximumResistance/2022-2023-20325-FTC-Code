package org.firstinspires.ftc.teamcode.util.threads.lights;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.Hardware;

public class panicLights{
    public movementState CurrentState;
    Hardware r;
    OpMode opMode;
    lightThread l;

    public void updateState(){
        movementState lastState = CurrentState;
        for (int i = 0; i == 0; i++) {
            boolean test = true;


            if (opMode.gamepad1.right_stick_x > 0) {CurrentState = movementState.RIGHTTURN;break;}
            if (opMode.gamepad1.right_stick_x < 0) {CurrentState = movementState.LEFTTURN;break;}
            if (opMode.gamepad1.left_stick_y < 0) {CurrentState = movementState.FORWARD;break;}
            if (opMode.gamepad1.left_stick_y > 0) {CurrentState = movementState.REVERSE;break;}
            CurrentState = movementState.STOPPED;

        }

        if (CurrentState != lastState && l.runtime > 250) {

            if (l.isAlive()) {
                l.interrupt();
            }
            l.updateLights(CurrentState);
        }

    }

    public panicLights(Hardware r, OpMode opMode){
        this.opMode = opMode;
        this.r = r;
        l = new lightThread(this.opMode, this.r);
    }




    public enum movementState{
        STOPPED,
        RIGHTTURN,
        LEFTTURN,
        REVERSE,
        FORWARD
    }
}
