package com.breeze.artest.square;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by breeze on 2/25/16.
 * Description: {$TODO}
 */
public class TriangleObject {

    private Buffer vertexBuffer;

    //数组每个顶点的坐标数
    public final static int COORDS_PER_VERTEX = 3;
    private static float[] triangleCoords = new float[]{
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
    };

    float[] colors = new float[]{
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f
    };

    public TriangleObject() {
        vertexBuffer = fillBuffer(triangleCoords);
    }

    public Buffer getVertexBuffer() {
        return vertexBuffer;
    }

    public float[] getColors() {
        return colors;
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

}
