package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
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
    public Servo claw, spin;
    public DcMotor arm1, arm2;
    public ModernRoboticsI2cRangeSensor distanceSensor;

    public void init_robot(OpMode opMode){
        this.opMode = opMode;
        initHardware();
    }
    public void initHardware(){
        try {distanceSensor = opMode.hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range");}
        catch(Exception e){opMode.telemetry.addLine("Range sensor was not found in config");}
        try {
            frontLeft = opMode.hardwareMap.dcMotor.get("FLM");
            frontRight = opMode.hardwareMap.dcMotor.get("FRM");
            backLeft = opMode.hardwareMap.dcMotor.get("BLM");
            backRight = opMode.hardwareMap.dcMotor.get("BRM");
            for (DcMotor motor : drive) {
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
        }catch (Exception e){opMode.telemetry.addLine("Drive Motor(s) were not found in config.");}
        try{touch1 = opMode.hardwareMap.touchSensor.get("touch1");} catch (Exception e){opMode.telemetry.addLine("Touch Sensor1 were not found in config.");}


        try {

            touch2 = opMode.hardwareMap.touchSensor.get("touch2");
        }catch (Exception e){opMode.telemetry.addLine("Touch Sensor2 were not found in config.");}


        try {
            claw = opMode.hardwareMap.servo.get("claw");
            spin = opMode.hardwareMap.servo.get("spin");
        } catch(Exception e){opMode.telemetry.addLine("Servo(s) were not found in config.");}
        try {
            arm1 = opMode.hardwareMap.dcMotor.get("arm1");
            arm1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } catch (Exception e){opMode.telemetry.addLine("Crane Arm Motor1 were not found in config");}
        try {

            arm2 = opMode.hardwareMap.dcMotor.get("arm2");
        } catch(Exception e){opMode.telemetry.addLine("Crane Arm Motor2 were not found in config");}
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

