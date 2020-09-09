package com.samoylov.gameproject.character.heroes;

import com.samoylov.gameproject.Data;
import com.samoylov.gameproject.Person;
import com.samoylov.gameproject.items.Equipment;
import com.samoylov.gameproject.locations.LocationSettings;

import java.util.ArrayList;

public class Hero  implements Person {
    HeroStat heroStat;
    private int position;
    private int positionBattle=27;
    private String name;
    private String location = "Москва";
    private int x,y;

    private double Lvl = 1;
    private double point =9;
    private double EXP = 0;

    public boolean isGroup() {
        return Group;
    }

    public void setGroup(boolean group) {
        Group = group;
    }

    public boolean isGroupLider() {
        return GroupLider;
    }

    public void setGroupLider(boolean groupLider) {
        GroupLider = groupLider;
    }

    private boolean Group=false;
    private boolean GroupLider=false;

    private double Str ;
    private double Agi ;
    private double Int ;
    private double Luc ;

    private double hp;
    private double hp_now;

    private double Dmg = 5;
    private double Hr = 0.4;

    private double Ddg = 1;
    private double Acc = 0.6;
    private double CritPower = 1.1;

    private double SkillsPower = 1;
    private double Mp = 10;
    private double Mr = 0.2;

    private double CritChance = 10;
    private double DropChance;
    private double SkillChance = 60;


    private double Armor;
    private int num;

    public Hero(String name, double hp_now, double Str, double Agi, double Int, double Luc,
                double Lvl, String location, double hp, double Armor) {
        this.name=name;
        this.hp_now=hp_now;
        this.Str=Str;
        this.Agi=Agi;
        this.Int=Int;
        this.Luc=Luc;
        this.Lvl = Lvl;
        this.location = location;
        this.hp = hp;
        this.Armor = Armor;

        addStat(heroStats);
        addStat(newHeroStats);
    }


    public ArrayList<HeroStat> heroStats = new ArrayList<HeroStat>();
    public ArrayList<HeroStat> newHeroStats = new ArrayList<HeroStat>();

    public ArrayList<HeroStat> getNewHeroStats() {
        return newHeroStats;
    }

    public void setNewHeroStats(ArrayList<HeroStat> newHeroStats) {
        this.newHeroStats = newHeroStats;
    }

    public ArrayList<HeroStat> getHeroStats() {
        return heroStats;
    }

    public void setHeroStats(ArrayList<HeroStat> heroStats) {
        this.heroStats = heroStats;
    }

