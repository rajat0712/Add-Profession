package com.rajatw.addProfession.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajatw.addProfession.R;
import com.rajatw.addProfession.model.Profession;

import java.util.List;

public class ProfessionAdapter extends RecyclerView.Adapter<ProfessionAdapter.ViewHolder> {
    private List<Profession> professions;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public ProfessionAdapter(List<Profession> professionList, Context context) {
        this.professions = professionList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profession profession = professions.get(position);
        holder.Display.setText(profession.getProfession());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return professions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Display;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Display = itemView.findViewById(R.id.txtDisplay);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
