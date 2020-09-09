package com.samoylov.gameproject.authorization;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("response")
    private String Response;

    @SerializedName("name")
    private String Name;
    @SerializedName("location")
    private String location;

    @SerializedName("Lvl")
    private double Lvl;
    @SerializedName("point")
    private double point;
    @SerializedName("EXP")
    private double EXP;

    @SerializedName("hp")
    private double hp;

    @SerializedName("Str")
    private double Str;
    @SerializedName("Agi")
    private double Agi;
    @SerializedName("Int")
    private double Int;
    @SerializedName("Luc")
    private double Luc;

    public String getLocation() {
        return location;
    }

    public double getLvl() {
        return Lvl;
    }

    public double getPoint() {
        return point;
    }

    public double getEXP() {
        return EXP;
    }

    public double getHp() {
        return hp;
    }

    public double getStr() {
        return Str;
    }

    public double getAgi() {
        return Agi;
    }

    public double getInt() {
        return Int;
    }

    public double getLuc() {
        return Luc;
    }




    public String getResponse() {
        return Response;
    }

    public String getName() {
        return Name;
    }
}
