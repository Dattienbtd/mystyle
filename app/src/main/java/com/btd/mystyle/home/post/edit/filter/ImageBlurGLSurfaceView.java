package com.btd.mystyle.home.post.edit.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.btd.mystyle.home.post.edit.FilterImageAdapter;

import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ImageBlurGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {

    private Bitmap bitmapSrc;
    private int imageWidth;
    private int imageHeight;
    private int[] textures = new int[2];
    private EffectContext effectContext;
    private Effect effect;
    private TextureRenderer texRenderer = new TextureRenderer();
    private boolean initialized = false;
    int currentEffect = 0;
    private int count = 100;

    public ImageBlurGLSurfaceView(Context context) {
        super(context);
        initView();
    }

    public ImageBlurGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void setImageSource(Bitmap bitmap) {
        this.bitmapSrc = bitmap;
        this.requestRender();
    }

    private void initView() {
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private void loadTextures() {
        // Generate textures
        GLES20.glGenTextures(2, textures, 0);

        // Load input bitmap
        imageWidth = bitmapSrc.getWidth();
        imageHeight = bitmapSrc.getHeight();
        texRenderer.updateTextureSize(imageWidth, imageHeight);

        // Upload to texture
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmapSrc, 0);

        // Set texture parameters
        GLToolbox.initTexParams();
    }

    private void applyEffect() {
        if (effect != null) {
            effect.apply(textures[0], imageWidth, imageHeight, textures[1]);
        }
    }

    private void renderResult() {
        if (currentEffect != 0) {
            // if no effect is chosen, just render the original bitmap
            texRenderer.renderTexture(textures[1]);
        } else {
            // render the result of applyEffect()
            texRenderer.renderTexture(textures[0]);
        }
    }

    public void setCurrentEffectId(int currentId) {
        this.currentEffect = currentId;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void onResume() {
        super.onResume();
        initialized = false;
    }

    @Override
    public void onDrawFrame(GL10 arg0) {
        if (!initialized) {
            //Only need to do this once
            effectContext = EffectContext.createWithCurrentGlContext();
            texRenderer.init();
            loadTextures();
            initialized = true;
        }
        if (currentEffect != 0) {
            //if an effect is chosen initialize it and apply it to the texture
            effect = ImageBlurUtils.createEffect(currentEffect, effectContext, count);
            applyEffect();
        }
        renderResult();
        bitmap = BitmapSrc(arg0);
    }

    @Override
    public void onSurfaceChanged(GL10 arg0, int width, int height) {
        if (texRenderer != null) {
            texRenderer.updateViewSize(width, height);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    public Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Bitmap BitmapSrc(GL10 gl10) {
        final int mWidth = this.getWidth();
        final int mHeight = this.getHeight();
        IntBuffer ib = IntBuffer.allocate(mWidth * mHeight);
        IntBuffer ibt = IntBuffer.allocate(mWidth * mHeight);
        gl10.glReadPixels(0, 0, mWidth, mHeight, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, ib);
        for (int i = 0; i < mHeight; i++) {
            for (int j = 0; j < mWidth; j++) {
                ibt.put((mHeight - i - 1) * mWidth + j, ib.get(i * mWidth + j));
            }
        }
        Bitmap mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        mBitmap.copyPixelsFromBuffer(ibt);
        mBitmap = FilterImageAdapter.scaleDown(mBitmap, 512, true);
        return mBitmap;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
        this.currentEffect = 0;
    }
}
