package com.samoylov.gameproject.character.profile.adapers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.R;
import com.samoylov.gameproject.items.Equipment;

import java.util.ArrayList;

public class BagAdapter extends RecyclerView.Adapter<BagAdapter.ViewHolder> {
    private ArrayList<Equipment> equipment;
    private ArrayList<Equipment.Weapon> weapons;
    private ArrayList<Equipment.Armour> armours;
    String tag;
    private ItemClick mItemClick;


    public BagAdapter(ArrayList<Equipment> equipment, String tag) {
        this.equipment = equipment;
        this.tag = tag;
    }

    public interface ItemClick {
        void onItemClik(int pos);

        void onDeleteClick(int position);
    }

    public void setItemClick(ItemClick click) {
        mItemClick = click;
    }

    

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_item, viewGroup, false);

        return new ViewHolder(view, mItemClick);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Equipment currentItem = equipment.get(i);
        viewHolder.mImageView.setImageResource(currentItem.getmImageResource());
//        viewHolder.mTextView1.setText("0");
        viewHolder.mTextView1.setText(currentItem.getName() + " Id: " + Double.toString(currentItem.getId()));
        if (currentItem instanceof Equipment.Weapon) {
            double dmg = ((Equipment.Weapon) currentItem).getDmg();
//            viewHolder.mTextView2.setText("1");
            viewHolder.mTextView2.setText("Урон: " + Double.toString(dmg));
        }
        if (currentItem instanceof Equipment.Armour) {
            double dmg = ((Equipment.Armour) currentItem).getArmor();
//            viewHolder.mTextView2.setText("2");
            viewHolder.mTextView2.setText(((Equipment.Armour) currentItem).getTypeArmour() + "Броня: " + Double.toString(dmg));
        }
    }

    @Override
    public int getItemCount() {
        return equipment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public ImageView mDeleteView;

        public TextView mTextView1;
        public TextView mTextView2;

        public ViewHolder(@NonNull View itemView, final ItemClick click) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView1);
            mTextView2 = itemView.findViewById(R.id.textView2);

            mDeleteView = itemView.findViewById(R.id.delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (click != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            click.onItemClik(pos);
                        }
                    }
                }
            });
            mDeleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (click != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            click.onDeleteClick(pos);
                        }
                    }
                }
            });

        }
    }
}

