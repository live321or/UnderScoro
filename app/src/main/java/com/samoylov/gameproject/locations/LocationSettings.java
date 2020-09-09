package com.samoylov.gameproject.locations;

import com.samoylov.gameproject.Data;
import com.samoylov.gameproject.character.heroes.Hero;
import com.samoylov.gameproject.character.mobs.Mob;

import java.util.ArrayList;

public class LocationSettings {

    private String locName, locDescription;
    private ArrayList<LocationSettings> transitions = new ArrayList<>();
    private ArrayList<Hero> playersList = new ArrayList<>();
    private ArrayList<Mob> mobList = new ArrayList<>();






    public LocationSettings(String locN, String locDescription) {

        this.locName = locN;
        this.locDescription = locDescription;


    }

    public void addOnLocation() {
        addPlayersOnLocationList(locName);
        addMobList(locName);
    }

    public void addTransition(LocationSettings location){
        transitions.add(location);
    }


    public void addPlayersOnLocationList(String locationName) {
        playersList.clear();
        for (int i = 0; i < Data.bdHeros.size(); i++) {
            if (locationName == Data.bdHeros.get(i).getLocation1().getLocName()) {
                playersList.add(Data.bdHeros.get(i));
            }
        }
    }

    public void addMobList(String locationName) {
        mobList.clear();
        if (Data.bdMob.size() > 0) {
            for (int i = 0; i < Data.bdMob.size(); i++) {
                if (locationName == Data.bdMob.get(i).getLocation1().getLocName()) {
                    mobList.add(Data.bdMob.get(i));
                }
            }
        }
    }

    public String getLocName() {
        return locName;
    }

    public String getLocDescription() {
        return locDescription;
    }


    public ArrayList<LocationSettings> getTransitions() {
        return transitions;
    }
    public ArrayList<Hero> getPlayersList() {
        return playersList;
    }
    public ArrayList<Mob> getMobList() {
        return mobList;
    }
}
