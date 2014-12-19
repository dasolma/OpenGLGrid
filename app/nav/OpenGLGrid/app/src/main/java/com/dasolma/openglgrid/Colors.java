package com.dasolma.openglgrid;

/**
 * Created by dasolma on 19/12/14.
 */
public class Colors {

    private static float _colors[][] = {
        {1,1,1,0}, {1,0,0,0}, {0,1,0,0}, {0,0,1,0},
        {1,1,0,0}, {1,0,1,0}, {0,1,1,0}
    };

    public static final int NUM_COLORS = 7;

    public static float[] getColor(int i) {
        if (i < 0 || i >= NUM_COLORS) return new float[] {0,0,0,0};

        return _colors[i];
    }

}
