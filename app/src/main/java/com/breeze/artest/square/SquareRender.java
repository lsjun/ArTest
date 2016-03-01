package com.breeze.artest.square;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by breeze on 2/25/16.
 * Description: {$TODO}
 */
public class SquareRender implements GLSurfaceView.Renderer {

    private SquareObject squareObject;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        initRendering();
    }

    private void initRendering() {

        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        squareObject = new SquareObject();

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        Matrix.frustumM(SquareObject.mProjectMatrix, 0, -ratio, ratio, -1, 1, 1, 10);

        Matrix.setLookAtM(SquareObject.mVMatrix, 0, 0f, 0f, 3f, 0f, 0f, 0f, 0f, 1.0f, 0f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        renderFrame();
    }

    private void renderFrame() {

        squareObject.draw();
    }
}
