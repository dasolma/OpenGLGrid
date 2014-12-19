package com.dasolma.openglgrid;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by dasolma on 19/12/14.
 */
public class MainActivity extends Activity {

    private Grid grid;
    private TextView txtFPS;
    private TextView txtSize;
    private SeekBar sbWeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        grid = (Grid)findViewById(R.id.grid);
        txtFPS = (TextView)findViewById(R.id.fps);
        txtSize = (TextView)findViewById(R.id.size);
        sbWeight = (SeekBar)findViewById(R.id.weight_bar);



        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtFPS.setText("FPS: " + grid.fpsCounter.getFPS() );
                                txtSize.setText("Size: " + grid.wgrid * grid.hgrid );
                                grid.rweight = sbWeight.getProgress();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (grid != null)
            grid.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (grid != null)
            grid.onResume();
    }
}