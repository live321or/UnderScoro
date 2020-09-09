package com.samoylov.gameproject.group;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.R;

public class GroupViewHolderFactory {
    public static class Ne_Lider_Holder extends RecyclerView.ViewHolder {
        TextView name,hp;
        public Ne_Lider_Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nGHero);
            hp=itemView.findViewById(R.id.hpGHero);
        }
    }

    public static class Lider_Holder extends RecyclerView.ViewHolder {
        TextView name,hp;
        Button gLider,gKikL;
        public Lider_Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nLHero);
            hp=itemView.findViewById(R.id.hpLHero);
            gLider=itemView.findViewById(R.id.gLider);
            gKikL=itemView.findViewById(R.id.gKikL);
        }
    }

    public static class InviteHolder extends RecyclerView.ViewHolder{
        TextView name,hp;
        Button yes,no;
        public InviteHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.inviteHeroName);
            yes=itemView.findViewById(R.id.inviteYes);
            no=itemView.findViewById(R.id.inviteNo);
        }
    }

    public static RecyclerView.ViewHolder create(ViewGroup parent, boolean isGroup,boolean isLider) {
        if (!isGroup){
            View invite=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.group_ivite_item,parent,false);
            return new InviteHolder(invite);
        } else if (!isLider) {

            View neLider = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.group_item, parent, false);
            return new Ne_Lider_Holder(neLider);
        } else if(isLider) {

            View lider = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.group_lider_item, parent, false);
            return new Lider_Holder(lider);
        }else
            return null;
    }

}
