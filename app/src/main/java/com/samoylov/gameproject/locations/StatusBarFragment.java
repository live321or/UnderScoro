package com.samoylov.gameproject.locations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samoylov.gameproject.character.heroes.Hero;
import com.samoylov.gameproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatusBarFragment extends Fragment {

    private Hero hero;
    TextView sLvl, sHp, sXpe;

    public StatusBarFragment() {
        // Required empty public constructor
    }

    public static StatusBarFragment newInstance(Hero hero) {
        StatusBarFragment fragment = new StatusBarFragment();
        fragment.hero = hero;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status_bar, container, false);
        sLvl = view.findViewById(R.id.sLvl);
        sHp = view.findViewById(R.id.sHp);
        sXpe = view.findViewById(R.id.sExp);
        sLvl.setText("Lvl " + hero.getLvl());
        sHp.setText("Hp " + hero.getHp_now() + "/" + hero.getHp());
        sXpe.setText("Exp " + hero.getEXP() + "/" + hero.getEXP_for_Lvl());
        return view;
    }
    public void potok() {
        sHp.setText("Hp " + hero.getHp_now() + "/" + hero.getHp());
    }
}
