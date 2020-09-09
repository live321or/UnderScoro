package com.samoylov.gameproject.locations;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.samoylov.gameproject.Data;
import com.samoylov.gameproject.R;
import com.samoylov.gameproject.character.heroes.Hero;
import com.samoylov.gameproject.character.mobs.Mob;
import com.samoylov.gameproject.items.Equipment;
import com.samoylov.gameproject.locations.adapters.Locations;
import com.samoylov.gameproject.locations.adapters.MobAdapter;
import com.samoylov.gameproject.locations.adapters.PlayersAdapter;
import com.samoylov.gameproject.locations.adapters.TransitionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;


public class NewLocation extends Fragment implements TransitionAdapter.Update, PlayersAdapter.Update, MobAdapter.Update {
    @BindViews({R.id.goList, R.id.playersList, R.id.mobList})
    List<RecyclerView> recyclerViews;
    LocationSettings location;
    @BindViews({R.id.newlocationName, R.id.newlocatioDescription, R.id.goText, R.id.playersText, R.id.mobText})
    List<TextView> textViews;

    TransitionAdapter transitionAdapter;
    PlayersAdapter playersAdapter;
    MobAdapter mobAdapter;
    boolean flag = false, flag2 = false, flag3 = false;
    private Test2 listener;


    public NewLocation() {
        // Required empty public constructor
    }


    public static NewLocation newInstance(Toolbar toolbar) {
        NewLocation fragment = new NewLocation();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_location, container, false);
        ButterKnife.bind(this, v);
        location = Data.bdHeros.get(0).getLocation1();
        Toast.makeText(getActivity().getApplicationContext(),""+location.getTransitions().size(),Toast.LENGTH_SHORT).show();
        onCreateContent();
        textViews.get(2).setOnClickListener(v1 -> {
            if (!flag) {
                transitionAdapter.setSize(0);
                transitionAdapter.notifyDataSetChanged();
                flag = true;
            } else {
                transitionAdapter.setSize(location.getTransitions().size());
                transitionAdapter.notifyDataSetChanged();
                flag = false;
            }
            recyclerViews.get(0).setAdapter(transitionAdapter);
        });
        textViews.get(3).setOnClickListener(v1 -> {
            if (!flag2) {
                playersAdapter.setSize(0);
                playersAdapter.notifyDataSetChanged();
                flag2 = true;
            } else {
                playersAdapter.setSize(location.getPlayersList().size());
                playersAdapter.notifyDataSetChanged();
                flag2 = false;
            }
            recyclerViews.get(1).setAdapter(playersAdapter);
        });
        textViews.get(4).setOnClickListener(v1 -> {
            if (!flag3) {
                mobAdapter.setSize(0);
                mobAdapter.notifyDataSetChanged();
                flag3 = true;
            } else {

                mobAdapter.setSize(location.getMobList().size());
                mobAdapter.notifyDataSetChanged();
                flag3 = false;
            }
            recyclerViews.get(2).setAdapter(mobAdapter);
        });


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
    private void onCreateContent() {

        textViews.get(0).setText(location.getLocName());
        textViews.get(1).setText(location.getLocDescription());

        recyclerViews.get(0).setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViews.get(1).setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViews.get(2).setLayoutManager(new LinearLayoutManager(getActivity()));

        transitionAdapter = new TransitionAdapter(location.getTransitions(), location.getTransitions().size());
        playersAdapter = new PlayersAdapter(location.getPlayersList(), location.getPlayersList().size());
        mobAdapter = new MobAdapter(location.getMobList(), location.getMobList().size());

        transitionAdapter.setOnUpdateListener(this);
        playersAdapter.setOnUpdateListener(this);
        mobAdapter.setOnUpdateListener(this);

        recyclerViews.get(0).setAdapter(transitionAdapter);
        recyclerViews.get(1).setAdapter(playersAdapter);
        recyclerViews.get(2).setAdapter(mobAdapter);
    }

    @Override
    public void onUpdate() {
//        transitionAdapter.notifyDataSetChanged();
        location = Data.bdHeros.get(0).getLocation1();
        location.addOnLocation();
        transitionAdapter.setContent(location.getTransitions());
        transitionAdapter.notifyDataSetChanged();
        playersAdapter.setContent(location.getPlayersList());
        playersAdapter.notifyDataSetChanged();
        mobAdapter.setContent(location.getMobList());
        mobAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdatePlayer(Hero player) {
    listener.infoPlayer(player);
    }

    @Override
    public void infoMob(Mob mob) {
        listener.infoMob(mob);
    }

    @Override
    public void goBattleMob(Mob mob) {
        listener.goBattle(mob);
    }

}