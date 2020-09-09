package com.samoylov.gameproject.battle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samoylov.gameproject.Data;
import com.samoylov.gameproject.Person;
import com.samoylov.gameproject.R;
import com.samoylov.gameproject.character.heroes.Hero;
import com.samoylov.gameproject.character.mobs.Mob;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BattlefieldFragment extends Fragment implements AdapterBattlefield.BattleListener {


    @BindView(R.id.battlefield)
    RecyclerView battlefield;
    @BindView(R.id.fab)
    FloatingActionButton attack;
    AdapterBattlefield adapterBattlefield;
    ArrayList<Battlefield> boxes = new ArrayList<>();
    Mob enemy;
    Hero player;

    public BattlefieldFragment() {
        // Required empty public constructor
    }

    public static BattlefieldFragment newInstance(Hero player,Mob enemy) {
        BattlefieldFragment fragment = new BattlefieldFragment();
        fragment.enemy = enemy;
        fragment.player=player;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.battlefield_fragment, container, false);
        ButterKnife.bind(this, v);
        attack.hide();
        setBoxes();

        battlefield.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        adapterBattlefield = new AdapterBattlefield(boxes);
        adapterBattlefield.setBattleListener(this);
        battlefield.setAdapter(adapterBattlefield);
        attack.setOnClickListener(v1 -> {
            Toast.makeText(getActivity().getApplicationContext(),"1",Toast.LENGTH_SHORT).show();
            hod(enemy,player);
            adapterBattlefield.notifyDataSetChanged();
            if (enemy.getHp_now() <= 0) {
                boxes.set(enemy.getPositionBattle(),new BoxEmpty());
                enemy.setDead(true);

                attack.setEnabled(false);
                adapterBattlefield.notifyDataSetChanged();
            }else {
                adapterBattlefield.notifyDataSetChanged();
                hod(player,enemy);
            }
            adapterBattlefield.notifyDataSetChanged();
        });
        return v;
    }

    void setBoxes() {
        for (int i = 0; i < 35; i++) {
            if(i<5) {
                boxes.add(new BoxEmpty());
                boxes.get(i).setPosition(i);
                boxes.get(i).setRadius(i, 27);
                boxes.get(i).setXY(i,6);
            }else
            if(i<10) {
                boxes.add(new BoxEmpty());
                boxes.get(i).setPosition(i);
                boxes.get(i).setRadius(i, 27);
                boxes.get(i).setXY(i-5,5);
            }else
            if(i<15) {
                boxes.add(new BoxEmpty());
                boxes.get(i).setPosition(i);
                boxes.get(i).setRadius(i, 27);
                boxes.get(i).setXY(i-10,4);
            }else
            if(i<20) {
                boxes.add(new BoxEmpty());
                boxes.get(i).setPosition(i);
                boxes.get(i).setRadius(i, 27);
                boxes.get(i).setXY(i-15,3);
            }else
            if(i<25) {
                boxes.add(new BoxEmpty());
                boxes.get(i).setPosition(i);
                boxes.get(i).setRadius(i, 27);
                boxes.get(i).setXY(i-20,2);
            }else
            if(i<30) {
                boxes.add(new BoxEmpty());
                boxes.get(i).setPosition(i);
                boxes.get(i).setRadius(i, 27);
                boxes.get(i).setXY(i-25,1);
            }else
            if(i<35) {
                boxes.add(new BoxEmpty());
                boxes.get(i).setPosition(i);
                boxes.get(i).setRadius(i, 27);
                boxes.get(i).setXY(i-30,0);
            }
        }
        boxes.set(7, new BoxEnemy(enemy));
        boxes.get(7).setPosition(7);
        boxes.set(27, new BoxPlayer(player));
        boxes.get(27).setPosition(27);
        enemy.setXY(2,5);
        enemy.setPositionBattle(7);
        player.setXY(2,1);
        player.setPositionBattle(27);

    }
    private void hod(Person defending, Person attacking) {
        if (Math.random() * 100 <= attacking.getAcc()) {//попадание
            if (Math.random() * 100 <= attacking.getCritChance()) {//Шанс крита
                double Crit = attacking.getCritPower();
                double dmg = attacking.getDmg();
                defending.setHp_now(defending.getHp_now() - dmg * Crit);//Крит прошел, расчет урона с крита

                adapterBattlefield.notifyDataSetChanged();
            } else {
                double dmg = attacking.getDmg();
                defending.setHp_now(defending.getHp_now() - dmg);//крит не прошел, расчет урон без крита

                adapterBattlefield.notifyDataSetChanged();
            }
        } else {


        }
    }
    @Override
    public void onUpdate() {
        attack.hide();

        adapterBattlefield.notifyDataSetChanged();
    }

    @Override
    public void onAttack() {
        attack.show();
    }

    @Override
    public void onAttackEnemy() {
        hod(player,enemy);
    }
}