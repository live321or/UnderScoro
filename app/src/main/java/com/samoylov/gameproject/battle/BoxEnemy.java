package com.samoylov.gameproject.battle;

import com.samoylov.gameproject.character.mobs.Mob;

public class BoxEnemy implements Battlefield {
    int position = 0;
    int x, y;
    boolean flag = true;
    Mob enemy;

    public Mob getAction() {
        return enemy;
    }

    @Override
    public int getItemViewType() {
        return Battlefield.BOX_ENEMY;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean getFlag(int radius, int pos) {
        int rad[] = {-6, -5, -4, -1, 1, 4, 5, 6};
        for (int i = 0; i < rad.length; i++) {
            if (radius - pos == rad[i]) {
                this.flag = true;
                i = rad.length;
            } else this.flag = false;
        }
        return flag;
    }

    @Override
    public void setRadius(int radius, int pos) {
        int rad[] = {-6, -5, -4, -1, 1, 4, 5, 6};
        for (int i = 0; i < rad.length; i++) {
            if (radius - pos == rad[i]) {
                this.flag = true;
                i = rad.length;
            } else this.flag = false;
        }
    }

    @Override
    public void setXY(int x, int y) {

    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }



    public void setEnemy(Mob enemy) {
        this.enemy = enemy;
    }

    public BoxEnemy(Mob enemy) {
        this.enemy = enemy;
    }
}
