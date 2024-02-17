package com.example.otb;

import java.util.ArrayList;

public class objectiveHintData {

    private int mObjectiveImage;
    private ArrayList<String> mHintText;
    private boolean mIsHintForAllObjectives;


    public objectiveHintData(int mObjectiveImage, ArrayList<String> mHintText, boolean mIsHintForAllObjectives) {
        this.mObjectiveImage = mObjectiveImage;
        this.mHintText = mHintText;
        this.mIsHintForAllObjectives = mIsHintForAllObjectives;
    }

    public int getObjectiveImage() {
        return mObjectiveImage;
    }

    public ArrayList<String> getHintText() {
        return mHintText;
    }

    public boolean isIsHintForAllObjectives() {
        return mIsHintForAllObjectives;
    }
}
