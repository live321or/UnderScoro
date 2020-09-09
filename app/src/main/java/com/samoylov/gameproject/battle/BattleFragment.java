package com.samoylov.gameproject.battle;

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
import com.samoylov.gameproject.character.profile.MyBagFragment;
import com.samoylov.gameproject.locations.FragmentLocation;
import com.samoylov.gameproject.MainActivity;
import com.samoylov.gameproject.character.mobs.Mob;
import com.samoylov.gameproject.Person;
import com.samoylov.gameproject.R;

import com.samoylov.gameproject.authorization.User;
import com.samoylov.gameproject.locations.StatusBarFragment;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BattleFragment extends Fragment {
    private RecyclerView heroList, enemyList, logoRV;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.LayoutManager manager2;
    private RecyclerView.LayoutManager manager3;
    private BattleAdapter heroBattleAdapter, enemyBattleAdapter, bagAdapter;
    private MyBagFragment mybagfragment;
    private BattleLogoAdapter battleLogoAdapter;
    private Mob enemy;
    private Hero hero;

    private Button attack;
    private ArrayList<LogoText> logoTexts = new ArrayList<LogoText>();

//    public Timer timer_regen = new Timer();
//    public TimerTask task_regen = new TimerTask() {
//        @Override
//        public void run() {
//            if (Data.bdHeros.get(0).getHp_now() <= Data.bdHeros.get(0).getHp()) {
//                Data.bdHeros.get(0).setHp_now(Data.bdHeros.get(0).getHp_now() + 1);
//            }
//        }
//    };
//    private long regen_delay = 1000L;
//    private long regen_period = 1000L;

    public BattleFragment() {
        // Required empty public constructor
    }

    public static BattleFragment newInstance(Hero hero, Mob mob) {
        BattleFragment fragment = new BattleFragment();
        fragment.hero = hero;
        fragment.enemy = mob;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_battle, container, false);
        manager = new LinearLayoutManager(getActivity());
        manager2 = new LinearLayoutManager(getActivity());
        manager3 = new LinearLayoutManager(getActivity());

        heroList = view.findViewById(R.id.heroList);
        enemyList = view.findViewById(R.id.enemyList);
        logoRV = view.findViewById(R.id.logList);

        attack = view.findViewById(R.id.bAttack);
        attack.setText("Атаковать");

        heroList.setLayoutManager(manager);
        enemyList.setLayoutManager(manager2);
        logoRV.setLayoutManager(manager3);

        logoTexts.add(0, new LogoText("Вы напали на " + enemy.getName() + "!", ""));

        heroBattleAdapter = new BattleAdapter(hero);
        enemyBattleAdapter = new BattleAdapter(enemy);

//        bagAdapter = new BagAdapter();
        ///////////////////////Инвентарь, добавление ножика, бошка гудит
//        mybagfragment = new MyBagFragment();
        battleLogoAdapter = new BattleLogoAdapter(logoTexts);
        heroList.setAdapter(heroBattleAdapter);
        enemyList.setAdapter(enemyBattleAdapter);

        logoRV.setAdapter(battleLogoAdapter);

        attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //атака/////////////////////////////////////////////////////////////////////////////////////////////
                //получаю дамаг игрока и монстра
                //если хп >0 тогда бой продолжается иначе
                //конец боя//
                //запрос результат////////////////////////////////////////////////////////////////////////////////////
//Поменял 10.05
                battle();
            }
        });
        return view;
    }

    public void battle() {
        hod(enemy, hero);
        heroBattleAdapter.notifyDataSetChanged();
        enemyBattleAdapter.notifyDataSetChanged();
        if (enemy.getHp_now() <= 0) {
            Data.bdMob.remove(enemy);
//            hero.setEXP(hero.getEXP() + enemy.getEXP());

//            mybagfragment.AddKnife();
//            mybagfragment.buildBag();
//            hero.addItemOnInventory(new Equipment.Weapon("Нож", R.drawable.ic_colorize_black,
//                    0, 0, 1, 0, 0, 5));
            if (enemy.getDropItem() == null) {
                logoTexts.add(0, new LogoText("Лох", "Без шмотки остался"));
            } else {
//                logoTexts.add(1, new LogoText("Получено", "Шмотка" + enemy.getDropItem().getName()));
                hero.addItemOnInventory(enemy.getDropItem());
            }

            logoTexts.add(0, new LogoText("Получено: ", "Опыта: " + enemy.getEXP()));
            logoTexts.add(0, new LogoText("Вы победили" + enemy.getName() + " повержен!", ""));

            battleLogoAdapter.notifyDataSetChanged();
            attack.setText("Продолжить");
            attack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<User> call= MainActivity.apiInterface.addExp(MainActivity.prefConfig.readCookie(),enemy.getEXP());
//                    Call<User> call= MainActivity.apiInterface.your_name(Data.cookie);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.code() == 200) {
                                Data.bdHeros.get(0).setEXP(response.body().getEXP());
                                Data.bdHeros.get(0).setPoint(response.body().getPoint());
                                Data.bdHeros.get(0).setLvl(response.body().getLvl());
                                MainActivity.prefConfig.displayToast("ВАНДУФЛ " + response.body().getEXP()+" " + response.body().getPoint()+" "+response.body().getLvl());
                            }else if(response.code() == 500){
                                MainActivity.prefConfig.displayToast("Server Error");
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable throwable) {

                        }
                    });
                    StatusBarFragment statusBarFragment=StatusBarFragment.newInstance(Data.bdHeros.get(0));
                    getActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.containerFragments, statusBarFragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().
                            add(R.id.containerFragments, new FragmentLocation()).commit();
                }
            });
        }else {

            heroBattleAdapter.notifyDataSetChanged();
            enemyBattleAdapter.notifyDataSetChanged();
            hod(hero, enemy);
        }

        if (hero.getHp_now() <= 0) {
            //enemy.getLvl() (Сделать увелечение лвла)
            logoTexts.add(0, new LogoText("Вас победил " + enemy.getName() + "!", ""));
            battleLogoAdapter.notifyDataSetChanged();
            attack.setText("Продолжить");
            attack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatusBarFragment statusBarFragment=StatusBarFragment.newInstance(Data.bdHeros.get(0));
                    getActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.containerFragments, statusBarFragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().
                            add(R.id.containerFragments, new FragmentLocation()).commit();
                }
            });
        }
        heroBattleAdapter.notifyDataSetChanged();
        hero.UpLvl();
    }

    private void hod(Person defending, Person attacking) {
        if (Math.random() * 100 <= attacking.getAcc()) {//попадание
            if (Math.random() * 100 <= attacking.getCritChance()) {//Шанс крита
                double Crit = attacking.getCritPower();
                double dmg = attacking.getDmg();
                defending.setHp_now(defending.getHp_now() - dmg * Crit);//Крит прошел, расчет урона с крита
                logoTexts.add(0, new LogoText(attacking.getName(), "Кританул и нанес " + (dmg * Crit) + " урона"));
                battleLogoAdapter.notifyDataSetChanged();
            } else {
                double dmg = attacking.getDmg();
                defending.setHp_now(defending.getHp_now() - dmg);//крит не прошел, расчет урон без крита
                logoTexts.add(0, new LogoText(attacking.getName(), "Нанес " + dmg + " урона"));
                battleLogoAdapter.notifyDataSetChanged();
            }
        } else {
            logoTexts.add(0, new LogoText(attacking.getName(), "Промазал"));
            battleLogoAdapter.notifyDataSetChanged();
        }
    }

    public static class LogoText {
        private String nLogo, tLogo;

        public LogoText(String nLogo, String tLogo) {
            this.nLogo = nLogo;
            this.tLogo = tLogo;
        }

        public String getnLogo() {
            return nLogo;
        }

        public String gettLogo() {
            return tLogo;
        }
    }
}