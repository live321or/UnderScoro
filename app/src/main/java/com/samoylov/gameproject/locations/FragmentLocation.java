package com.samoylov.gameproject.locations;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samoylov.gameproject.Data;
import com.samoylov.gameproject.items.Equipment;
import com.samoylov.gameproject.character.heroes.Hero;
import com.samoylov.gameproject.character.mobs.Mob;
import com.samoylov.gameproject.character.mobs.AboutPlaersFragment;
import com.samoylov.gameproject.R;
import com.samoylov.gameproject.locations.adapters.Locations;
import com.samoylov.gameproject.nextversion.GroupList;
import com.samoylov.gameproject.nextversion.RowType;
import com.samoylov.gameproject.nextversion.Transitions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLocation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLocation extends Fragment implements ExpListAdapter.OnCardClickListener {
    private Location location;

    private ExpListAdapter adapter;
    private LinearLayout linearLayout;
    private ExpandableListView listView;
    private TextView lName, lDescription;
    private AboutPlaersFragment aboutPlaersFragment;
//    private FragmentMyProfile fragmentMyProfile;
    private FragmentManager fragmentManager;
    private Button add1, add2, add3 ,add4;

    ArrayList<ArrayList<RowType>> dataSet= new ArrayList<>();
    private Hero hero;


// Нужен поток для переодического запроса////////////

    private Test2 listener;

    public FragmentLocation() {
    }

    public static FragmentLocation newInstance(FragmentManager fragmentManager) {
        FragmentLocation fragment = new FragmentLocation();
        fragment.fragmentManager = fragmentManager;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the la yout for this fragment
        View v = inflater.inflate(R.layout.fragment_location, container, false);
        listView = (ExpandableListView) v.findViewById(R.id.expanded_menu);

        add1 = v.findViewById(R.id.add1);
        add2 = v.findViewById(R.id.add2);
        add3 = v.findViewById(R.id.add3);
        add4 = v.findViewById(R.id.add4);

        lName = (TextView) v.findViewById(R.id.lName);
        lDescription = (TextView) v.findViewById(R.id.lDescription);
        location = Data.bdLocations.get(Data.bdHeros.get(0).getLocationId());
        //запрос данных локации на которой находиться герой(id/название)////////////////////////////////////////////////////////////////////
        // обработка полученной лкации////////////
        testLocation();
        setOnClick();
        startLoc();//отображение локации


        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Test2) {
            listener = (Test2) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }

    @Override
    public void onCardClick(String name, int pos, String tag) {
        switch (name) {
            case "Замкадье":
                //запрос данных локации на которой нахдиться герой(id/название)/////////////////////////////////
                // обработка полученной лкации//////////////////////////////////////////////////////////////////
                location = Data.bdLocations.get(1);
                Data.bdHeros.get(0).setLocation(location.getLocName());
                location.addPlayersOnLocationList(location.getLocName());
                location.addMobList(location.getLocName());
                location.addOnLocation();
                lName.setText(location.getLocName());
                lDescription.setText(location.getLocDescription());
                //Создаем адаптер и передаем context и список с данными
                adapter = new ExpListAdapter(getContext(), location.getOnLocation(),dataSet);
                listView.setAdapter(adapter);
                listView.expandGroup(0);
                listView.expandGroup(1);
                listView.expandGroup(2);
                break;
            case "Москва":
                Data.bdHeros.get(0).setLocation(name);

                location = Data.bdLocations.get(Data.bdHeros.get(0).getLocationId());
                startLoc();
                break;
            case "Площадь Приакроувера":
                location = Data.bdLocations.get(2);
                Data.bdHeros.get(0).setLocation(location.getLocName());
                location.addPlayersOnLocationList(location.getLocName());
                location.addMobList(location.getLocName());
                location.addOnLocation();
                lName.setText(location.getLocName());
                lDescription.setText(location.getLocDescription());
                //Создаем адаптер и передаем context и список с данными
                adapter = new ExpListAdapter(getContext(), location.getOnLocation(),dataSet);
                listView.setAdapter(adapter);
                listView.expandGroup(0);
                listView.expandGroup(1);
                listView.expandGroup(2);
                break;
            case "Ворота Приакроувера":
                location = Data.bdLocations.get(3);
                Data.bdHeros.get(0).setLocation(location.getLocName());
                location.addPlayersOnLocationList(location.getLocName());
                location.addMobList(location.getLocName());
                location.addOnLocation();
                lName.setText(location.getLocName());
                lDescription.setText(location.getLocDescription());
                //Создаем адаптер и передаем context и список с данными
                adapter = new ExpListAdapter(getContext(), location.getOnLocation(),dataSet);
                listView.setAdapter(adapter);
                listView.expandGroup(0);
                listView.expandGroup(1);
                listView.expandGroup(2);
                for (int i=0;i<3;i++) {
                    if (location.mobList.size() < 3) {
                        ArrayList<Equipment> items = new ArrayList<>();
                        items.add(new Equipment.Armour("Деревянный щит", R.drawable.ic_security_black,
                                0, 20, 0, 0, 0, 90, "Деревянный щит", 15));
                        Mob bomj = new Mob("Бездомный", 5, 12, 55, 1, 20,
                                5, "Ворота Приакроувера",Locations.Votota_Priakroyvera, 55, 180, 30, items);
                        Data.bdMob.add(bomj);
                        location.addMobList(location.getLocName());
                        location.addOnLocation();
                        Locations.Votota_Priakroyvera.addOnLocation();
                    }
                }
                    adapter.notifyDataSetChanged();
                break;
            case "Развилка у Приакроувера":
                location = Data.bdLocations.get(4);
                Data.bdHeros.get(0).setLocation(location.getLocName());
                location.addPlayersOnLocationList(location.getLocName());
                location.addMobList(location.getLocName());
                location.addOnLocation();
                lName.setText(location.getLocName());
                lDescription.setText(location.getLocDescription());
                //Создаем адаптер и передаем context и список с данными
                adapter = new ExpListAdapter(getContext(), location.getOnLocation(),dataSet);
                listView.setAdapter(adapter);
                listView.expandGroup(0);
                listView.expandGroup(1);
                listView.expandGroup(2);
                break;
            case "Берег у реки Кита":
                location = Data.bdLocations.get(5);
                Data.bdHeros.get(0).setLocation(location.getLocName());
                location.addPlayersOnLocationList(location.getLocName());
                location.addMobList(location.getLocName());
                location.addOnLocation();
                lName.setText(location.getLocName());
                lDescription.setText(location.getLocDescription());
                //Создаем адаптер и передаем context и список с данными
                adapter = new ExpListAdapter(getContext(), location.getOnLocation(),dataSet);
                listView.setAdapter(adapter);
                listView.expandGroup(0);
                listView.expandGroup(1);
                listView.expandGroup(2);
                for (int i=0;i<4;i++) {
                    if (location.mobList.size() < 6) {
                        ArrayList<Equipment> items = new ArrayList<>();
                        items.add(new Equipment.Weapon("Деревянный меч", R.drawable.ic_colorize_black,
                                0, 8, 0, 0, 0, 50, 150));
                        Mob plut = new Mob("Плут", 15, 12, 55, 1, 20,
                                5, "Берег у реки Кита",Locations.Kit, 15, 20, 30, items);
                        Data.bdMob.add(plut);
                        location.addMobList(location.getLocName());
                        location.addOnLocation();
                        Locations.Kit.addOnLocation();
                    }
                }
                for (int i=0;i<2;i++) {
                    if (location.mobList.size() < 6) {
                        ArrayList<Equipment> items = new ArrayList<>();
                        items.add(new Equipment.Weapon("Деревянный меч", R.drawable.ic_colorize_black,
                                0, 15, 0, 0, 0, 50, 300));
                        Mob vol4onok = new Mob("Волчонок", 40, 12, 55, 1, 20,
                                5, "Берег у реки Кита",Locations.Kit, 40, 20, 30, items);
                        Data.bdMob.add(vol4onok);
                        location.addMobList(location.getLocName());
                        location.addOnLocation();
                        Locations.Kit.addOnLocation();
                    }
                }
                adapter.notifyDataSetChanged();
                break;
            case "Пещера Велонис":
                location = Data.bdLocations.get(6);
                Data.bdHeros.get(0).setLocation(location.getLocName());
                location.addPlayersOnLocationList(location.getLocName());
                location.addMobList(location.getLocName());
                location.addOnLocation();
                lName.setText(location.getLocName());
                lDescription.setText(location.getLocDescription());
                //Создаем адаптер и передаем context и список с данными
                adapter = new ExpListAdapter(getContext(), location.getOnLocation(),dataSet);
                listView.setAdapter(adapter);
                listView.expandGroup(0);
                listView.expandGroup(1);
                listView.expandGroup(2);
                if (location.mobList.size() < 1) {
                    ArrayList<Equipment> items = new ArrayList<>();
                    items.add(new Equipment.Weapon("Легендарный меч", R.drawable.ic_colorize_black,
                            0, 90, 0, 0, 0, 100, 700));
                    Mob basan = new Mob("Басан", 300, 12, 55, 1, 20,
                            5, "Пещера Велонис",Locations.Velonis, 300, 20, 30, items);
                    Data.bdMob.add(basan);
                    location.addMobList(location.getLocName());
                    location.addOnLocation();
                    Locations.Velonis.addOnLocation();
                }
                adapter.notifyDataSetChanged();
                break;
                default:
                break;
        }

        if (pos == 1 && tag == null) {
            for (int i = 0; i < Data.bdHeros.size(); i++) {
                if (name == Data.bdHeros.get(i).getName()) {
                    listener.onSelected("null", i);

                }
            }
        }
        if (pos == 2 && tag == "a") {
            for (int i = 0; i < Data.bdHeros.size(); i++) {
                if (name == Data.bdHeros.get(i).getName()) {
                    listener.onSelected("a", i);

                }
            }
        }
        if (pos == 1 && tag == "b") {
            for (int i = 0; i < Data.bdHeros.size(); i++) {
                if (name == Data.bdHeros.get(0).getName()) {
                    listener.onSelected(tag, 0);
                    i = Data.bdHeros.size();
                } else {
                    if (name == Data.bdHeros.get(i).getName()) {
                        listener.onSelected("a", i);
                    }
                }
            }
        }
        if (pos == 2) {
            listener.onSelected(tag, 0);
        }
    }

    @Override
    public void onClickBattle(int id,int tag) {
        listener.onBattle(id,tag);
    }


    public void startLoc() {

        location.addPlayersOnLocationList(location.getLocName());
        location.addMobList(location.getLocName());
        location.addOnLocation();// создание локация
        lName.setText(location.getLocName());
        lDescription.setText(location.getLocDescription());
        //Создаем адаптер и передаем context и список с данными
        adapter = new ExpListAdapter(getContext(), location.getOnLocation(),dataSet);
        adapter.setOnCardClickListener(this);
        listView.setAdapter(adapter);
        listView.expandGroup(0);
        listView.expandGroup(1);
        listView.expandGroup(2);


    }

    public void add1(View view) {

    }

    public void setOnClick() {
        add1.setOnClickListener(v -> {
            ArrayList<Equipment> items = new ArrayList<>();
            items.add(new Equipment.Weapon("Деревянный меч", R.drawable.ic_colorize_black,
                    0, 10, 0, 0, 0, 50, 1000));
            Mob Volk = new Mob("Бандит", 25, 5, 1, 5, 20,
                    1, "Москва", Locations.Moskva, 25, 15, 10, items);
            Data.bdMob.add(Volk);
            location.addMobList(location.getLocName());
            location.addOnLocation();
            Locations.Moskva.addOnLocation();
//                adapter = new ExpListAdapter(getContext(), location.getOnLocation());
//                listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Equipment> items = new ArrayList<>();
                items.add(new Equipment.Armour("Картонка", R.drawable.ic_security_black,
                        0, 20, 0, 0, 0, 90, "Деревянный щит", 15));
                Mob bomj = new Mob("Бездомный", 5, 12, 55, 1, 20,
                        5, "Замкадье",Locations.Mkad, 55, 180, 30, items);
                Data.bdMob.add(bomj);
                location.addMobList(location.getLocName());
                location.addOnLocation();
                Locations.Mkad.addOnLocation();
                adapter.notifyDataSetChanged();
            }
        });
        add3.setOnClickListener(v -> {
            ArrayList<Equipment> items = new ArrayList<>();
//                items.add(new Equipment.Armour("Картонка", R.drawable.ic_security_black,
//                        0, 20, 0, 0, 0, 90, "Деревянный щит", 15));
            Mob zaMKAD = new Mob("Нищий", 20, 8, 40, 50, 1,
                    7, "Москва",Locations.Moskva, 2, 3, 500, items);
            Data.bdMob.add(zaMKAD);
            location.addMobList(location.getLocName());
            location.addOnLocation();
            Locations.Moskva.addOnLocation();
            adapter.notifyDataSetChanged();
        });
        add4.setOnClickListener(v -> {
            listener.goNewLocation();

        });
    }

    private void testLocation(){

        ArrayList<RowType> navSet=new ArrayList<>();
        navSet.add(new Transitions("Замкадье"));
        navSet.add(new Transitions("Дом"));
        navSet.add(new Transitions("Площадь Приакроувера"));
        navSet.add(new Transitions("Ворота Приакроувера"));
        navSet.add(new Transitions("Развилка у Приакроувера"));
        navSet.add(new Transitions("Берег реки Кита"));
        navSet.add(new Transitions("Пещера Велонис"));
        dataSet.add(navSet);
        ArrayList<RowType> groupSet=new ArrayList<>();
        groupSet.add(new GroupList("Переходы"));
        dataSet.add(groupSet);

    }
//    @OnClick(R.id.add4)
//    void goBattle(View view){
//        listener.goBattle();
//    }
}
