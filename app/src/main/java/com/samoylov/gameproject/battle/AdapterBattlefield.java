package com.samoylov.gameproject.battle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samoylov.gameproject.Data;
import com.samoylov.gameproject.Person;
import com.samoylov.gameproject.R;
import com.samoylov.gameproject.character.mobs.Mob;

import java.util.ArrayList;

public class AdapterBattlefield extends RecyclerView.Adapter {
    private ArrayList<Battlefield> battlefield;
    int playerPosition;
    Person player, enemy;

    public interface BattleListener {
        void onUpdate();

        void onAttack();

        void onAttackEnemy();
    }

    private static BattleListener onUpdate;

    public AdapterBattlefield(ArrayList<Battlefield> battlefield) {
        this.battlefield = battlefield;
        this.player = battlefield.get(27).getAction();
        this.enemy = battlefield.get(7).getAction();
    }

    public void setBattleListener(BattleListener listener) {
        onUpdate = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return battlefield.get(position).getItemViewType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case Battlefield.BOX_EMPTY:
                View boxEmpty = LayoutInflater.from(parent.getContext()).inflate(R.layout.box_empty, parent, false);
                return new EmptyHolder(boxEmpty);

            case Battlefield.BOX_PLAYER:
                View textTypeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.box_player, parent, false);
                return new PlayerHolder(textTypeView);

