package com.breeze.artest.square;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by breeze on 2/25/16.
 * Description: {$TODO}
 */
public class SquareObject {

    private final int mProgram;

    private Buffer vertexBuffer;
    private Buffer drawBuffer;
    private Buffer colorBuffer;

    public final static int COORDS_PER_VERTEX = 3;
    private static float[] squareCoords = new float[]{
            -0.5f, -0.5f, 0.5f, //front
            0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            -0.5f, -0.5f, -0.5f, //back
            0.5f, -0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,

            -0.5f, 0.5f, 0.5f,  //top
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,

            -0.5f, -0.5f, 0.5f, //bottom
            0.5f, -0.5f, 0.5f
            - 0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f
            - 0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,

            -0.5f, -0.5f, 0.5f, //left
            -0.5f, -0.5f, -0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,

            0.5f, -0.5f, 0.5f,  //right
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, -0.5f,
    };

    float[] colors = new float[]{
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 0.0f, 1.0f,

            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 0.0f, 1.0f,

            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 0.0f, 1.0f,

            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 0.0f, 1.0f,

            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 0.0f, 1.0f,

            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 0.0f, 1.0f
    };

    private short[] drawOrder = new short[]{
            0, 1, 2, 1, 2, 3
    };

    public static float[] mProjectMatrix = new float[16];
    public static float[] mVMatrix = new float[16];
    public static float[] mMVPMatrix;

    int muMVPMatrixHandle;
    int maPositionHandle;
    int maColorHandle;

    static float[] mMMatrix = new float[16];
    int vCount = 0;
    float xAngle = 0;


    public SquareObject() {
        vertexBuffer = fillBuffer(squareCoords);
        drawBuffer = fillBuffer(drawOrder);
        colorBuffer = fillBuffer(colors);

        vCount = 4;

        mProgram = SquareShaders.createProgram(SquareShaders.vertexShaderCode, SquareShaders.fragmentShaderCode);

//        int vertexShader = loadShader(GLES20.GL_TRIANGLES, SquareShaders.vertexShaderCode);
//        int fragShader = loadShader(GLES20.GL_FRAGMENT_SHADER, SquareShaders.fragmentShaderCode);
//
//        GLES20.glAttachShader(mProgram, vertexShader);
//        GLES20.glAttachShader(mProgram, fragShader);
//        GLES20.glLinkProgram(mProgram);


        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");

        maColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");

        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

    }

    public void draw() {
//        Log.d("draw", "draw");

        GLES20.glUseProgram(mProgram);

        Matrix.setRotateM(mMMatrix, 0, 0, 0, 1, 0);
        Matrix.translateM(mMMatrix, 0, 0, 0, 1);
        Matrix.setRotateM(mMMatrix, 0, xAngle++, 1, 1, 0);

//        GLES20.glUniform4fv(maColorHandle, 1, colors, 0);



        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, getFianlMatrix(mMMatrix), 0);

        GLES20.glVertexAttribPointer(maPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);
        GLES20.glVertexAttribPointer(maColorHandle, 4,
                GLES20.GL_FLOAT, false, 4 * 4, colorBuffer);

        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maColorHandle);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vCount);

//        GLES20.glDisableVertexAttribArray(maPositionHandle);
//        GLES20.glDisableVertexAttribArray(maColorHandle);
    }

    /**
     * 计算最终投影的矩阵
     *
     * @param spec
     * @return
     */
    public static float[] getFianlMatrix(float[] spec) {
        mMVPMatrix = new float[16];
                /*
         * 计算矩阵变换投影
         * 
         * 参数介绍 : ① 总变换矩阵 ② 总变换矩阵起始索引 ③ 摄像机位置朝向矩阵 ④ 摄像机朝向矩阵起始索引 ⑤ 投影变换矩阵 ⑥
         * 投影变换矩阵起始索引
         */
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }

    protected Buffer fillBuffer(float[] array) {
        // Each float takes 4 bytes
        ByteBuffer bb = ByteBuffer.allocateDirect(4 * array.length);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        for (float d : array)
            bb.putFloat(d);
        bb.rewind();

        return bb;

    }

    protected Buffer fillBuffer(short[] array) {
        // Each short takes 2 bytes
        ByteBuffer bb = ByteBuffer.allocateDirect(2 * array.length);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        for (short s : array)
            bb.putShort(s);
        bb.rewind();

        return bb;

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
