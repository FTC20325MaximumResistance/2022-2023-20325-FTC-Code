package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@SuppressWarnings("unused")
public class Hardware {
    OpMode opMode;
    public DcMotor frontLeft, frontRight, backLeft, backRight;
    public DcMotor[] drive;
    public TouchSensor touch1, touch2;
    public Servo claw;
    public DcMotor arm1, arm2;

    public void init_robot(OpMode opMode){
        this.opMode = opMode;
        initHardware();
    }
    public void initHardware(){
        //Add motors and shit here
        try {
            frontLeft = opMode.hardwareMap.dcMotor.get("FLM");
            frontRight = opMode.hardwareMap.dcMotor.get("FRM");
            backLeft = opMode.hardwareMap.dcMotor.get("BLM");
            backRight = opMode.hardwareMap.dcMotor.get("BRM");
            for (DcMotor motor : drive) {
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
        }catch (Exception e){opMode.telemetry.addLine("Drive Motor(s) were not found in config.");}


        try {
            touch1 = opMode.hardwareMap.touchSensor.get("touch1");
            touch2 = opMode.hardwareMap.touchSensor.get("touch2");
        }catch (Exception e){opMode.telemetry.addLine("Touch Sensor(s) were not found in config.");}


        try {
            claw = opMode.hardwareMap.servo.get("claw");
        } catch(Exception e){opMode.telemetry.addLine("Servo(s) were not found in config.");}
        arm1 = opMode.hardwareMap.dcMotor.get("arm1");
        //arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        try {

            arm2 = opMode.hardwareMap.dcMotor.get("arm2");
        } catch(Exception e){opMode.telemetry.addLine("Crane Arm Motor(s) were not found in config");}
        opMode.telemetry.update();
    }
    public void waiter(int time){
        ElapsedTime Time = new ElapsedTime();
        Time.reset();
        while(true){if (Time.milliseconds() > time){break;}}
    }
    public void initAuto(){
        waiter(500);
    }
    public void setDriverMotorMode(DcMotor.RunMode mode){
        for(DcMotor dcMotor: drive) dcMotor.setMode(mode);
    }

}

