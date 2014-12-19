package com.dasolma.openglgrid;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.widget.TextView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by dasolma on 19/12/14.
 */
public class Grid extends GLSurfaceView {



    //grid config
    private int grid_matrix[];
    public int wgrid = 50;
    public int hgrid = 70;
    public int rweight = 25;
    public FPSCounter fpsCounter = new FPSCounter();


    //opengl vars
    private FloatBuffer vertexBuffer;
    float vertices[] = {
            -0.5f, -0.5f,  0.0f,  // 0. left-bottom
            0.5f, -0.5f,  0.0f,  // 1. right-bottom
            -0.5f,  0.5f,  0.0f,  // 2. left-top
            0.5f,  0.5f,  0.0f   // 3. right-top
    };

    private final class MyRenderer implements GLSurfaceView.Renderer {

        /* Stage width and height */
        private float w, h;

        /* Screen width and height */
        private int screenWidth, screenHeight;

        private Random rnd = new Random();

        public final void onDrawFrame(GL10 gl) {

            gl.glClear(GLES10.GL_COLOR_BUFFER_BIT);
            float[] c;
            int ic = 0;

            /*
            ic = rnd.nextInt(Colors.NUM_COLORS);
            c = Colors.getColor(ic);
            drawRectangle(gl, 100, 100, c[0], c[1], c[2]);
            */

            wgrid = screenWidth / rweight;
            hgrid = screenHeight / rweight;

            for(int i = 0; i < wgrid; i++) {
                for(int j = 0; j < hgrid; j++) {
                    ic = rnd.nextInt(Colors.NUM_COLORS);
                    c = Colors.getColor(ic);

                    drawRectangle(gl, rweight * i, rweight * j, c[0], c[1], c[2]);

                }
            }

            fpsCounter.logFrame();



        }

        private void drawRectangle(GL10 gl, int x, int y, float r, float g, float b) {

            gl.glPushMatrix();
            gl.glTranslatef(x, y, 0);
            gl.glScalef(rweight, rweight, 0);

            gl.glColor4f(r, g, b, 1);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
            gl.glPopMatrix();
        }


        public final void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glClearColor(0, 0, 0, 1);

            float w, h;

            if(width > height) {
                h = 600;
                w = width * h / height;
            } else {
                w = 600;
                h = height * w / width;
            }

            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(0, w, h, 0, -1, 1);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();

            screenWidth = width;
            screenHeight = height;



            gl.glViewport(0, 0, screenWidth, screenHeight);
        }

        public final void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // Set up alpha blending
            gl.glEnable(GL10.GL_ALPHA_TEST);
            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);

            // We are in 2D. Who needs depth?
            gl.glDisable(GL10.GL_DEPTH_TEST);

            // Enable vertex arrays (we'll use them to draw primitives).
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

            // Enable texture coordination arrays.
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }
    }

    public Grid(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRenderer(new MyRenderer());

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());

        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }
}
