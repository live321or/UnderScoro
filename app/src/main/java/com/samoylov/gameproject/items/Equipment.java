package com.samoylov.gameproject.items;

import java.util.ArrayList;

public class Equipment {
    //Уникальный id, тип предмета, характеристики
    //Характеристки:
    private int mImageResource;
    private double Id;
    private double Str = 0;
    private double Agi = 0;
    private double Int = 0;
    private double Luc = 0;
    String name;
    private ArrayList<Double> stat = new ArrayList<>();
    private double DropChance;

    public Equipment(String name, int mImageResource, double Id, double Str, double Agi, double Int,
                     double Luc, double DropChance) {
        this.Id = Id;
        this.Str = Str;
        this.Agi = Agi;
        this.Int = Int;
        this.Luc = Luc;
        this.mImageResource = mImageResource;
        this.name = name;
        this.DropChance = DropChance;
        stat.add(new Double(Str));
        stat.add(new Double(Agi));
        stat.add(new Double(Int));
        stat.add(new Double(Luc));
    }

    //Оуржие:
    // Тип оружия(Одноручный-ближний).
    // Тип Урона(Физ)+Базовые статы+Подсаты(позже).
    // Требование для использования(позже)
    public static class Weapon extends Equipment {
        double Dmg = 0;
        private String tag = "Weapon";

        public Weapon(String name, int mImageResource, double Id, double Str, double Agi,
                      double Int, double Luc, double DropChance, double dmg) {
            super(name, mImageResource, Id, Str, Agi, Int, Luc, DropChance);
            this.Dmg = dmg;
        }

        public String getTag() {
            return tag;
        }

        public double getDmg() {
            return Dmg;
        }
    }

    //Броня:
    // Тип брони(Голова,тело,ноги,щит).
    // Тип защиты(Физ)+Базовые статы+Подсаты(позже).
    // Требование для использования(позже)
    public static class Armour extends Equipment {
        String typeArmour;
        private String tag = "Armour";
        double Armor = 0;

        public Armour(String name, int mImageResource, double Id, double Str, double Agi,
                      double Int, double Luc, double DropChance, String typeArmour, double armor) {
            super(name, mImageResource, Id, Str, Agi, Int, Luc, DropChance);
            this.typeArmour = typeArmour;
            this.Armor = armor;
        }

        public String getTag() {
            return tag;
        }

        public double getArmor() {
            return Armor;
        }

        public String getTypeArmour() {
            return typeArmour;
        }
    }

    //
    //
    //
    //
    public double getId() {
        return Id;
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

    public int getmImageResource() {
        return mImageResource;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Double> getStat() {
        return stat;
    }

    public double getDropChance() {
        return DropChance;
    }
}
