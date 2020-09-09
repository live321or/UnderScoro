package com.samoylov.gameproject.character.heroes;

public class HeroStat {
    String name;
    double count;

    public HeroStat(String name, double count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}

