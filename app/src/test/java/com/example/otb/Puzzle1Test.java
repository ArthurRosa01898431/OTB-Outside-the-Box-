package com.example.otb;


import org.junit.Test;
import static org.junit.Assert.*;


public class Puzzle1Test {
    private final EasyPuzzle1FragmentMock mFragment = new EasyPuzzle1FragmentMock();

    @Test
    public void canPuzzle1BeSolved() {
        // If brightness is not min or max, then none of the objectives are solved.
        mFragment.setBrightness(50);

        mFragment.sensorChanged();

        assertFalse(mFragment.isObjectiveSolved(1));
        assertFalse(mFragment.isObjectiveSolved(2));

        // When brightness min, objective 1 is solved.
        mFragment.setBrightness(244);
        mFragment.sensorChanged();

        assertTrue(mFragment.isObjectiveSolved(1));
        assertFalse(mFragment.isObjectiveSolved(2));

        // When brightness max, objective 2 is solved.
        mFragment.setBrightness(0);
        mFragment.sensorChanged();

        assertTrue(mFragment.isObjectiveSolved(2));

    }
}
