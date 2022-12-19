package org.firstinspires.ftc.teamcode.util.threads.lights;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Hardware;

import java.util.concurrent.TimeUnit;

public class lightThread extends Thread{
    Hardware r;
    OpMode opMode;
    panicLights.movementState state;
    double runtime = 99999;


    @Override
    public void run(){
        ElapsedTime time = new ElapsedTime();
        super.run();
        time.reset();
        ElapsedTime Time = new ElapsedTime();
        switch (state){
            case FORWARD:
                r.backleft.setState(true);
                r.backright.setState(true);
                break;
            case STOPPED:
                r.backleft.setState(false);
                r.backright.setState(false);
                break;
            case LEFTTURN:
                r.backright.setState(false);
                while (!this.isInterrupted()){
                    Time.reset();
                    while (!this.isInterrupted() && Time.milliseconds() <834){runtime = time.time(TimeUnit.MILLISECONDS);}
                    r.backleft.setState(!r.backleft.getState());
                    runtime = time.time(TimeUnit.MILLISECONDS);
                }
                break;
            case RIGHTTURN:
                r.backleft.setState(false);

                while (!this.isInterrupted()){
                    Time.reset();
                    while (!this.isInterrupted() && Time.milliseconds() <834){runtime = time.time(TimeUnit.MILLISECONDS);}
                    r.backright.setState(!r.backright.getState());

                }
                break;
            case REVERSE:
                r.backleft.setState(true);
                r.backright.setState(true);

                while (!this.isInterrupted()){
                    Time.reset();
                    while (!this.isInterrupted() && Time.milliseconds() <834){runtime = time.time(TimeUnit.MILLISECONDS);}
                    r.backright.setState(!r.backright.getState());
                    r.backleft.setState(!r.backleft.getState());
                    runtime = time.time(TimeUnit.MILLISECONDS);
                }
                break;
        }
        runtime = 9999;
    }

    public void updateLights(panicLights.movementState state){
        this.state = state;
        start();
    }

    public lightThread(OpMode opMode, Hardware r){
        this.r = r;
        this.opMode = opMode;
    }


}
