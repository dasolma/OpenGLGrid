package com.dasolma.openglgrid;

/**
 * Created by dasolma on 19/12/14.
 */
import android.util.Log;

public class FPSCounter {
    long startTime = System.nanoTime();
    int frames = 0;

    int fps = 0;

    public void logFrame() {
        frames++;
        if(System.nanoTime() - startTime >= 1000000000) {
            Log.d("FPSCounter", "fps: " + frames);

            fps = frames;
            frames = 0;
            startTime = System.nanoTime();
        }
    }

    public int getFPS() {
        return fps;
    }
}