package com.samoylov.gameproject.group;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.Data;
import com.samoylov.gameproject.character.heroes.Hero;

import java.util.ArrayList;
import java.util.List;

public class AdapterGroupList extends RecyclerView.Adapter {

    private ArrayList<Integer> groupList;
    private boolean isLider, isGroup;

    public interface Update {
        void onUpdate(int tag);
    }

    private static Update onUpdate;

    public AdapterGroupList(ArrayList<Integer> dataSet, boolean a, boolean isGroup) {
        this.groupList = dataSet;
        this.isLider = a;
        this.isGroup = isGroup;
    }

    public void setOnUpdateListener(Update listener) {
        onUpdate = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return GroupViewHolderFactory.create(parent, isGroup, isLider);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (!isGroup) {
            GroupViewHolderFactory.InviteHolder inviteHolder =
                    (GroupViewHolderFactory.InviteHolder) viewHolder;

            inviteHolder.name.setText(Data.bdHeros.get(groupList.get(position)).getName());
            inviteHolder.yes.setOnClickListener(v -> {
                Data.bdInvite.remove(position);
                Data.bdHeros.get(0).setGroup(true);
                Data.GroupList.add(Data.bdHeros.get(1).getPosition());
                Data.GroupList.add(Data.bdHeros.get(0).getPosition());
                onUpdate.onUpdate(1);
            });
            inviteHolder.no.setOnClickListener(v -> {
                Data.bdInvite.remove(position);
                onUpdate.onUpdate(0);
            });
        } else if (!isLider) {
            GroupViewHolderFactory.Ne_Lider_Holder holder =
                    (GroupViewHolderFactory.Ne_Lider_Holder) viewHolder;
            holder.name.setText(Data.bdHeros.get(groupList.get(position)).getName());
            holder.hp.setText("" + Data.bdHeros.get(groupList.get(position)).getHp());


        } else {
            GroupViewHolderFactory.Lider_Holder liderHolder =
                    (GroupViewHolderFactory.Lider_Holder) viewHolder;
            liderHolder.name.setText(Data.bdHeros.get(groupList.get(position)).getName());
            liderHolder.hp.setText("" + Data.bdHeros.get(groupList.get(position)).getHp());
            liderHolder.gKikL.setOnClickListener(v -> {


                Data.bdHeros.get(groupList.get(position)).setGroup(false);
                Data.GroupList.remove(position);

                onUpdate.onUpdate(0);


            });

        }
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }


}
