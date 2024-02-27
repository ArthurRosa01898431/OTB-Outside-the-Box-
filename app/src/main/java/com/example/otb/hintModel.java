package com.example.otb;

import java.util.ArrayList;

/*
    Holds all the information about hints and changes how
    the XML should look like depending on the hint data.
 */
public class hintModel {
    private final int mObjectiveImage;
    private final String[] mHintText;
    private final boolean mIsHintForAllObjectives;
    private boolean mShouldBeDisplayed;


    public hintModel(int mObjectiveImage, String[] mHintText, boolean mIsHintForAllObjectives, boolean isShouldBeDisplayed) {
        this.mObjectiveImage = mObjectiveImage;
        this.mHintText = mHintText;
        this.mIsHintForAllObjectives = mIsHintForAllObjectives;
        this.mShouldBeDisplayed = isShouldBeDisplayed;
    }

    public void toggleShouldBeDisplayed() {
        mShouldBeDisplayed = !mShouldBeDisplayed;
    }

    public int getObjectiveImage() {
        return mObjectiveImage;
    }

    public String[] getHintText() {
        return mHintText;
    }

    public boolean isIsHintForAllObjectives() {
        return mIsHintForAllObjectives;
    }

    public boolean shouldBeDisplayed() {
        return mShouldBeDisplayed;
    }
}
