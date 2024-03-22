package com.example.otb;

public class puzzle1FragmentMock implements puzzle1Fragment {
    private final puzzle1LogicHandler mHandler = new puzzle1LogicHandler(this);
    private int mBrightness = 0;
    private boolean mObjective1IsSolved = false;
    private boolean mObjective2IsSolved = false;
    @Override
    public void puzzle1Animation(int objectiveNumber) {
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
