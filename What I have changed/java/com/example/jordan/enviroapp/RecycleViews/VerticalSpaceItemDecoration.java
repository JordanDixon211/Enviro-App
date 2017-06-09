package com.example.jordan.enviroapp.RecycleViews;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Jordan on 12/05/2017.
 */

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {


    /**
     * Adds Spaces between items in the card View
     * */
    private final int verticalSpaceHeight;

    public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
    }
}
