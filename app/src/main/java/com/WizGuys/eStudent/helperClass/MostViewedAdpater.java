package com.WizGuys.eStudent.helperClass;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.helperClass.helperResources.MostViewedHelperClass;
import java.util.ArrayList;
public class MostViewedAdpater extends RecyclerView.Adapter<MostViewedAdpater.MostViewedViewHolder> {
    ArrayList<MostViewedHelperClass> mostViewedLocations;
    public MostViewedAdpater(ArrayList<MostViewedHelperClass> mostViewedLocations) {
        this.mostViewedLocations = mostViewedLocations;
    }
    @NonNull
    @Override
    public MostViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_viewed_card_design, parent, false);
        MostViewedViewHolder mostViewedViewHolder = new MostViewedViewHolder(view);
        return mostViewedViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MostViewedViewHolder holder, int position) {
        MostViewedHelperClass helperClass = mostViewedLocations.get(position);
        holder.imageView.setImageResource(helperClass.getImage());
        holder.textView.setText(helperClass.getTitle());
        holder.mv_desc.setText(helperClass.getDescription());
    }
    @Override
    public int getItemCount() {
        return mostViewedLocations.size();
    }
    public static class MostViewedViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView mv_desc;

        public MostViewedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mv_image);
            textView = itemView.findViewById(R.id.mv_title);
            mv_desc = itemView.findViewById(R.id.mv_desc);
        }
    }
}