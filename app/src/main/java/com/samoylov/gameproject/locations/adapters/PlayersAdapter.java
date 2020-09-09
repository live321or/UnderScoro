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
import com.samoylov.gameproject.locations.LocationSettings;

import java.util.ArrayList;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {
    ArrayList<Hero> players;
    int size;

    public interface Update {
        void onUpdatePlayer(Hero player);
    }

    private static Update onUpdate;

    public void setOnUpdateListener(Update listener) {
        onUpdate = listener;
    }

    public PlayersAdapter(ArrayList<Hero> players, int size) {
        this.players = players;
        this.size = size;
    }
    public void setSize(int size){
        this.size=size;
    }
    public void setContent(ArrayList<Hero> players){
        this.players=players;
        size=players.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View invite = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expanded_players_item, parent, false);
        return new ViewHolder(invite);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textChild.setText(players.get(position).getName());
        holder.button.setOnClickListener(v -> {
            onUpdate.onUpdatePlayer(players.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textChild;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textChild = (TextView) itemView.findViewById(R.id.textPlayer);
            button = (Button) itemView.findViewById(R.id.bInfoPlayer);
        }
    }
}