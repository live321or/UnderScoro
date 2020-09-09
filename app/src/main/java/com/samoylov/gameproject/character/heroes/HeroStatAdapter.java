package com.samoylov.gameproject.character.heroes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.samoylov.gameproject.R;
import com.samoylov.gameproject.character.heroes.Hero;
import com.samoylov.gameproject.character.heroes.HeroStat;

import java.util.ArrayList;

public class HeroStatAdapter extends BaseAdapter {
    public ArrayList<HeroStat> list,newHeroStat;
    private LayoutInflater inflater;
    Hero hero;
//    HeroStat newHeroStat;
    private double pPoint,g;
    TextView getPoint;
    double mPoint=0;


    public HeroStatAdapter(Context context, Hero hero,TextView getPoint,ArrayList<HeroStat> newHeroStat) {
        this.hero=hero;
        this.getPoint=getPoint;
        this.newHeroStat=newHeroStat;
        this.list = hero.getHeroStats();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.pPoint=hero.getPoint();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View v = view;
//        if (v == null) {
            v = inflater.inflate(R.layout.stat_item, viewGroup, false);
//        }
        HeroStat heroStat = getHaroStat(i);
        final double[] d = {heroStat.getCount()};
        g=list.get(i).getCount();
        final TextView name = (TextView) v.findViewById(R.id.nStat);
        final TextView gname = (TextView) v.findViewById(R.id.gStat);
        final TextView gG = (TextView) v.findViewById(R.id.g);
        name.setText(list.get(i).getName());
        gname.setText(Double.toString(list.get(i).getCount()));
        gG.setText(Double.toString(g));

        Button mStat = (Button) v.findViewById(R.id.mStat);
        Button pStat = (Button) v.findViewById(R.id.pStat);

g=list.get(i).getCount();
        mStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newHeroStat.get(i).getCount() != hero.getHeroStats().get(i).getCount()) {
                    d[0] = d[0] - 1;
                    g=g-1;
                    gG.setText(Double.toString(g));
                    newHeroStat.get(i).setCount(d[0]);
                    gname.setText(Double.toString( newHeroStat.get(i).getCount()));
//                    gname.setText(Double.toString(d[0]));
                    mPoint--;
                    pPoint++;
                    hero.setPoint(pPoint);
                    getPoint.setText(Double.toString(hero.getPoint()));
//                    getPoint.setText(Double.toString(newHeroStat.get(i).getCount()));
                }
            }
        });
        pStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pPoint!=0) {
                    d[0] = d[0] + 1;
                    g=list.get(i).getCount()+1;
                    gG.setText(Double.toString(g));
                    newHeroStat.get(i).setCount(d[0]);
                    gname.setText(Double.toString( newHeroStat.get(i).getCount()));
                    name.setText(Double.toString( hero.getHeroStats().get(i).getCount()));
                    pPoint--;
                    mPoint++;
                    //hero.setPoint(pPoint);
                    hero.setPoint(pPoint);
                    getPoint.setText(Double.toString(hero.getPoint()));
                }

            }
        });

        return v;
    }

    private HeroStat getHaroStat(int i) {
        return (HeroStat) getItem(i);
    }

}
