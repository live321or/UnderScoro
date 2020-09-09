package com.samoylov.gameproject.nextversion;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class MobList implements RowType {
    private String mob;
    private Context context;

    public MobList(String mob, Context context){
        this.mob=mob;
        this.context=context;
    }
    public View.OnClickListener getOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Click mob!", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
