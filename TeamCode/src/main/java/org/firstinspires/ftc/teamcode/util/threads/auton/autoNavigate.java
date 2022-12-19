package org.firstinspires.ftc.teamcode.util.threads.auton;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.Hard_Auto;
import org.firstinspires.ftc.teamcode.util.dictionary;
import org.firstinspires.ftc.teamcode.util.threads.crane.RobotArm;

import java.util.HashMap;

public class autoNavigate extends Thread{
    OpMode opMode;
    int x;
    int y;
    Hard_Auto r;
    Hard_Auto.direction dir;
    Hard_Auto.direction dir2;
    dictionary heights = new dictionary(new String[]{"Ground", "Short", "Middle", "Tall"}, new double[]{0, 13.5, 23.5, 33.5});
    HashMap<String, double[]> arm_pos = new HashMap<>();
    HashMap<String, boolean[]> fulls = new HashMap<>();
    RobotArm a1;
    RobotArm a2;

    int target_x = 0;
    int target_y = 0;
    double power;
    String[][] poles = new String[6][6];

    public autoNavigate(OpMode opMode, Hard_Auto r, int x, int y){
        this.opMode = opMode;
        this.r = r;
        this.x = x;
        this.y = y;
        a1 = new RobotArm(this.opMode, this.r);
        a2 = new RobotArm(this.opMode, this.r);
        fulls.put("Ground", new boolean[]{true});
        fulls.put("Short", new boolean[]{true});
        fulls.put("Middle", new boolean[]{false});
        fulls.put("Tall", new boolean[]{false});
        arm_pos.put("Ground", new double[]{0,0,7.5});
        arm_pos.put("Short",new double[]{0,-33,0});
        arm_pos.put("Middle",new double[]{6541,-69,0});
        arm_pos.put("Tall",new double[]{0,0,0});
        {
            poles[1][1] = "Ground";
            poles[3][1] = "Ground";
            poles[5][1] = "Ground";
            poles[1][3] = "Ground";
            poles[3][3] = "Ground";
            poles[5][3] = "Ground";
            poles[1][5] = "Ground";
            poles[3][5] = "Ground";
            poles[5][5] = "Ground";
            poles[2][2] = "Middle";
            poles[4][2] = "Middle";
            poles[2][4] = "Middle";
            poles[4][4] = "Middle";
            poles[2][1] = "Short";
            poles[4][1] = "Short";
            poles[2][5] = "Short";
            poles[4][5] = "Short";
            poles[1][2] = "Short";
            poles[5][2] = "Short";
            poles[1][4] = "Short";
            poles[5][4] = "Short";
            poles[3][2] = "Tall";
            poles[2][3] = "Tall";
            poles[4][3] = "Tall";
            poles[3][4] = "Tall";
        }


    }

    public void moveArm(int arm1, int arm2, boolean wait, boolean degree, boolean full){
        a1.moveArm(1, arm1, degree, full);
        a2.moveArm(2, arm2, degree, full);
        opMode.telemetry.addData("A2 done", a2.isAlive());
        opMode.telemetry.update();
        while (wait && a1.isAlive() && a2.isAlive());
    }
    public void MoveToPosition(int x, int y, double power, boolean wait){
        target_x = x - this.x;
        target_y = y - this.y;
        this.x = x;
        this.y = y;
        if (target_x >= 0){dir= Hard_Auto.direction.FORWARD;}
        else{dir= Hard_Auto.direction.BACKWARD;}
        if (target_y >= 0){dir2= Hard_Auto.direction.LEFT;}
        else{dir2= Hard_Auto.direction.RIGHT;}
        this.power = power;
        start();
        //↓ Change this if you are planning to run multiple threads at once
        if (wait){while (this.isAlive());}

    }

    @Override
    public void run(){
        super.run();
        if (target_y != 0)
            r.distance_move(Math.abs(this.target_y*24), this.dir2, power);
        r.setToStill();
        if (target_x != 0)
            r.distance_move(Math.abs(this.target_x *24), this.dir, power);
        r.setToStill();

        opMode.telemetry.addData("Target Position", target_x);
        opMode.telemetry.update();
        r.waiter(500);
    }

    public void placeCone(poleDirection dir){
        int rotation = 0;
        RobotArm a = new RobotArm(opMode, r);
        Hard_Auto.direction dir2 = Hard_Auto.direction.LEFT;
        String height = null;
        try {
            switch (dir) {
                case NW:
                    rotation = 135;
                    height = poles[x - 1][y];
                    break;
                case NE:
                    rotation = 45;
                    height = poles[x][y];
                    break;
                case SE:
                    rotation = 45;
                    height = poles[x][y - 1];
                    dir2 = Hard_Auto.direction.RIGHT;
                    break;
                case SW:
                    rotation = 135;
                    height = poles[x - 1][y - 1];
                    dir2 = Hard_Auto.direction.RIGHT;
                    break;
            }
        }catch(Exception e){
            opMode.telemetry.addData("Error", e);
            opMode.telemetry.update();
            return;
        }
        if (height == null){
            opMode.telemetry.addLine("Pole Not Found");
            opMode.telemetry.update();
            return;
        }
        r.degree_rotate(rotation, dir2, 0.5);
        opMode.telemetry.addData("Pole Height", height);
        opMode.telemetry.addData("Pole Height In Inches", heights.getValue(height));
        opMode.telemetry.update();
        double[] pos = arm_pos.get(height);

        dir2 = dir2 == Hard_Auto.direction.RIGHT ? Hard_Auto.direction.LEFT: Hard_Auto.direction.RIGHT;

        moveArm((int) pos[0], (int) pos[1], true, false, fulls.get(height)[0]);
        r.distance_move(pos[2], Hard_Auto.direction.FORWARD, 0.6);
        r.spin.setPosition(1);
        r.distance_move(pos[2], Hard_Auto.direction.BACKWARD, 0.6);
        r.degree_rotate(rotation, dir2, 0.5);



    }

    public enum poleDirection{
        NW,
        NE,
        SW,
        SE
    }
}
