package com.appzupp.accesscare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appzupp.accesscare.R;

import java.util.List;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
    List<String> mActivityNamesList;
    List<Integer> mActivityImageList;
    LayoutInflater mLayoutInflater;

    public ActivityListAdapter(List<String> activityNames, List<Integer> activityImages, Context context) {
        this.mActivityNamesList = activityNames;
        this.mActivityImageList = activityImages;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.activity_list_grid_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.activityText.setText(mActivityNamesList.get(position));
        holder.activityImage.setImageResource(mActivityImageList.get(position));
    }

    @Override
    public int getItemCount() {
        return mActivityNamesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView activityImage;
        TextView activityText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.activityImage = itemView.findViewById(R.id.image_view_activity);
            this.activityText = itemView.findViewById(R.id.text_view_activity);
        }
    }

}
