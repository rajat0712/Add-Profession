package com.rajatw.addProfession.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajatw.addProfession.R;
import com.rajatw.addProfession.model.ProfessionExpItem;

import java.util.List;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.ViewHolder> {
    private List<ProfessionExpItem> professionExpItemList;
    private Context context;

    public DisplayAdapter(List<ProfessionExpItem> professionExpItems, Context context) {
        this.professionExpItemList = professionExpItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_display_profession, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProfessionExpItem professionExpItem = professionExpItemList.get(position);

        holder.Display.setText(professionExpItem.getProfession());
        holder.Exp.setText(String.valueOf(professionExpItem.getExperience()));
        holder.Cancel.setVisibility(professionExpItem.isCancelVisible() ? View.VISIBLE : View.INVISIBLE);

        // Set a tag to the EditText to identify its position in the list
        holder.Exp.setTag(position);

        // Add TextWatcher to capture changes in the EditText
        holder.Exp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Retrieve the position from the tag
                int position = (int) holder.Exp.getTag();

                // Update the experience value in the corresponding SkillExpItem
                try {
                    int newExperience = Integer.parseInt(editable.toString());
                    professionExpItemList.get(position).setExperience(newExperience);
                } catch (NumberFormatException e) {
                    // Handle the case where the input is not a valid integer
                    e.printStackTrace();
                }
            }
        });

        holder.Cancel.setOnClickListener(v -> handleCancelClick(position));
    }

    @Override
    public int getItemCount() {
        return professionExpItemList.size();
    }

    public void addNewProfession(ProfessionExpItem newSkill) {
        // Assuming the initial experience is set to "0"
        professionExpItemList.add(new ProfessionExpItem("New Profession", 0, true));
        notifyItemInserted(professionExpItemList.size() - 1);
    }

    public List<ProfessionExpItem> getUpdatedProfession() {
        return professionExpItemList;
    }

    private void handleCancelClick(int position) {
        if (position >= 0 && position < professionExpItemList.size()) {
            // Remove the item from the list
            professionExpItemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Display;
        private EditText Exp;
        private ImageView Cancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Display = itemView.findViewById(R.id.txtDisplay);
            Exp = itemView.findViewById(R.id.edtExp);
            Cancel = itemView.findViewById(R.id.imgCancel);
        }
    }
}
