
package com.breeze.artest.modelCube;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.breeze.artest.utils.ShaderUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class CubeObject {
    // Data for drawing the 3D plane as overlay
    private static final float cubeVertices[] = {
            -1.00f, -1.00f, 1.00f, // front
            1.00f, -1.00f, 1.00f,
            1.00f, 1.00f, 1.00f,
            -1.00f, -1.00f, 1.00f,
            1.00f, 1.00f, 1.00f,
            -1.00f, 1.00f, 1.00f,

            -1.00f, -1.00f, -1.00f, // back
            1.00f, -1.00f, -1.00f,
            1.00f, 1.00f, -1.00f,
            -1.00f, -1.00f, -1.00f,
            1.00f, 1.00f, -1.00f,
            -1.00f, 1.00f, -1.00f,

            -1.00f, -1.00f, -1.00f, // left
            -1.00f, -1.00f, 1.00f,
            -1.00f, 1.00f, 1.00f,
            -1.00f, -1.00f, -1.00f,
            -1.00f, 1.00f, 1.00f,
            -1.00f, 1.00f, -1.00f,

            1.00f, -1.00f, -1.00f, // right
            1.00f, -1.00f, 1.00f,
            1.00f, 1.00f, 1.00f,
            1.00f, -1.00f, -1.00f,
            1.00f, 1.00f, 1.00f,
            1.00f, 1.00f, -1.00f,

            -1.00f, 1.00f, 1.00f, // top
            1.00f, 1.00f, 1.00f,
            1.00f, 1.00f, -1.00f,
            -1.00f, 1.00f, 1.00f,
            1.00f, 1.00f, -1.00f,
            -1.00f, 1.00f, -1.00f,

            -1.00f, -1.00f, 1.00f, // bottom
            1.00f, -1.00f, 1.00f,
            1.00f, -1.00f, -1.00f,
            -1.00f, -1.00f, 1.00f,
            1.00f, -1.00f, -1.00f,
            -1.00f, -1.00f, -1.00f};


    private static final float cubeTexcoords[] = {
            1, 0, 0, 1,     //front
            1, 0, 0, 1,
            1, 0, 0, 1,
            1, 0, 0, 1,
            1, 0, 0, 1,
            1, 0, 0, 1,

            0, 1, 0, 1,     //back
            0, 1, 0, 1,
            0, 1, 0, 1,
            0, 1, 0, 1,
            0, 1, 0, 1,
            0, 1, 0, 1,

            0, 0, 1, 1,     //left
            0, 0, 1, 1,
            0, 0, 1, 1,
            0, 0, 1, 1,
            0, 0, 1, 1,
            0, 0, 1, 1,

            0, 1, 1, 1,     //right
            0, 1, 1, 1,
            0, 1, 1, 1,
            0, 1, 1, 1,
            0, 1, 1, 1,
            0, 1, 1, 1,

            1, 1, 0, 1,     //top
            1, 1, 0, 1,
            1, 1, 0, 1,
            1, 1, 0, 1,
            1, 1, 0, 1,
            1, 1, 0, 1,

            1, 0, 1, 1,     //bottom
            1, 0, 1, 1,
            1, 0, 1, 1,
            1, 0, 1, 1,
            1, 0, 1, 1,
            1, 0, 1, 1
    };


    private static final float cubeNormals[] = {
            0, 0, 1,
            0, 0, 1,
            0, 0, 1,
            0, 0, 1,

            0, 0, -1,
            0, 0, -1,
            0, 0, -1,
            0, 0, -1,

            -1, 0, 0,
            -1, 0, 0,
            -1, 0, 0,
            -1, 0, 0,

            1, 0, 0,
            1, 0, 0,
            1, 0, 0,
            1, 0, 0,

            0, 1, 0,
            0, 1, 0,
            0, 1, 0,
            0, 1, 0,

            0, -1, 0,
            0, -1, 0,
            0, -1, 0,
            0, -1, 0,
    };

    private static final short cubeIndices[] = {
            0, 1, 2, 3, 4, 5, // front
            6, 7, 8, 9, 10, 11, // back
            12, 13, 14, 15, 16, 17, // left
            18, 19, 20, 21, 22, 23, // right
            24, 25, 26, 27, 28, 29, // top
            30, 31, 32, 33, 34, 35  // bottom
    };

    private Buffer mVertBuff;
    private Buffer mTexCoordBuff;
    private Buffer mNormBuff;
    private Buffer mIndBuff;


    private int shaderProgramID;
    private int vertexHandle;
    private int normalHandle;
    private int textureCoordHandle;
    private int mvpMatrixHandle;
    private int texSampler2DHandle;

    private float angle;

    public float[] mMVPMatrix = new float[16];
    public float[] mViewMatrix = new float[16];
    public float[] mModelMatrix = new float[16];
    public float[] mProjectionMatrix = new float[16];

    public CubeObject() {
        mVertBuff = fillBuffer(cubeVertices);
        mTexCoordBuff = fillBuffer(cubeTexcoords);
        mNormBuff = fillBuffer(cubeNormals);
        mIndBuff = fillBuffer(cubeIndices);

        shaderProgramID = ShaderUtils.createProgram(CubeShaders.CUBE_MESH_VERTEX_SHADER,
                CubeShaders.CUBE_MESH_FRAGMENT_SHADER);

        vertexHandle = GLES20.glGetAttribLocation(shaderProgramID,
                "vertexPosition");
        normalHandle = GLES20.glGetAttribLocation(shaderProgramID,
                "vertexNormal");
        textureCoordHandle = GLES20.glGetAttribLocation(shaderProgramID,
                "vertexTexCoord");
        mvpMatrixHandle = GLES20.glGetUniformLocation(shaderProgramID,
                "modelViewProjectionMatrix");
        texSampler2DHandle = GLES20.glGetUniformLocation(shaderProgramID,
                "texSampler2D");
    }

    public void draw() {
        GLES20.glUseProgram(shaderProgramID);

        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, -1.0f);
        Matrix.rotateM(mModelMatrix, 0, angle, 1.0f, 1.0f, 0.0f);

        GLES20.glVertexAttribPointer(vertexHandle, 3, GLES20.GL_FLOAT, false, 0, mVertBuff);
        GLES20.glEnableVertexAttribArray(vertexHandle);

        GLES20.glVertexAttribPointer(textureCoordHandle, 4, GLES20.GL_FLOAT, false, 0, mTexCoordBuff);
        GLES20.glEnableVertexAttribArray(textureCoordHandle);

        GLES20.glVertexAttribPointer(normalHandle, 3, GLES20.GL_FLOAT, false, 0, mNormBuff);
        GLES20.glEnableVertexAttribArray(normalHandle);

        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);


//        GLES20.glUniform1i(texSampler2DHandle, 0);

        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mMVPMatrix, 0);

//        GLES20.glDrawElements(GLES20.GL_TRIANGLES, cubeIndices.length, GLES20.GL_UNSIGNED_SHORT, mIndBuff);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);

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

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
