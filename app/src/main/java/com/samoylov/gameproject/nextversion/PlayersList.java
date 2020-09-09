package com.samoylov.gameproject.nextversion;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class PlayersList implements RowType {
    private String player;
    private Context context;

    public PlayersList(String player, Context context){
        this.player=player;
        this.context=context;
    }
    public View.OnClickListener getOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
