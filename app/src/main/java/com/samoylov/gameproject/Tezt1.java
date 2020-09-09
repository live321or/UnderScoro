package com.samoylov.gameproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.character.profile.adapers.BagAdapter;

public class Tezt1 extends RecyclerView.Adapter<Tezt1.ViewHolder>{
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public ImageView mDeleteView;
        public ImageView mOnOf;
        public TextView mTextView1;
        public TextView mTextView2;

        public ViewHolder(@NonNull View itemView, final BagAdapter.ItemClick click) {
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
