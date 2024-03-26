package com.example.otb;

public class EasyPuzzle1FragmentMock implements EasyPuzzle1Fragment {

    private final EasyPuzzle1LogicHandler mHandler;

    public EasyPuzzle1FragmentMock() {
        // Make sure that puzzle1LogicHandler can accept puzzle1Fragment or its implementer
        mHandler = new EasyPuzzle1LogicHandler(this);
    }

    private int mBrightness = 0;
    private boolean mObjective1IsSolved = false;
    private boolean mObjective2IsSolved = false;
    @Override
    public void easyPuzzle1Animation(int objectiveNumber) {
        switch (objectiveNumber) {
            case 1:
                mObjective1IsSolved = true;
                break;
            case 2:
                mObjective2IsSolved = true;
                break;
            default:
                break;
        }
    }

    @Override
    public int getAndroidBrightness() {
        return mBrightness;
    }

    public boolean isObjectiveSolved(int objectiveNumber) {
        boolean rtn = false;
        switch (objectiveNumber) {
            case 1:
                rtn = mObjective1IsSolved;
                break;
            case 2:
                rtn = mObjective2IsSolved;
                break;
            default:
                break;
        }
        return rtn;
    }

    public void sensorChanged()  {
        mHandler.handleSensorChange();
    }

    public void setBrightness(int brightness) {
        mBrightness = brightness;
    }

}
