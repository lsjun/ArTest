package com.breeze.artest.utils;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by breeze on 2/26/16.
 * Description: {$TODO}
 */
public class ShaderUtils {

    /**
     * 创建着色程序
     *
     * ① 加载顶点着色器
     * ② 加载片元着色器
     * ③ 创建着色程序
     * ④ 向着色程序中加入顶点着色器
     * ⑤ 向着色程序中加入片元着色器
     * ⑥ 链接程序
     * ⑦ 获取链接程序结果
     *
     * @param vertexSource        定点着色器脚本字符串
     * @param fragmentSource    片元着色器脚本字符串
     * @return
     */
    public static int createProgram(String vertexSource , String fragmentSource){
        //1. 加载顶点着色器, 返回0说明加载失败
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if(vertexShader == 0)
        {
            Log.e("ES20_ERROR", "加载顶点着色器失败");
            return 0;
        }
        //2. 加载片元着色器, 返回0说明加载失败
        int fragShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if(fragShader == 0)
        {
            Log.e("ES20_ERROR", "加载片元着色器失败");
            return 0;
        }
        //3. 创建着色程序, 返回0说明创建失败
        int program = GLES20.glCreateProgram();
        if(program != 0){
            //4. 向着色程序中加入顶点着色器
            GLES20.glAttachShader(program, vertexShader);
            //检查glAttachShader操作有没有失败
            checkGLError("glAttachShader");
            //5. 向着色程序中加入片元着色器
            GLES20.glAttachShader(program, fragShader);
            //检查glAttachShader操作有没有失败
            checkGLError("glAttachShader");

            //6. 链接程序
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            //获取链接程序结果
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if(linkStatus[0] != GLES20.GL_TRUE){
                Log.e("ES20.ERROR", "链接程序失败 : ");
                Log.e("ES20.ERROR", GLES20.glGetProgramInfoLog(program));
                //如果链接程序失败删除程序
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        else{
            Log.e("ES20_ERROR", "glCreateProgram Failed: "+program);
        }

        return program;
    }

    /**
     * 检查每一步的操作是否正确
     *
     * 使用GLES20.glGetError()方法可以获取错误代码, 如果错误代码为0, 那么就没有错误
     *
     * @param op 具体执行的方法名, 比如执行向着色程序中加入着色器,
     *         使glAttachShader()方法, 那么这个参数就是"glAttachShader"
     */
    public static void checkGLError(String op){
        int error;
        //错误代码不为0, 就打印错误日志, 并抛出异常
        while( (error = GLES20.glGetError()) != GLES20.GL_NO_ERROR ){
            Log.e("ES20_ERROR", op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }

    /**
     * 加载着色器方法
     *
     * 流程 :
     *
     * ① 创建着色器
     * ② 加载着色器脚本
     * ③ 编译着色器
     * ④ 获取着色器编译结果
     *
     * @param shaderType 着色器类型,顶点着色器(GLES20.GL_FRAGMENT_SHADER), 片元着色器(GLES20.GL_FRAGMENT_SHADER)
     * @param source 着色脚本字符串
     * @return 返回的是着色器的引用, 返回值可以代表加载的着色器
     */
    public static int loadShader(int shaderType , String source){
        //1.创建一个着色器, 并记录所创建的着色器的id, 如果id==0, 那么创建失败
        int shader = GLES20.glCreateShader(shaderType);
        if(shader != 0){
            //2.如果着色器创建成功, 为创建的着色器加载脚本代码
            GLES20.glShaderSource(shader, source);
            //3.编译已经加载脚本代码的着色器
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            //4.获取着色器的编译情况, 如果结果为0, 说明编译失败
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if(compiled[0] == 0){
                Log.e("ES20_ERROR", "Could not compile shader " + shaderType + ":");
                Log.e("ES20_ERROR", GLES20.glGetShaderInfoLog(shader));
                //编译失败的话, 删除着色器, 并显示log
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        else{
            Log.e("ES20_ERROR", "Could Create shader " + shaderType + ":"+
                    "Error:"+ shader);
        }
        return shader;
    }
}
