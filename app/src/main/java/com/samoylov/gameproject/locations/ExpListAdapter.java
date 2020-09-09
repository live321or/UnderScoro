package com.samoylov.gameproject.locations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.samoylov.gameproject.Data;
import com.samoylov.gameproject.R;
import com.samoylov.gameproject.nextversion.RowType;

import java.util.ArrayList;

public class ExpListAdapter extends BaseExpandableListAdapter {
    public interface OnCardClickListener {
        void onCardClick(String name, int pos, String tag);

        void onClickBattle(int id, int tag);
    }

    private static OnCardClickListener mListener;
    private ArrayList<ArrayList<String>> mGroups;
    private Context mContext;

    private ArrayList<ArrayList<RowType>> dataSet;

    public ExpListAdapter(Context context, ArrayList<ArrayList<String>> groups, ArrayList<ArrayList<RowType>> dataSet) {
        this.mContext = context;
        mGroups = groups;
        this.dataSet = dataSet;
    }

//    @Override
//    public int getChildType(int groupPosition, int childPosition) {
//
//        if (dataSet.get(groupPosition).get(childPosition) instanceof Transitions) {
//            return RowType.Transitions;
//        } else if (dataSet.get(groupPosition).get(childPosition) instanceof PlayersList) {
//            return RowType.PlayersList;
//        } else if (dataSet.get(groupPosition).get(childPosition) instanceof MobList) {
//            return RowType.MobList;
//        } else return -1;
//    }


    @Override
    public int getGroupCount() {
        return mGroups.size() - 2;
//        return dataSet.size() - 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
//        return dataSet.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
//        return dataSet.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
//        return dataSet.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_view, null);
        }

        if (isExpanded) {
            //Изменяем что-нибудь, если текущая Group_Fragment раскрыта
        } else {
            //Изменяем что-нибудь, если текущая Group_Fragment скрыта
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
        textGroup.setText(mGroups.get(3).get(groupPosition));
//        if(dataSet.get(1).get(groupPosition) instanceof GroupList) {
//        textGroup.setText(((GroupList) dataSet.get(1).get(groupPosition)).getGroupName());
//        }

        return convertView;
    }

    public void setOnCardClickListener(OnCardClickListener listener) {
        mListener = listener;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        ViewHolder0 viewHolder0 = null;
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;


        if (groupPosition == 0) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expanded_navigation_item, null);
            viewHolder0 = new ViewHolder0(convertView);
            convertView.setTag(viewHolder0);

        } else if (groupPosition == 1) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expanded_players_item, null);
            viewHolder1 = new ViewHolder1(convertView);
            convertView.setTag(viewHolder1);
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expanded_mob_item, null);
            viewHolder2 = new ViewHolder2(convertView);
            convertView.setTag(viewHolder2);
        }


//        TextView textChild = (TextView) convertView.findViewById(R.id.textChild);
        if (groupPosition == 0) {
//            viewHolder0.textChild.setText(mGroups.get(groupPosition).get(childPosition));
            viewHolder0.textChild.setText((mGroups.get(groupPosition).get(childPosition)));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onCardClick((mGroups.get(groupPosition).get(childPosition)), 0, null);
                }
            });
        } else if (groupPosition == 1) {
            viewHolder1.textChild.setText(mGroups.get(groupPosition).get(childPosition));
//            viewHolder1.textChild.setText(dataSet.get(groupPosition).get(childPosition).gw);
            viewHolder1.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onCardClick(mGroups.get(groupPosition).get(childPosition), groupPosition, null);
                }
            });
        } else {
            viewHolder2.textChild.setText(mGroups.get(groupPosition).get(childPosition));
            for(int i=0;i<Data.bdMob.size();i++) {
                if (Data.bdMob.get(i).getId() == Integer.parseInt(mGroups.get(4).get(childPosition))) {

                    viewHolder2.hp.setText("Hp: " + Data.bdMob.get(i).getHp_now());
                    viewHolder2.dmg.setText("Урон: " + Data.bdMob.get(i).getDmg());
                    i=Data.bdMob.size();
                }
            }
//        viewHolder2.textChild.setText(dataSet.get(groupPosition).get(childPosition));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClickBattle(Integer.parseInt(mGroups.get(4).get(childPosition)), 0);
                }
            });
            viewHolder2.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    mListener.onCardClick(view,mGroups.get(groupPosition).get(childPosition),groupPosition,"Mob");
                    Toast.makeText(mContext, mGroups.get(4).get(childPosition), Toast.LENGTH_SHORT).show();
                    mListener.onClickBattle(Integer.parseInt(mGroups.get(4).get(childPosition)), 1); //Напасть на монстра (Передача айди)

                }
            });
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public class ViewHolder {

    }


    private class ViewHolder0 {
        final TextView textChild;

        ViewHolder0(View view) {
            textChild = (TextView) view.findViewById(R.id.textNav);
        }
    }

    private class ViewHolder1 {
        final TextView textChild;
        final Button button;

        ViewHolder1(View view) {
            textChild = (TextView) view.findViewById(R.id.textPlayer);
            button = (Button) view.findViewById(R.id.bInfoPlayer);
        }
    }

    private class ViewHolder2 {
         TextView textChild;
        TextView hp;
        TextView dmg;
         Button button;

        ViewHolder2(View view) {
            textChild = (TextView) view.findViewById(R.id.textMob);
            hp = (TextView) view.findViewById(R.id.textMobHp);
            dmg = (TextView) view.findViewById(R.id.textMobDmg);
            button = (Button) view.findViewById(R.id.bAttackMob);
        }
    }

}

