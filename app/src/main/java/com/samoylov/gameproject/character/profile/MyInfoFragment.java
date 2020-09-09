package com.samoylov.gameproject.character.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.Data;
import com.samoylov.gameproject.character.heroes.Hero;
import com.samoylov.gameproject.character.heroes.HeroStat;
import com.samoylov.gameproject.character.profile.adapers.MyProfileStatAdapter;
import com.samoylov.gameproject.items.Equipment;
import com.samoylov.gameproject.MainActivity;
import com.samoylov.gameproject.R;
import com.samoylov.gameproject.locations.Test2;
import com.samoylov.gameproject.authorization.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment implements MyProfileStatAdapter.OnPorofileClicListener {

    private Hero hero;
    private ArrayList<HeroStat> newHeroStat;
    private ArrayList<HeroStat> gg;
    private ArrayList<HeroStat> buffer;
    private ArrayList<Equipment> myEq;
    private TextView getName, getLvl, getEXP, getPoint;
    private ImageView weapon, armour;
    private boolean onW = false, onAr = false;
    private RecyclerView list_My_Stat;
    private MyProfileStatAdapter myProfileStatAdapter;
    private int i = 0;
    private Button button, button2;
    private Test2 test;

    public MyInfoFragment() {
        // Required empty public constructor
    }

    public static MyInfoFragment newInstance(int i) {
        MyInfoFragment fragment = new MyInfoFragment();
        fragment.i = i;
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// запрос к статам//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        hero = Data.bdHeros.get(0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_info, container, false);
        gg = hero.getHeroStats();
        newHeroStat = hero.getNewHeroStats();
        myEq = hero.getOnEquip();
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
        weapon = v.findViewById(R.id.onWeapon);//если одет то ссылка на картинку
        armour = v.findViewById(R.id.onSheld);
        getName = (TextView) v.findViewById(R.id.get_My_Name);
        getLvl = (TextView) v.findViewById(R.id.get_My_Lvl);
        getEXP = (TextView) v.findViewById(R.id.get_My_EXP);
        getPoint = (TextView) v.findViewById(R.id.get_My_Point);
        getName.setText(hero.getName());
        getEXP.setText("Опыт: " + Double.toString(hero.getEXP()));
//        getLvl.setText("Уровень: " + Double.toString(hero.getLvl()));
        getLvl.setText("Уровень: " + hero.getLvl());
        getPoint.setText(Double.toString(hero.getPoint()));

        for (int g = 0; g < myEq.size(); g++) {
            if (myEq.get(g) instanceof Equipment.Weapon) {
                weapon.setImageResource(myEq.get(g).getmImageResource());
            }
            if (myEq.get(g) instanceof Equipment.Armour) {
                armour.setImageResource(myEq.get(g).getmImageResource());
            }
        }

        list_My_Stat = v.findViewById(R.id.list_My_Stat);

        button = (Button) v.findViewById(R.id.b_My_Apply);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hero.setPoint(Double.parseDouble(getPoint.getText().toString()));
                if (buffer != null) {
                    for (int i = 0; i < 4; i++) {
                        newHeroStat.get(i).setCount(buffer.get(i).getCount());
                    }


                    Call<User> call = MainActivity.apiInterface.add_stat(MainActivity.prefConfig.readCookie(), newHeroStat.get(0).getCount(),
                            newHeroStat.get(1).getCount(), newHeroStat.get(2).getCount(), newHeroStat.get(3).getCount());
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.code() == 200) {
                                Data.bdHeros.get(0).setAtribut(response.body().getStr(), response.body().getAgi(),
                                        response.body().getInt(), response.body().getLuc());
                                MainActivity.prefConfig.displayToast("ВАНДУФЛ" + response.body().getStr() +
                                        response.body().getAgi() +
                                        response.body().getInt() + response.body().getLuc() + " " + response.body().getPoint());
                            } else if (response.code() == 500) {
                                MainActivity.prefConfig.displayToast("Server Error");
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable throwable) {

                        }
                    });
                    for (int i = 0; i < 4; i++) {
                        gg.get(i).setCount(newHeroStat.get(i).getCount());
                    }
                }else
                    MainActivity.prefConfig.displayToast("Нет изменений");
            }
        });

        weapon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                for (int g = 0; g < hero.getOnEquip().size(); g++) {

                    if (hero.getOnEquip().get(g) instanceof Equipment.Weapon) {
                        hero.removeEquip(hero.getOnEquip().get(g));

                        i = 1;
                        g = hero.getOnEquip().size();
                    }
                    //запрос на снятие шмотки(получение данных о персонаже)/////////////////////////////////////////////////////////////////////////////////////
                    weapon.setImageResource(R.drawable.ic_colorize_gray);
                    myProfileStatAdapter.notifyDataSetChanged();
                }
                if (i == 0) {
                    test.onEquipItem("Weapon");
                }

            }

        });
        armour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                for (int g = 0; g < hero.getOnEquip().size(); g++) {

                    if (hero.getOnEquip().get(g) instanceof Equipment.Armour) {
                        hero.removeEquip(hero.getOnEquip().get(g));

                        i = 1;
                        g = hero.getOnEquip().size();
                    }
                    //запрос на снятие шмотки(получение данных о персонаже)/////////////////////////////////////////////////////////////////////////////////////
                    armour.setImageResource(R.drawable.ic_security_gray);
                    myProfileStatAdapter.notifyDataSetChanged();
                }
                if (i == 0) {
                    test.onEquipItem("Armour");
                }

            }
        });

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
        list_My_Stat.setLayoutManager(layoutManager);
        myProfileStatAdapter = new MyProfileStatAdapter(hero, getPoint, newHeroStat, gg,0);
        myProfileStatAdapter.setOnPorofileClicListener(this);
        list_My_Stat.setAdapter(myProfileStatAdapter);
    }

    @Override

    public void onItemStatClick(View view, ArrayList<HeroStat> buffer) {
        this.buffer = buffer;
    }

}

