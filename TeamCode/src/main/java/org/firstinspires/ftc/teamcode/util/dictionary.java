package org.firstinspires.ftc.teamcode.util;

import java.util.Objects;
@SuppressWarnings("unused")

public class dictionary {
    String[] keys;
    double[] values;

    public dictionary(String[] keys, double[] values){
        this.keys = keys;
        this.values = values;
    }

    public int length(){
        return keys.length;
    }

    public String[] getKeys(){
        return keys;
    }

    public double[] getValues(){
        return values;
    }

    public void append(String key, double value){
        String[] keys = new String[this.keys.length + 1];
        System.arraycopy(this.keys, 0, keys, 0, this.keys.length);
        keys[keys.length-1] = key;
        this.keys = keys;
        double[] values = new double[this.values.length + 1];
        System.arraycopy(this.values, 0, values, 0, this.values.length);
        values[values.length-1] = value;
        this.values = values;
    }

    public void remove(String key){
        String[] fin = new String[this.keys.length - 1];
        int index = getIndex(key);
        int x = 0;
        for (String i: this.keys){
            if (!Objects.equals(key, i)){
                fin[x] = i;
            }
            x += 1;
        }
        this.keys = fin;
        double[] values = new double[this.values.length -1];
        x = 0;
        boolean found = false;
        for (double i: this.values){
            if (getIndex(i) == index && !found){
                found = true;
            }else{values[x]=i;}
            x+=1;
        }
        this.values = values;

    }

    public void changeValue(String key, double newValue){
        int index = getIndex(key);
        values[index] = newValue;
    }

    public double getValue(String key){
        int x = getIndex(key);
        return values[x];
    }

    public String getValue(double key){
        int x = getIndex(key);
        return keys[x];
    }

    private int getIndex(double value){
        int index = 0;
        for (double i: values){
            if (i == value){
                break;
            }
            index += 1;
        }
        return index;
    }

    private int getIndex(String value){
        int index = 0;
        for (String i: keys){
            if (Objects.equals(i, value)){
                break;
            }
            index += 1;
        }
        return index;
    }

}
