package com.samoylov.gameproject.nextversion;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.samoylov.gameproject.R;

public class ViewHolderFactory {
    private static class ViewHolder0{
        final TextView textChild;
        ViewHolder0(View view){
            textChild=(TextView)view.findViewById(R.id.textNav);
        }
    }
    private class ViewHolder1{
        final TextView textChild;
        final Button button;
        ViewHolder1(View view){
            textChild=(TextView)view.findViewById(R.id.textPlayer);
            button=(Button)view.findViewById(R.id.bInfoPlayer);
        }
    }
    private class ViewHolder2{
        final TextView textChild;
        final Button button;
        ViewHolder2(View view){
            textChild=(TextView)view.findViewById(R.id.textMob);
            button=(Button)view.findViewById(R.id.bAttackMob);
        }
    }

    public static  View getChildView(final int groupPosition, final int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        ViewHolder0 viewHolder0;
        ViewHolder1 viewHolder1;
        ViewHolder2 viewHolder2;
//        if (convertView == null) {
//            switch (childPosition)
//            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.child_view, null);
//            viewHolder=new ExpListAdapter.ViewHolder(convertView);
//            convertView.setTag(viewHolder);
//        }else {
//            viewHolder=(ExpListAdapter.ViewHolder)convertView.getTag();
//        }

        return convertView;
    }
}
