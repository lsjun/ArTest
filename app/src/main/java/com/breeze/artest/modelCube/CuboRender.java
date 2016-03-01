package com.breeze.artest.modelCube;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by breeze on 2/26/16.
 * Description: {$TODO}
 */
public class CuboRender implements GLSurfaceView.Renderer {

    private CubeObject cuboObject;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

//        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
//        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
//        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        cuboObject = new CubeObject();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width/ height;

        Matrix.frustumM(cuboObject.mProjectionMatrix, 0, -ratio, ratio, -1.0f, 1.0f, 1.0f, 7f);

        Matrix.setLookAtM(cuboObject.mViewMatrix, 0, 0f, 0f, 3f, 0f, 0f, 0f, 0f, 1.0f, 0f);
    }

    float angle = 0.0f;

    @Override
    public void onDrawFrame(GL10 gl) {
        // Redraw background color
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

//        long time = SystemClock.uptimeMillis() % 10000L;
//        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);
        angle += 1.0f;

        cuboObject.setAngle(angle);

        cuboObject.draw();

    }
}
