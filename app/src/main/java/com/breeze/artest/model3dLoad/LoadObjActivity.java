package com.breeze.artest.model3dLoad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.breeze.artest.helper.AppGLSurfaceView;

/**
 * Created by breeze on 2/28/16.
 * Description: {$TODO}
 */
public class LoadObjActivity extends AppCompatActivity {

    private AppGLSurfaceView mGlView;
    private ObjRenderer mRenderer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGlView = new AppGLSurfaceView(this);

        init();
    }

    private void init() {
        mRenderer = new ObjRenderer();
        mGlView.setRenderer(mRenderer);
    }
}