            case Battlefield.BOX_ENEMY:
                View imageTypeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.box_enemy, parent, false);
                return new EnemyHolder(imageTypeView);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyHolder) {

            int x = Data.bdHeros.get(0).getX(), y = player.getY();
            ((EmptyHolder) holder).pos.setText("" + battlefield.get(position).getX() + "," + battlefield.get(position).getY());
            holder.itemView.setOnClickListener(v -> {
//                Toast.makeText(holder.itemView.getContext(), "Position: " + holder.getLayoutPosition()+" Position Player: "+Data.bdHeros.get(0).getPositionBattle(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(holder.itemView.getContext(), "x: " + battlefield.get(position).getX()+" Y: "+battlefield.get(position).getY(), Toast.LENGTH_SHORT).show();
                if (battlefield.get(position).getFlag(position, player.getPositionBattle())) {
                    player.setXY(battlefield.get(position).getX(), battlefield.get(position).getY());
                    battlefield.set(position, battlefield.get(player.getPositionBattle()));
                    battlefield.set(player.getPositionBattle(), new BoxEmpty());
                    battlefield.get(player.getPositionBattle()).setXY(x, y);
                    player.setPositionBattle(position);
                    playerPosition = position;
                    if (!Data.bdMob.get(0).isDead()) {
                        if (Data.bdMob.get(0).getFlag(player.getPositionBattle(), Data.bdMob.get(0).getPositionBattle())) {
                            Toast.makeText(holder.itemView.getContext(), "Вас атакуют", Toast.LENGTH_SHORT).show();
                            onUpdate.onAttackEnemy();
                        } else {
                            hodEnemy(enemy, holder.itemView.getContext());
                        }
                    }
                    onUpdate.onUpdate();

                }
            });
        }
        if (holder instanceof EnemyHolder) {
            enemy = battlefield.get(position).getAction();
            ((EnemyHolder) holder).name.setText(enemy.getName());
            ((EnemyHolder) holder).hp.setText("" + enemy.getHp_now() + "/" + enemy.getHp());
//            ((EnemyHolder) holder).textView.setText("X: " + Data.bdMob.get(0).getX() + "Y: " + Data.bdMob.get(0).getY());
            int x = enemy.getX() - player.getX();
            int y = enemy.getY() - player.getY();
            if (!Data.bdMob.get(0).isDead()) {
                if (x <= 1 && y <= 1) onUpdate.onAttack();
            } else onUpdate.onUpdate();
            holder.itemView.setOnClickListener(v -> {
                if (battlefield.get(position).getFlag(position, player.getPositionBattle())) {
                    Toast.makeText(holder.itemView.getContext(), "Можно атаковать", Toast.LENGTH_SHORT).show();
                    onUpdate.onAttack();
                } else
                    Toast.makeText(holder.itemView.getContext(), "Вы далеко", Toast.LENGTH_SHORT).show();
            });
        }
        if (holder instanceof PlayerHolder) {
            player = battlefield.get(position).getAction();
            ((PlayerHolder) holder).name.setText(player.getName());
            ((PlayerHolder) holder).hp.setText("" + player.getHp_now() + "/" + player.getHp());
            holder.itemView.setOnClickListener(v -> {
//                Toast.makeText(holder.itemView.getContext(), "X=" + player.getX() + " Y= " + player.getY(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return battlefield.size();
    }

    public static class EmptyHolder extends RecyclerView.ViewHolder {

        TextView pos;
        LinearLayout layout;

        public EmptyHolder(@NonNull View itemView) {
            super(itemView);

            pos = itemView.findViewById(R.id.info_box_empty_text);
            layout = itemView.findViewById(R.id.empty);


        }
    }

    public static class PlayerHolder extends RecyclerView.ViewHolder {
        TextView name, hp;

        public PlayerHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pName);
            hp = itemView.findViewById(R.id.pHp);
            itemView.setOnClickListener(v -> {

                Toast.makeText(itemView.getContext(), "Position: " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    public static class EnemyHolder extends RecyclerView.ViewHolder {
        TextView name, hp;

        public EnemyHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.eName);
            hp = itemView.findViewById(R.id.eHp);
            itemView.setOnClickListener(v -> {

//                Toast.makeText(itemView.getContext(), "Position: " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    void hodEnemy(Person enemy, Context context) {
//        Toast.makeText(context, "X=" + player.getX() + " Y= " + player.getY(), Toast.LENGTH_SHORT).show();
        int x = player.getX() - enemy.getX();
        int y = player.getY() - enemy.getY();
//        Toast.makeText(context, "X=" + x + " Y= " + y, Toast.LENGTH_SHORT).show();
        int pos = enemy.getPositionBattle();
        if (x > 0) {
            if (y > 1 && !(Data.bdMob.get(0).getY() == 6)) {
                hodEnemy2(pos - 4, pos, context);
            } else if (y < 1 && !(Data.bdMob.get(0).getY() == 0)) {
                hodEnemy2(pos + 6, pos, context);
            } else if (!(battlefield.get(pos + 1) instanceof BoxPlayer) && (y == 1 || y == 0)) {
                hodEnemy2(pos + 1, pos, context);
            }

        } else if (x < 0) {
            if (y > 1 && !(Data.bdMob.get(0).getY() == 6)) {
                hodEnemy2(pos - 6, pos, context);
            } else if (y < 1 && !(Data.bdMob.get(0).getY() == 0)) {
                hodEnemy2(pos + 4, pos, context);
            } else if (!(battlefield.get(pos - 1) instanceof BoxPlayer) && (y == 1 || y == 0)) {
                hodEnemy2(pos - 1, pos, context);
            }
        } else {
            if (y > 1) {
                hodEnemy2(pos - 5, pos, context);
            } else if (y < 1) {
                hodEnemy2(pos + 5, pos, context);
            } else {

            }
        }
    }


    void hodEnemy2(int pos, int pos1, Context context) {
//        Toast.makeText(context, "X=" + battlefield.get(pos).getX() + " Y= " + battlefield.get(pos).getY(), Toast.LENGTH_SHORT).show();
        int x1 = enemy.getX(), y1 = enemy.getY();
        enemy.setXY(battlefield.get(pos).getX(), battlefield.get(pos).getY());
//        Toast.makeText(context, "pos " + pos + "pos1 " + pos1, Toast.LENGTH_SHORT).show();
        battlefield.set(pos, battlefield.get(pos1));
        battlefield.set(pos1, new BoxEmpty());
        battlefield.get(enemy.getPositionBattle()).setXY(x1, y1);
        enemy.setPositionBattle(pos);
    }
}
