package com.demo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

class OpenGLSurfaceView extends GLSurfaceView
  {
  private final OpenGLRenderer mOpenGLRenderer;

  public OpenGLSurfaceView(Context context)
    {
    super(context);
    setEGLConfigChooser(new GLSurfaceView.EGLConfigChooser()
      {
        @Override
        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display)
          {
          int[] config = new int[1];
          EGLConfig[] eglConfig = new EGLConfig[1];

          int[] configAttributes = new int[]{EGL10.EGL_RED_SIZE, 8, EGL10.EGL_GREEN_SIZE, 8, EGL10.EGL_BLUE_SIZE, 8,
              EGL10.EGL_ALPHA_SIZE, 8, EGL10.EGL_DEPTH_SIZE, 16, EGL10.EGL_STENCIL_SIZE, EGL10.EGL_DONT_CARE, EGL10.EGL_NONE};
          egl.eglChooseConfig(display, configAttributes, eglConfig, 1, config);

          if(config[0] <= 0)
            {
            configAttributes = new int[]{EGL10.EGL_RED_SIZE, 5, EGL10.EGL_GREEN_SIZE, 6, EGL10.EGL_BLUE_SIZE, 5,
                EGL10.EGL_ALPHA_SIZE, EGL10.EGL_DONT_CARE, EGL10.EGL_DEPTH_SIZE, 16, EGL10.EGL_STENCIL_SIZE,
                EGL10.EGL_DONT_CARE, EGL10.EGL_NONE};
            egl.eglChooseConfig(display, configAttributes, eglConfig, 1, config);
            }

          if(config[0] <= 0)
            {
            configAttributes = new int[]{EGL10.EGL_RED_SIZE, EGL10.EGL_DONT_CARE, EGL10.EGL_GREEN_SIZE, EGL10.EGL_DONT_CARE,
                EGL10.EGL_BLUE_SIZE, EGL10.EGL_DONT_CARE, EGL10.EGL_ALPHA_SIZE, EGL10.EGL_DONT_CARE, EGL10.EGL_DEPTH_SIZE,
                EGL10.EGL_DONT_CARE, EGL10.EGL_STENCIL_SIZE, EGL10.EGL_DONT_CARE, EGL10.EGL_NONE};
            egl.eglChooseConfig(display, configAttributes, eglConfig, 1, config);
            }

          if(config[0] <= 0)
            {
            throw new RuntimeException("Couldn't create OpenGL config.");
            }
          return eglConfig[0];
          }
      });
    getHolder().setFormat(PixelFormat.RGBA_8888);
    setEGLContextClientVersion(2);
    mOpenGLRenderer = new OpenGLRenderer(context, this);
    setRenderer(mOpenGLRenderer);
    setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

  @Override
  public void onPause()
    {
    mOpenGLRenderer.onPause();
    }

  @Override
  public boolean performClick()
    {
    return super.performClick();
    }

  @Override
  public void onResume()
    {
    mOpenGLRenderer.onResume();
    }

  @Override
  public boolean onTouchEvent(MotionEvent event)
    {
    mOpenGLRenderer.onTouchEvent(event);
    requestRender();
    if(event.getAction() == MotionEvent.ACTION_DOWN)
      {
      performClick();
      }
    return true;
    }
  }