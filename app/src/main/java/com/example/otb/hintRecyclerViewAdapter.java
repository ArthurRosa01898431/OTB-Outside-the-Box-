package com.example.otb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class hintRecyclerViewAdapter extends RecyclerView.Adapter<hintRecyclerViewAdapter.MyViewHolder> {

    private final Context mContext;
    ArrayList<hintModel> mHintModel;

    public hintRecyclerViewAdapter(Context mContext, ArrayList<hintModel> hintModel) {
        this.mContext = mContext;
        this.mHintModel = hintModel;
    }

    /*
        Inflates the layout.
     */
    @NonNull
    @Override
    public hintRecyclerViewAdapter.MyViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.hint_recycler_view_row, parent, false);

        return new hintRecyclerViewAdapter.MyViewHolder(view);
    }

    /*
        Assigning values to the views we created in the recycler_view_row layout file
        based on the position of the recycler view.
     */
    @Override
    public void onBindViewHolder(
            @NonNull hintRecyclerViewAdapter.MyViewHolder holder, int position) {

        hintModel objectiveHintData = mHintModel.get(position);


        // Only Objectives that been clicked or Hints for all objective should be displayed.
        if (objectiveHintData.shouldBeDisplayed()) {
            holder.bindData(
                    objectiveHintData.getObjectiveImage(),
                    objectiveHintData.getHintText(),
                    objectiveHintData.isIsHintForAllObjectives());
        } else {
            // Removes objective's hint if they should not be
            // displayed by setting the visibility to gone.
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
    }

    /*
        The recycler view wants to know the number of items you want displayed.

        @return the amount of items on the layout.
     */
    @Override
    public int getItemCount() {
        return mHintModel.size();
    }

    /*
        Grabs the views from recycler_view_row layout file. (Similar to onCreate Method)
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mObjectiveImage;
        private final TextView mHintText1;
        private final TextView mHintText2;
        private final TextView mObjectiveText;
        private final CardView mCardViewHint2;
        private final ConstraintLayout mHintRecyclerRow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mHintRecyclerRow = itemView.findViewById(R.id.hintRecyclerRow);
            mObjectiveImage = itemView.findViewById(R.id.hintObjectiveImage);
            mHintText1 = itemView.findViewById(R.id.hintText1);
            mHintText2 = itemView.findViewById(R.id.hintText2);
            mObjectiveText = itemView.findViewById(R.id.hintObjectiveText);
            mCardViewHint2 = itemView.findViewById(R.id.hintView2);
        }

        /*
            Sets the data for the view.
         */
        public void bindData(int objectiveImage, String[] hints, boolean isObjectiveAll) {
            mObjectiveImage.setImageResource(objectiveImage);
            int text = isObjectiveAll ? R.string.hint_for_all_objectives : R.string.hint_for_a_objective;
            mObjectiveText.setText(R.string.hint_for_all_objectives);
            mHintText1.setText(hints[0]);

            // If there are two hints for the objective, then
            // make card view that contains hintText visible.
            if (hints.length == 2) {
                mCardViewHint2.setVisibility(View.VISIBLE);
                mHintText2.setText(hints[1]);
            } else {
                mCardViewHint2.setVisibility(View.GONE);
            }
        }
    }
}
