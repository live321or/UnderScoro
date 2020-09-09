package com.samoylov.gameproject.locations;

import com.samoylov.gameproject.character.heroes.Hero;
import com.samoylov.gameproject.character.mobs.Mob;

public interface Test2 {
    public void test(boolean i);

    public void onSelected(String tag, int i);

    public void onBattle(int id, int tag);

    public void onEquipItem(String tag);

    void infoPlayer(Hero player);

    void infoMob(Mob mob);

    void goNewLocation();

    void goBattle(Mob mob);
}
