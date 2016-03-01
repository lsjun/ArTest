package com.breeze.artest.square;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.breeze.artest.helper.AppGLSurfaceView;

/**
 * Created by breeze on 2/25/16.
 * Description: {square}
 */
public class TriangleActivty extends AppCompatActivity {

    private AppGLSurfaceView mGlView;

    private TriangleRender mRenderer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGlView = new AppGLSurfaceView(this);
        setContentView(mGlView);

        init();
    }

    private void init() {
        mRenderer = new TriangleRender();
        mGlView.setRenderer(mRenderer);
    }
}
