package com.example.otb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class hintRecyclerViewAdapter extends RecyclerView.Adapter<hintRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<hintModel> mHintModel;

    public hintRecyclerViewAdapter(Context mContext, ArrayList<com.example.otb.hintModel> hintModel) {
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

        private ImageView mObjectiveImage;
        private TextView mHintText;

        private TextView mObjectiveText;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mObjectiveImage = itemView.findViewById(R.id.hintObjectiveImage);
            mHintText = itemView.findViewById(R.id.hintText);
            mObjectiveText = itemView.findViewById(R.id.hintObjectiveText);



        }
    }
}
