package com.breeze.artest.helper;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by breeze on 2/25/16.
 * Description: {$TODO}
 */
public class AppGLSurfaceView extends GLSurfaceView {
    private Renderer mRenderer;

    public AppGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);
    }

    @Override
    public void setRenderer(Renderer renderer) {
        super.setRenderer(renderer);
        this.mRenderer = renderer;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        
        return super.onTouchEvent(event);
    }
}
