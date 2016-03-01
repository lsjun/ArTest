package com.breeze.artest.square;

/**
 * Created by breeze on 2/25/16.
 * Description: {$TODO}
 */
public class TriangleShader {
    public final static String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "gl_Position = vPosition;" +
                    "}";
    public final static String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() { " +
                    "gl_FragColor = vColor; " +
                    "}";


}
