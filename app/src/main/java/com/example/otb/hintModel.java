package com.example.otb;

import java.util.ArrayList;

public class hintModel {
    private ArrayList<objectiveHintData> objectiveHintData = new ArrayList<>();






    public void createObjectiveHints(
            int image, ArrayList<String> hintTexts, boolean isObjectiveHintForAll) {

        objectiveHintData.add(new objectiveHintData(image, hintTexts, isObjectiveHintForAll));

    }


    public ArrayList<com.example.otb.objectiveHintData> getObjectiveHintData() {
        return objectiveHintData;
    }
}
