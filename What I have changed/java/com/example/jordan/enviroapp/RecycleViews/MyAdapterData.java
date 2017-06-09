package com.example.jordan.enviroapp.RecycleViews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jordan.enviroapp.R;

import java.util.List;

/**
 * Created by Jordan on 12/05/2017.
 */

public class MyAdapterData extends RecyclerView.Adapter<MyAdapterData.ViewHolder> {


    private List<ProjectDataStruct> getDataSet;
    private MyAdapterData.OnItemClickListener listener;

    public MyAdapterData(List<ProjectDataStruct> getDataSet, MyAdapterData.OnItemClickListener listener) {
        this.getDataSet = getDataSet;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_data, parent, false);

        MyAdapterData.ViewHolder vh = new MyAdapterData.ViewHolder(itemLayout);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getDataSet.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return getDataSet.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ProjectDataStruct item);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView altitude;
        public TextView longitude;
        public TextView notes;

        public ViewHolder(View itemLayout) {
            super(itemLayout);
            altitude = (TextView)itemLayout.findViewById(R.id.altitude);
            longitude = (TextView)itemLayout.findViewById(R.id.longitude);
            notes = (TextView)itemLayout.findViewById(R.id.Notes);

        }


        public void bind(final ProjectDataStruct item, final MyAdapterData.OnItemClickListener listener) {

            altitude.setText("Latitude: " + item.getLatiude());
            longitude.setText("Longitude: " +item.getLongatiude());
            notes.setText("Notes: " + item.getNotes());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
