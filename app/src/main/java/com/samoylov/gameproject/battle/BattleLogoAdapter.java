package com.samoylov.gameproject.battle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.R;

import java.util.ArrayList;

public class BattleLogoAdapter extends RecyclerView.Adapter<BattleLogoAdapter.ViewHolder>{
    private ArrayList<BattleFragment.LogoText> logoTexts;

    public BattleLogoAdapter(ArrayList<BattleFragment.LogoText> logoTexts) {
        this.logoTexts = logoTexts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View v=inflater.inflate(R.layout.battle_logo_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.nLog.setText(logoTexts.get(i).getnLogo());
        viewHolder.tLog.setText(logoTexts.get(i).gettLogo());
    }

    @Override
    public int getItemCount() {
        return logoTexts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nLog, tLog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nLog = itemView.findViewById(R.id.nLogo);
            tLog = itemView.findViewById(R.id.tLogo);
        }
    }
}
