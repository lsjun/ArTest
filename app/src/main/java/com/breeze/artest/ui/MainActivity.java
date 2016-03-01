package com.breeze.artest.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.breeze.artest.R;
import com.breeze.artest.model3dLoad.LoadObjActivity;
import com.breeze.artest.modelCube.CuboActivity;
import com.breeze.artest.sample.OpenGLES20Activity;
import com.breeze.artest.square.SquareActivity;
import com.breeze.artest.square.TriangleActivty;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click3d(View view) {
        Intent intent = new Intent(this, TriangleActivty.class);
        startActivity(intent);
    }

    public void click3dsquare(View view) {
        Intent intent = new Intent(this, SquareActivity.class);
        startActivity(intent);
    }

    public void clicksample(View view) {
        Intent intent = new Intent(this, OpenGLES20Activity.class);
        startActivity(intent);
    }

    public void clickCubo(View view) {
        Intent intent = new Intent(this, CuboActivity.class);
        startActivity(intent);
    }

    public void clickmodel(View view) {
        Intent intent = new Intent(this, LoadObjActivity.class);
        startActivity(intent);
    }
}