    public void addStat(ArrayList<HeroStat> heroStats1) {
        heroStats1.add(new HeroStat("Сила", Str));
        heroStats1.add(new HeroStat("Ловкость", Agi));
        heroStats1.add(new HeroStat("Интеллект", Int));
        heroStats1.add(new HeroStat("Удача", Luc));
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {

        for (int i = 0; i < Data.bdLocations.size(); i++) {
            if (Data.bdLocations.get(i).getLocName().equals(location)) {
                this.locationId = i;
            }
        }
        this.location = location;
    }

    private int locationId;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int location) {
        this.locationId = location;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Инвентарь
    private ArrayList<Equipment> inventory = new ArrayList<>();

    //Добавить предмет в инвенарь(в начало инвентаря)
    public void addItemOnInventory(Equipment item) {
        this.inventory.add(0, item);
    }

    //Удалить предмет из инвентаря
    public void removeItemOnInventory(Equipment item) {
        this.inventory.remove(item);
    }

    //Надетая экипировка
    private ArrayList<Equipment> onEquip = new ArrayList<>();

    //надеть придмет
    public void onEquip(Equipment item) {
        this.onEquip.add(0, item);
        setEquipStat(item);
        removeItemOnInventory(item);
    }

    //снять предмет
    public void removeEquip(Equipment item) {
        this.onEquip.remove(item);
        reEquipStat(item);
        addItemOnInventory(item);
    }

    //Получение стат с одетой экипировки
    public void setEquipStat(Equipment item) {
        for (int e = 0; e < heroStats.size(); e++) {
            if (item.getStat().get(e) != 0) {
                heroStats.get(e).setCount(heroStats.get(e).getCount() + item.getStat().get(e));
                newHeroStats.get(e).setCount(newHeroStats.get(e).getCount() + item.getStat().get(e));
            }
        }
    }

    //Убираем статы при снятие экипировки
    public void reEquipStat(Equipment item) {
        for (int e = 0; e < heroStats.size(); e++) {
            if (item.getStat().get(e) != 0) {
                heroStats.get(e).setCount(heroStats.get(e).getCount() - item.getStat().get(e));
                newHeroStats.get(e).setCount(newHeroStats.get(e).getCount() + item.getStat().get(e));
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public double getPoint() {
        return point;
    }

    public void setPoint(double Point) {
        point = Point;
    }

    @Override
    public double getEXP() {
        return EXP;
    }

    @Override
    public void setEXP(double EXP) {
        this.EXP = EXP;
    }

    public double getEXP_for_Lvl() {
        return Math.floor(30 * Math.pow(4, (Lvl - 1)));
    }

    public void UpLvl() {
        while (30 * Math.pow(4, (Lvl - 1)) <= EXP) {
            if (30 * Math.pow(4, (Lvl - 1)) <= EXP) {
                Lvl++;
                setPoint(point + Math.floor(Math.log(Lvl) + 1));
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public double getLvl() {
        return Lvl;
    }

    @Override
    public void setLvl(double Lvl) {
        this.Lvl = Lvl;
    }

    @Override
    public double getStr() {
        return Str;
    }

    @Override
    public void setStr(double Str) {
        this.Str = Str;
    }

    @Override
    public double getHp() {
        return Math.floor((1 + Lvl / 10) * (20 + Str + (Str / 10 * 4)));
    }

    @Override
    public void setHp(double hp) {
        this.hp = hp;
    }


    @Override
    public double getHp_now() {
        return Math.floor(hp_now);
    }

    @Override
    public void setHp_now(double hp_now) {
        this.hp_now = hp_now;
    }

    //Поменял 10.05
    @Override
    public double getDmg() {
        return Math.floor((1 + Lvl / 10) * (5 + (Str / 4) + (Str / 10) * 2));//расчет урон без крита

    }

    @Override
    public void setDmg(double Dmg) {
        this.Dmg = Dmg;
    }

    //    public double getHr() {
//        return Math.floor(0.4 + Str/15 + Lvl/10);
//    }
    @Override
    public double getHr() {
        return 1;
    }

    @Override
    public void setHr(double Hr) {
        this.Hr = Hr;
    }

    @Override
    public double getAgi() {
        return Agi;
    }

    @Override
    public void setAgi(double Agi) {
        this.Agi = Agi;
    }

    @Override
    public double getDdg() {
        return Math.floor((20 * Agi * Agi) / (Agi * Agi + 30 * Agi) / 100);
    }

    @Override
    public void setDdg(double Ddg) {
        this.Ddg = Ddg;
    }

    @Override
    public double getAcc() {
        return 100;
//        return (50 + (40 * Agi * Agi) / (Agi * Agi + 40 * Agi) / 10);//50% - базовая меткость
    }

    @Override
    public void setAcc(double Acc) {
        this.Acc = Acc;
    }

    @Override
    public double getCritPower() {
        return (1.5 + (190 * Agi * Agi) / (Agi * Agi + 100 * Agi) / 100);//110% - базовый крит у всех (пока 150%, для тестов)
    }

    @Override
    public void setCritPower(double CritPower) {
        this.CritPower = CritPower;
    }

    @Override
    public double getInt() {
        return Int;
    }

    @Override
    public void setInt(double Int) {
        this.Int = Int;
    }

    @Override
    public double getSkillsPower() {
        return Math.floor((1 + Lvl / 10) * (Int / 2));
    }

    @Override
    public void setSkillsPower(double SkillsPower) {
        this.SkillsPower = SkillsPower;
    }

    @Override
    public double getMp() {
        return Math.floor((1 + Lvl / 10) * (10 + Int * 2 + (Lvl / 10 * 20)));
    }

    @Override
    public void setMp(double Mp) {
        this.Mp = Mp;
    }

    @Override
    public double getMr() {
        return Math.floor((1 + Lvl / 10) * (0.2 + Int * 0.03 + Lvl * 0.05));
    }

    @Override
    public void setMr(double Mr) {
        this.Mr = Mr;
    }

    @Override
    public double getLuc() {
        return Luc;
    }

    @Override
    public void setLuc(double Luc) {
        this.Luc = Luc;
    }


    @Override
    public double getCritChance() {
        return (50 + (90 * Luc * Luc) / (Luc * Luc + 35 * Luc) / 10); //10% - базовый шанс крита (пока 50%, для тестов)
    }

    @Override
    public void setCritChance(double CritChance) {
        this.CritChance = CritChance;
    }

    @Override
    public double getDropChance() {
        return DropChance;
    }

    @Override
    public void setDropChance(double DropChance) {
        this.DropChance = DropChance;
    }

    @Override
    public double getSkillChance() {
        return (60 + (40 * Luc * Luc) / (Luc * Luc + 35 * Luc) / 100);
    }

    @Override
    public void setSkillChance(double SkillChance) {
        this.SkillChance = SkillChance;
    }

    @Override
    public double getArmor() {
        return ((100 * Armor * Armor) / (Armor * Armor + 80 * Armor));//броня хранится в числах, возвращается в %
    }

    @Override
    public void setArmor(double Armor) {
        this.Armor = Armor;
    }

    ////////////////////
    public ArrayList<Equipment> getOnEquip() {
        return onEquip;
    }


    public ArrayList<Equipment> getInventory() {
        return inventory;
    }

    /////////////////
    @Override
    public double getAtribut(int num) {
        switch (num) {
            case 0:
                return Str;
            case 1:
                return Agi;
            case 2:
                return Int;
            case 3:
                return Luc;
            default:
                return Str;
        }
    }

    public void setAtribut(double atribut, double atribut1, double atribut2, double atribut3) {

        this.Str = atribut;
        this.Agi = atribut1;
        this.Int = atribut2;
        this.Luc = atribut3;

    }

    @Override
    public void setPosition(int position) {
        this.position=position;
    }

    @Override
    public int getPosition() {
        return position;
    }
    @Override
    public void setPositionBattle(int position) {
        this.positionBattle=position;
    }

    @Override
    public int getPositionBattle() {
        return positionBattle;
    }

    public void setXY(int x, int y) {
        this.x=x;
        this.y=y;
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
}


