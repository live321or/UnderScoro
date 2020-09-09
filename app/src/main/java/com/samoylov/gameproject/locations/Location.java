package com.samoylov.gameproject.locations;

import com.samoylov.gameproject.Data;
import com.samoylov.gameproject.nextversion.Transitions;

import java.util.ArrayList;

public class Location {
    //    Название текущей локаци
//    и ее опсание
    private String locName, locDescription;
    private ArrayList<Transitions> transitions = new ArrayList<>();
    ArrayList<Location> locations;

    public Location(String locN, String locDescription) {

        this.locName = locN;
        this.locDescription = locDescription;
        addName();
        addOnLocation();
    }

//    public int getSize1(int i) {
////        getSize2(locations.get(i));
//        int b=0;
//        Location l = locations.get(i);
//        if (l instanceof Location.Transitions) {
//            b= ((Transitions) l).getTransition().size();
//        }
//        if (l instanceof Location.PlayersList) {
//            b= ((PlayersList) l).getPlayersList().size();
//        }
//        if (l instanceof Location.MobList) {
//            b= ((MobList) l).getMobList().size();
//        }
//        if (l instanceof Location.GroupList) {
//            b= ((GroupList) l).getName().size();
//        }
//        return b;
//
//    }


    private ArrayList<ArrayList<String>> onLocation = new ArrayList<>();

    public ArrayList<ArrayList<String>> getOnLocation() {
        return onLocation;
    }

    public void addOnLocation() {
        onLocation.clear();
        onLocation.add(transition);
        onLocation.add(playersList);
//        onLocation.add(dropList);
        onLocation.add(mobList);
        onLocation.add(name);
        onLocation.add(idMob);
    }

    private ArrayList<String> transition = new ArrayList<>();
    public void addTransition(String name){
        this.transition.add(name);
    }

    //    private ArrayList<String> name = new ArrayList<String>();
//

    private ArrayList<String> playersList = new ArrayList<>();

    public void addPlayersOnLocationList(String locationName) {
        playersList.clear();
        for (int i = 0; i < Data.bdHeros.size(); i++) {
            if (locationName == Data.bdHeros.get(i).getLocation()) {
                playersList.add(Data.bdHeros.get(i).getName());
            }
        }
    }
    private ArrayList<String> idMob=new ArrayList<>();
    public ArrayList<String> mobList = new ArrayList<>();


    public void addMobList(String locationName) {
        mobList.clear();
        idMob.clear();
        if (Data.bdMob.size() > 0) {
            for (int i = 0; i < Data.bdMob.size(); i++) {
                if (locationName == Data.bdMob.get(i).getLocation()) {
                    mobList.add(Data.bdMob.get(i).getName() + " id: " + Data.bdMob.get(i).getId());
                    idMob.add(Integer.toString(Data.bdMob.get(i).getId()));
//                    addIdMob(Data.bdMob.get(i).getId());
                }
            }
        }
    }
    private void addIdMob(int id){
        idMob.add(Integer.toString(id));
    }
    private ArrayList<String> name = new ArrayList<String>();
    private void addName() {
        name.add("Переходы");
        name.add("Жители");
        name.add("Монстры");
    }




//    public static class Transitions extends Location {
//
//        private ArrayList<String> transition = new ArrayList<>();
//
//        public Transitions() {
//            super();
//            addTransition("Переходы");
//            addTransition("Жители");
//            addTransition("Монстры");
//        }
//
//        private void addTransition(String name) {
//            transition.add(name);
//        }
//
//
//        public ArrayList<String> getTransition() {
//            return transition;
//        }
//    }
//
//    public class PlayersList extends Location {
//        private ArrayList<String> playersList = new ArrayList<>();
//
//        public void addPlayersOnLocationList(String locationName) {
//            playersList.clear();
//            for (int i = 0; i < Data.bdHeros.size(); i++) {
//                if (locationName == Data.bdHeros.get(i).getLocation()) {
//                    playersList.add(Data.bdHeros.get(i).getName());
//                }
//            }
//        }
//
//        public ArrayList<String> getPlayersList() {
//            return playersList;
//        }
//    }
//
//    public class MobList extends Location {
//        private ArrayList<String> mobList = new ArrayList<>();
//
//        public void addMobList(String mob) {
//            mobList.add(mob);
//        }
//
//        public ArrayList<Mob> getMobList() {
//            return mobList;
//        }
//    }
//
//    public class GroupList extends Location {
//        private ArrayList<String> name = new ArrayList<String>();
//
//        public void addName(String name) {
//            this.name.add(name);
//        }
//
//        public ArrayList<String> getName() {
//            return name;
//        }
//    }


    public String getLocName() {
        return locName;
    }

    public String getLocDescription() {
        return locDescription;
    }


}
