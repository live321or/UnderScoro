package com.samoylov.gameproject.battle;

import java.util.ArrayList;

public class Serves {
    ArrayList<Battlefield> battlefield;
    ArrayList<Integer> playerPosition;
    int playerPos= 27;

    public Serves() {

    }

    void createBattlefield() {

    }

    void onSelect(int pos, int itemView) {
        switch (itemView) {
            case Battlefield.BOX_EMPTY:
                battlefield.set(pos,battlefield.get(playerPos));
                battlefield.set(playerPos,new BoxEmpty());
                playerPos=pos;
            case Battlefield.BOX_PLAYER:
                return;
            case Battlefield.BOX_ENEMY:
                return;
            default:
                return;
        }
    }
    void getRadius(){

    }
}
