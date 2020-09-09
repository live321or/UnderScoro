package com.samoylov.gameproject.character.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.Data;
import com.samoylov.gameproject.character.heroes.Hero;
import com.samoylov.gameproject.R;
import com.samoylov.gameproject.character.profile.adapers.BagAdapter;
import com.samoylov.gameproject.items.Equipment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyBagFragment extends Fragment {
    private Hero hero=Data.bdHeros.get(0);
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BagAdapter bagAdapter;
    private ArrayList<Equipment> BAG = hero.getInventory();
    private ArrayList<Integer> posSos=new ArrayList<>();
    private Button addArmour, addWeapon, bagClear;
    private String tag = "";
    private ArrayList<Equipment> test1;

    public MyBagFragment() {
        // Required empty public constructor
    }

    public static MyBagFragment newInstance(String tag) {
        MyBagFragment fragment = new MyBagFragment();
        fragment.tag = tag;
        return fragment;
    }

//    public void AddKnife() {
//        hero.addItemOnInventory(new Equipment.Weapon("Нож", R.drawable.ic_colorize_black,
//                0, 0, 1, 0, 0, 5));
//        bagAdapter.notifyDataSetChanged();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_bag, container, false);
        recyclerView = v.findViewById(R.id.bagList);
        buildBag();
        addArmour = v.findViewById(R.id.addArmour);
        addArmour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hero.addItemOnInventory(new Equipment.Armour("Щит", R.drawable.ic_security_black,
                        0, 2, 0, 0, 0, 100, "Щит", 15));
                bagAdapter.notifyDataSetChanged();

            }
        });

        addWeapon = v.findViewById(R.id.addWeapon);
        addWeapon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hero.addItemOnInventory(new Equipment.Weapon("Нож", R.drawable.ic_colorize_black,
                        0, 0, 1, 0, 0, 100, 5));
                bagAdapter.notifyDataSetChanged();

            }
        });


        bagClear = v.findViewById(R.id.bagClear);
        bagClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BAG.clear();
                bagAdapter.notifyDataSetChanged();
            }
        });
        return v;
    }

    public void buildBag() {

        layoutManager = new LinearLayoutManager(getActivity());
        if (tag == "") {
            //запрос на весь инвентарь //////////////////////////////////////////////////////////////////
            bagAdapter = new BagAdapter(BAG, tag);
        }
        if (tag == "Weapon") {
            // запрос на оружие////////////////////////////////////////////////////////////////////////////////////
            posSos.clear();
            test1 = new ArrayList<>();
            for (int i = 0; i < BAG.size(); i++) {
                if (BAG.get(i) instanceof Equipment.Weapon) {
                    test1.add(BAG.get(i));
                    posSos.add(i);
                }
            }
            bagAdapter = new BagAdapter(test1, tag);
        }
        if (tag == "Armour") {
            //запрос к серверу на щит/////////////////////////////////////////////////////////////////////////////////////
            posSos.clear();
            test1 = new ArrayList<>();
            for (int i = 0; i < BAG.size(); i++) {
                if (BAG.get(i) instanceof Equipment.Armour) {
                    test1.add(BAG.get(i));
                    posSos.add(i);
                }
            }
            bagAdapter = new BagAdapter(test1, tag);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bagAdapter);
        bagAdapter.setItemClick(new BagAdapter.ItemClick() {
            @Override
            public void onItemClik(int pos) {
                if (!tag.equals("")) {
                    hero.onEquip(test1.get(pos));
                    removeItem2(pos);
                    getActivity().getSupportFragmentManager().popBackStack();

                }
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void removeItem(int pos) {
        BAG.remove(pos);
        bagAdapter.notifyDataSetChanged();
    }

    public void removeItem2(int pos) {
        test1.remove(pos);
        bagAdapter.notifyDataSetChanged();
    }


}
