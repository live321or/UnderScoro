package com.samoylov.gameproject.character.profile.adapers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.character.heroes.Hero;
import com.samoylov.gameproject.character.heroes.HeroStat;
import com.samoylov.gameproject.R;

import java.util.ArrayList;

public class MyProfileStatAdapter extends RecyclerView.Adapter<MyProfileStatAdapter.ViewHolder> {
    public interface OnPorofileClicListener {
        void onItemStatClick(View view, ArrayList<HeroStat> buffer);
    }

    private static OnPorofileClicListener mProfListener;
    ArrayList<HeroStat> heroStats;
    ArrayList<HeroStat> newHeroStat;
    ArrayList<HeroStat> buffer = new ArrayList<HeroStat>();
    Hero hero;
    //    HeroStat newHeroStat;
    private double pPoint, g;
    TextView getPoint;
    double mPoint = 0;
    int k;
    public MyProfileStatAdapter(Hero hero, TextView getPoint, ArrayList<HeroStat> newHeroStat, ArrayList<HeroStat> heroStats,int k) {
        this.hero = hero;
        this.getPoint = getPoint;
        this.newHeroStat = newHeroStat;
        this.heroStats = heroStats;
        this.k=k;
        setBuffer(this.buffer);
        this.pPoint = hero.getPoint();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int listId;

            listId=R.layout.my_info_stat;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(listId, parent, false);
        return new ViewHolder(v,k);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.nameMyStat.setText(heroStats.get(position).getName());
        holder.resultMyStat.setText(Double.toString(heroStats.get(position).getCount()));


        final double[] d = {heroStats.get(position).getCount()};
        //Отнимаем сатыты
        holder.mStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Запрсос с отправкой позицией ////////////////////////////////////////////////////////////////////////////////////////////
                if (buffer.get(position).getCount() != heroStats.get(position).getCount()) {
                    d[0] = d[0] - 1;
                    buffer.get(position).setCount(d[0]);
                    holder.resultMyStat.setText(Double.toString(buffer.get(position).getCount()));
//                    mPoint--;
                    pPoint++;
//                    hero.setPoint(pPoint);
                    getPoint.setText(Double.toString(pPoint));
                    mProfListener.onItemStatClick(view, buffer);
                }
            }
        });
        //Прибовляем статы
        holder.pStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Запрсос с отправкой позицией////////////////////////////////////////////////////////////////////////////////////////////////
                if (pPoint != 0) {
                    d[0] = d[0] + 1;

                    buffer.get(position).setCount(d[0]);
                    holder.resultMyStat.setText(Double.toString(buffer.get(position).getCount()));
                    pPoint--;
//                    mPoint++;
//                    hero.setPoint(pPoint);
                    getPoint.setText(Double.toString(pPoint));
                    mProfListener.onItemStatClick(view, buffer);
                }
            }
        });
    }


    public void setOnPorofileClicListener(OnPorofileClicListener listener) {
        mProfListener = listener;
    }

    @Override
    public int getItemCount() {
        return heroStats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameMyStat, resultMyStat, costStat;
        private Button mStat, pStat;

        public ViewHolder(@NonNull View itemView,int k) {
            super(itemView);
            if (k==0) {
                nameMyStat = (TextView) itemView.findViewById(R.id.nameMyStat);
                resultMyStat = (TextView) itemView.findViewById(R.id.resultMyStat);
                costStat = (TextView) itemView.findViewById(R.id.costStat);

                mStat = (Button) itemView.findViewById(R.id.mMStat);
                pStat = (Button) itemView.findViewById(R.id.pMStat);
            }else {
                nameMyStat = (TextView) itemView.findViewById(R.id.nameStat);
                resultMyStat = (TextView) itemView.findViewById(R.id.resultStat);
            }

        }

    }


    public void setBuffer(ArrayList<HeroStat> buffer) {

        for (int i = 0; i < 4; i++) {
            buffer.add(new HeroStat(newHeroStat.get(i).getName(), newHeroStat.get(i).getCount()));

        }
    }
}
