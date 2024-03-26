package com.example.otb;

public interface EasyPuzzle1Fragment {
    /*
        Shows the animation of objective that has been solved.

        @param objectiveNumber - objective that been solved.
     */
    void easyPuzzle1Animation(int objectiveNumber);

    /*
        Returns the brightness value of android device.

        @return The brightness value of android device.
     */
    int getAndroidBrightness();

}
