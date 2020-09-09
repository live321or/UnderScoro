package com.samoylov.gameproject.character.mobs;

import com.samoylov.gameproject.Data;
import com.samoylov.gameproject.Person;
import com.samoylov.gameproject.character.heroes.HeroStat;
import com.samoylov.gameproject.items.Equipment;
import com.samoylov.gameproject.locations.LocationSettings;

import java.util.ArrayList;

public class Mob implements Person {
    //Класс который описывает моба, с которым игрок может сражаться
    private String name;
    boolean dead = false;
    private int id = Data.bdMob.size();
    private double Str, Agi, Int, Luc, Lvl, hp, hp_now, EXP, Armor;
    private String location;
    public ArrayList<Equipment> dropItems = new ArrayList<>();
    public ArrayList<HeroStat> mobStats = new ArrayList<HeroStat>();
    private int positionOnBattle = 7;
    private int x, y;

    // Конструктор
    public Mob(String name, double hp_now, double Str, double Agi, double Int, double Luc,
               double Lvl, String location, LocationSettings location1, double hp, double EXP, double Armor, ArrayList<Equipment> dropItems) {
        this.name = name;
        this.hp_now = hp_now;
        this.Str = Str;
        this.Agi = Agi;
        this.Int = Int;
        this.Luc = Luc;
        this.Lvl = Lvl;
        this.location = location;
        this.hp = hp;
        this.EXP = EXP;
        this.Armor = Armor;
        this.dropItems = dropItems;
        addStat(mobStats);
        this.location1 = location1;
    }

    void addStat(ArrayList<HeroStat> mobStats) {
        mobStats.add(new HeroStat("Сила", Str));
        mobStats.add(new HeroStat("Ловкость", Agi));
        mobStats.add(new HeroStat("Интеллект", Int));
        mobStats.add(new HeroStat("Удача", Luc));
    }

    // добавление выподающего предмета
    public void addDropItem(Equipment dropItem) {
        this.dropItems.add(0, dropItem);
    }

    @Override
    public void setDropChance(double DropChance) {

    }

    //функция дропа предметов
    public Equipment getDropItem() {
        if (!dropItems.isEmpty() && Math.random() * 100 <= dropItems.get(0).getDropChance()) {
            return dropItems.get(0);
        } else return null;
    }
//    шанс выподения предмета? в мобе или в предмете?

//    агрессивность
//    тип поведения(Груповой/одиночка)

//геторы и сеторы

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {

    }

    public int getId() {
        return id;
    }

    @Override
    public double getLvl() {
        return Lvl;
    }

    @Override
    public void setLvl(double Lvl) {

    }

    @Override
    public double getStr() {
        return Str;
    }

    @Override
    public void setStr(double Str) {

    }

    @Override
    public double getEXP() {
        return EXP;
    }

    @Override
    public void setEXP(double EXP) {

    }

    @Override
    public double getHp() {
        return hp;
    }

    @Override
    public void setHp(double Hp) {
        hp = Hp;
    }

    @Override
    public double getHp_now() {
        return Math.floor(hp_now);
    }

    @Override
    public void setHp_now(double Hp_now) {
        hp_now = Hp_now;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public double getArmor() {
        return ((100 * Armor * Armor) / (Armor * Armor + 80 * Armor));//броня хранится в числах, возвращается в %;
    }

    @Override
    public double getAtribut(int num) {
        return 0;
    }

    @Override
    public void setAtribut(double atribut, double atribut1, double atribut2, double atribut3) {

    }

    @Override
    public void setPosition(int position) {

    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public void setPositionBattle(int position) {
        this.positionOnBattle = position;
    }

    @Override
    public int getPositionBattle() {
        return positionOnBattle;
    }


    @Override
    public void setArmor(double Armor) {
        this.Armor = Armor;
    }

    //Поменял 10.05
    @Override
    public double getDmg() {
        return Math.floor((1 + Lvl / 10) * (5 + (Str / 4) + (Str / 10) * 2));//крит не прошел, расчет урон без крита
    }

    @Override
    public void setDmg(double Dmg) {

    }

    @Override
    public double getHr() {
        return 0;
    }

    @Override
    public void setHr(double Hr) {

    }

    @Override
    public double getAgi() {
        return 0;
    }

    @Override
    public void setAgi(double Agi) {

    }

    @Override
    public double getDdg() {
        return 0;
    }

    @Override
    public void setDdg(double Ddg) {

    }

    @Override
    public double getAcc() {
        return 80;
//        return (50 + (40 * Agi * Agi) / (Agi * Agi + 40 * Agi) / 10);//50% - базовая меткость
    }

    @Override
    public void setAcc(double Acc) {

    }

    @Override
    public double getCritPower() {
        return 1.5;
//        return (1.5 + (190 * Agi * Agi) / (Agi * Agi + 100 * Agi)/100);//110% - базовый крит у всех (пока 150%, для тестов)
    }

    @Override
    public void setCritPower(double CritPower) {

    }

    @Override
    public double getInt() {
        return 0;
    }

    @Override
    public void setInt(double Int) {

    }

    @Override
    public double getSkillsPower() {
        return 0;
    }

    @Override
    public void setSkillsPower(double SkillsPower) {

    }

    @Override
    public double getMp() {
        return 0;
    }

    @Override
    public void setMp(double Mp) {

    }

    @Override
    public double getMr() {
        return 0;
    }

    @Override
    public void setMr(double Mr) {

    }

    @Override
    public double getLuc() {
        return 0;
    }

    @Override
    public void setLuc(double Luc) {

    }

    @Override
    public double getCritChance() {
        return 50;
//        return (50 + (90 * Luc * Luc) / (Luc * Luc + 35 * Luc) / 10); //10% - базовый шанс крита (пока 50%, для тестов)
    }

    @Override
    public void setCritChance(double CritChance) {

    }

    @Override
    public double getDropChance() {
        return 0;
    }

    @Override
    public double getSkillChance() {
        return 0;
    }

    @Override
    public void setSkillChance(double SkillChance) {

    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    private LocationSettings location1;

    @Override
    public LocationSettings getLocation1() {
        return location1;
    }

    @Override
    public void setLocation1(LocationSettings location) {
        this.location1 = location;
    }


    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    boolean flag = false;

    public boolean getFlag(int radius, int pos) {
        int[] rad = {-6, -5, -4, -1, 1, 4, 5, 6};


        if (pos % 10 == 0 || pos % 10 == 5) {
            rad = new int[]{-5, -4, 1, 5, 6};
        }

        if (pos % 10 == 4 || pos % 10 == 9) {
            rad = new int[]{-5, -6, -1, 5, 4};
        }

        for (int i = 0; i < rad.length; i++) {
            if (radius - pos == rad[i]) {
                this.flag = true;
                i = rad.length;
            } else this.flag = false;
        }
        return flag;
    }
    //    @Override
//    public String getName2() {
//        return name;
//    }
//
//    @Override
//    public double getAcc2() {
//        return 80;
//    }
//
//    @Override
//    public double getCritChance2() {
//        return 50;
//    }
//
//    @Override
//    public double getCritPower2() {
//        return 1.5;
//    }
//
//    @Override
//    public double getDmg2() {
//        return Math.floor((1 + Lvl / 10) * (5 + (Str / 4) + (Str / 10) * 2));
//    }
//
//    @Override
//    public double getHp_now2() {
//        return hp_now;
//    }
//
//    @Override
//    public void setHp_now2(double Hp_now) {
//        hp_now = Hp_now;
//    }
}

