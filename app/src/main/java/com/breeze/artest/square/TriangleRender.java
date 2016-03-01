package com.breeze.artest.square;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by breeze on 2/25/16.
 * Description: {square render}
 */
public class TriangleRender implements GLSurfaceView.Renderer {

    private TriangleObject triangleObject;
    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;

    private float[] mProjMatrix = new float[16];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        initRendering();
    }

    private void initRendering() {
        // Set the background frame color
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        triangleObject = new TriangleObject();

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, TriangleShader.vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, TriangleShader.fragmentShaderCode);

        // 创建一个空的OpenGL ES Program
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);

        GLES20.glLinkProgram(mProgram);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        renderFrame();
    }

    private void renderFrame() {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUseProgram(mProgram);

        // 启用一个指向三角形的顶点数组的handle
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // 启用一个指向三角形的顶点数组的handle
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // 准备三角形的坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, TriangleObject.COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, 0, triangleObject.getVertexBuffer());

        // 获取指向fragment shader的成员vColor的handle
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        //设置三角形颜色
        GLES20.glUniform4fv(mColorHandle, 1, triangleObject.getColors(), 0);

        //画三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 9);

        // 禁用指向三角形的顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);

    }

    public static int loadShader(int type, String shaderCode) {
        //创建一个vertexShader类型(GLES20.GL_VERTEX_SHADER)
        //或创建一个fragshader类型(GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        //将源码添加到shader并编译
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
