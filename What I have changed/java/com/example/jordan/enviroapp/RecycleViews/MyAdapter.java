package com.example.jordan.enviroapp.RecycleViews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jordan.enviroapp.R;

import org.w3c.dom.Text;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    /* Create my List, to mainipulate
    Objects based on Query data. */


    public interface OnItemClickListener {
        void onItemClick(ProjectInfoStruct item);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView projectNameTextView;
        public TextView projectLocationTextView;


        public ViewHolder(View itemLayout) {
            super(itemLayout);
            projectNameTextView = (TextView)itemLayout.findViewById(R.id.projectName) ;
            projectLocationTextView = (TextView)itemLayout.findViewById(R.id.ProjectLocation);
        }

        public void bind(final ProjectInfoStruct item, final OnItemClickListener listener) {

            projectNameTextView.setText("Project Name: " + item.getProjectName());
            projectLocationTextView.setText("Location Name: " + item.getLocationText());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }

    }

    private List<ProjectInfoStruct> getDataSet;
    private OnItemClickListener listener;

    public MyAdapter(List<ProjectInfoStruct> getDataSet, OnItemClickListener  listener) {
        this.getDataSet = getDataSet;
        this.listener = listener;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_layout, parent, false);

        ViewHolder vh = new ViewHolder(itemLayout);
        return vh;
    }
    //Here can replace the array List contents, and find the specific information needed.
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.bind(getDataSet.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return getDataSet.size();
    }
}
