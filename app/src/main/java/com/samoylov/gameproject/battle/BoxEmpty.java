package com.samoylov.gameproject.battle;

import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.Person;

public class BoxEmpty implements Battlefield {

    int position = 0;
    int x, y;
    boolean flag = true;


    public BoxEmpty() {

    }

    @Override
    public int getItemViewType() {
        return Battlefield.BOX_EMPTY;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;

    }

    public boolean getFlag(int radius, int pos) {
        int[] rad = {-6, -5, -4, -1, 1, 4, 5, 6};


        if (pos % 10 == 0 || pos % 10 == 5) {
            rad = new int[]{-5, -4, 1, 5, 6};
        }

        if (pos % 10 == 4 || pos % 10 == 9) {
            rad = new int[]{-5, -6, -1, 5,4};
        }

        for (int i = 0; i < rad.length; i++) {
            if (radius - pos == rad[i]) {
                this.flag = true;
                i = rad.length;
            } else this.flag = false;
        }
        return flag;
    }

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
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Person getAction() {
        return null;
    }


}
