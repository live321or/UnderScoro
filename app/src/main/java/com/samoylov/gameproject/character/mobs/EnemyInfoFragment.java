package com.samoylov.gameproject.character.mobs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.samoylov.gameproject.items.Equipment;
import com.samoylov.gameproject.R;
import com.samoylov.gameproject.locations.Test2;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnemyInfoFragment extends Fragment {

    private Mob mob;

    private ArrayList<Equipment> myEq;
    private TextView getName, getLvl, getEXP, getHp, getDmg, drp;
    private ImageView armour;
    private boolean onW = false, onAr = false;
    private RecyclerView eStat;
    private AboutProfileAdapter aboutProfileAdapter;
    private int i = 0;
    private Button button, button2;
    private Test2 test;

    public EnemyInfoFragment() {
        // Required empty public constructor
    }

    public static EnemyInfoFragment newInstance(Mob i) {
        EnemyInfoFragment fragment = new EnemyInfoFragment();
        fragment.mob = i;
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// запрос к статам//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        mob = Data.bdMob.get(0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_enemy_info, container, false);

//        myEq = hero.getOnEquip();
        onBuildStat(v);
        onBuildListStst();


        return v;
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof Test2) {
//            test = (Test2) context;
//        } else {
//            throw new ClassCastException(context.toString() + " must implement listener");
//        }
//    }


    private void onBuildStat(View v) {
        armour = v.findViewById(R.id.eDrop);
        getName = (TextView) v.findViewById(R.id.eName);
        getLvl = (TextView) v.findViewById(R.id.eLvl);
        getEXP = (TextView) v.findViewById(R.id.eEXP);
        getHp = (TextView) v.findViewById(R.id.eHp);
        getDmg = (TextView) v.findViewById(R.id.eDmg);
        drp = (TextView) v.findViewById(R.id.eDropName);
        getName.setText(mob.getName());
        getEXP.setText("Опыт: " + Double.toString(mob.getEXP()));
        getHp.setText("Жизней: " + mob.getHp());
        getLvl.setText("Уровень: " + mob.getLvl());
        getDmg.setText("Урон: " + mob.getDmg());
        int res = 0;
        if (mob.dropItems.size()!=0){
        armour.setImageResource(mob.dropItems.get(0).getmImageResource());
        drp.setText(mob.dropItems.get(0).getName());}else {
            armour.setVisibility(View.INVISIBLE);
            drp.setText("Ничего");
        }
//        for (int g = 0; g < myEq.size(); g++) {
//            if (myEq.get(g) instanceof Equipment.Weapon) {
//                weapon.setImageResource(myEq.get(g).getmImageResource());
//            }
//            if (myEq.get(g) instanceof Equipment.Armour) {
//                armour.setImageResource(myEq.get(g).getmImageResource());
//            }
//        }

        eStat = v.findViewById(R.id.eStat);


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Test2) {
            test = (Test2) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }

    private void onBuildListStst() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        eStat.setLayoutManager(layoutManager);
        aboutProfileAdapter = new AboutProfileAdapter(mob.mobStats);
        eStat.setAdapter(aboutProfileAdapter);
    }


}
