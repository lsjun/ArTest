package com.breeze.artest.modelCube;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.breeze.artest.helper.AppGLSurfaceView;

/**
 * Created by breeze on 2/26/16.
 * Description: {$TODO}
 */
public class CuboActivity extends AppCompatActivity {

    private AppGLSurfaceView mGlView;
    private CuboRender mRenderer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGlView = new AppGLSurfaceView(this);
        setContentView(mGlView);

        init();
    }

    private void init() {
        mRenderer = new CuboRender();
        mGlView.setRenderer(mRenderer);
    }


    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGlView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGlView.onResume();
    }
}
