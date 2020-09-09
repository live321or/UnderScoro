package com.samoylov.gameproject.locations.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.R;
import com.samoylov.gameproject.character.heroes.Hero;
import com.samoylov.gameproject.character.mobs.Mob;
import com.samoylov.gameproject.group.GroupViewHolderFactory;

import java.util.ArrayList;

public class MobAdapter extends RecyclerView.Adapter<MobAdapter.ViewHolder> {
    ArrayList<Mob> mobs;
    int size;
    public interface Update {
        void infoMob(Mob mob);
        void goBattleMob(Mob mob);
    }

    private static Update onUpdate;

    public void setOnUpdateListener(Update listener) {
        onUpdate = listener;
    }
    public MobAdapter(ArrayList<Mob> mobs,int size) {
    this.mobs=mobs;
    this.size=size;
    }
    public void setSize(int size){
        this.size=size;
    }
    public void setContent(ArrayList<Mob> mobs){
        this.mobs=mobs;
        size=mobs.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View invite= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expanded_mob_item,parent,false);
        return new ViewHolder(invite);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textChild.setText(mobs.get(position).getName());
        holder.hp.setText(""+mobs.get(position).getHp());
        holder.dmg.setText(""+mobs.get(position).getDmg());
        holder.button.setOnClickListener(v -> {
            onUpdate.goBattleMob(mobs.get(position));
        });
        holder.itemView.setOnClickListener(v -> {
            onUpdate.infoMob(mobs.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textChild;
        TextView hp;
        TextView dmg;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textChild = (TextView) itemView.findViewById(R.id.textMob);
            hp = (TextView) itemView.findViewById(R.id.textMobHp);
            dmg = (TextView) itemView.findViewById(R.id.textMobDmg);
            button = (Button) itemView.findViewById(R.id.bAttackMob);
        }
    }
}
