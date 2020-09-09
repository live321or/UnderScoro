package com.samoylov.gameproject.locations.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.Data;
import com.samoylov.gameproject.R;
import com.samoylov.gameproject.character.heroes.Hero;
import com.samoylov.gameproject.group.AdapterGroupList;
import com.samoylov.gameproject.locations.LocationSettings;

import java.util.ArrayList;

public class TransitionAdapter extends RecyclerView.Adapter<TransitionAdapter.ViewHolder> {
    ArrayList<LocationSettings> transitions;
    int size;
    public interface Update {
        void onUpdate();
    }

    private static Update onUpdate;

    public void setOnUpdateListener(Update listener) {
        onUpdate = listener;
    }

    public TransitionAdapter(ArrayList<LocationSettings> transitions,int size) {
        this.transitions =transitions;
        this.size=size;
    }
    public void setSize(int size){
        this.size=size;
    }
    public void setContent(ArrayList<LocationSettings> transitions){
        this.transitions=transitions;
        size=transitions.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View invite= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expanded_navigation_item,parent,false);
        return new ViewHolder(invite);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.textChild.setText(transitions.get(position).getLocName());
    holder.itemView.setOnClickListener(v -> {
        Data.bdHeros.get(0).setLocation1(transitions.get(position));
        onUpdate.onUpdate();

    });
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textChild;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textChild = (TextView) itemView.findViewById(R.id.textNav);
        }
    }
}