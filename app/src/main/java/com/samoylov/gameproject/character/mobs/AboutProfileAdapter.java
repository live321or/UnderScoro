package com.samoylov.gameproject.character.mobs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.character.heroes.HeroStat;
import com.samoylov.gameproject.R;

import java.util.ArrayList;

public class AboutProfileAdapter extends RecyclerView.Adapter<AboutProfileAdapter.ViewHolder> {
    private ArrayList<HeroStat> heroStats;

    public AboutProfileAdapter(ArrayList<HeroStat> heroStats) {
        this.heroStats = heroStats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int listId = R.layout.item_profile;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(listId, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameStat.setText(heroStats.get(position).getName());
        holder.resultStat.setText(Double.toString(heroStats.get(position).getCount()));
    }

    @Override
    public int getItemCount() {
        return heroStats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameStat, resultStat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameStat = (TextView) itemView.findViewById(R.id.nameStat);
            resultStat = (TextView) itemView.findViewById(R.id.resultStat);
        }
    }
}
