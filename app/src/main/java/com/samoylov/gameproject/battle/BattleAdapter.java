package com.samoylov.gameproject.battle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.character.heroes.Hero;
import com.samoylov.gameproject.Person;
import com.samoylov.gameproject.R;

public class BattleAdapter extends RecyclerView.Adapter<BattleAdapter.ViewHolder1> {
    private Person person;
    int k;
    public BattleAdapter (Person person){
        this.person = person;
        if (person instanceof Hero){
            this.k=0;
        }else {
            this.k=1;
        }
    }
    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int itemId;
        if(k==0){
            itemId=R.layout.hero_item;
        }else {
            itemId=R.layout.enemy_item;
        }
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View v=inflater.inflate(itemId,viewGroup,false);
        return new ViewHolder1(v,k);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 viewHolder, int i) {
        viewHolder.name.setText(person.getName());
        viewHolder.hp_now.setText(""+ person.getHp_now());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public  class ViewHolder1 extends RecyclerView.ViewHolder {
        TextView name, hp_now;
        public ViewHolder1(@NonNull View itemView,int k) {
            super(itemView);
            if (k==0){
                name=itemView.findViewById(R.id.nHero);
                hp_now=itemView.findViewById(R.id.hpHero);
            }else if (k==1){
                name=itemView.findViewById(R.id.nEnemy);
                hp_now=itemView.findViewById(R.id.hpEnemy);
            }
        }
    }
}
