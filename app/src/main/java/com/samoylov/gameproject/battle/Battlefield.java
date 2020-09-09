package com.samoylov.gameproject.battle;

import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.Person;

public interface Battlefield {
    int BOX_EMPTY = 0;
    int BOX_PLAYER = 1;
    int BOX_ENEMY = 2;

    int getItemViewType();

    int getPosition();

    void setPosition(int position);
    boolean getFlag(int pos,int posPlayer);
    void setRadius(int radius,int pos);
    void setXY(int x,int y);
    int getX();
    int getY();
    Person getAction();
}