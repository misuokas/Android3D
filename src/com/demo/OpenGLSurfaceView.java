package com.demo;

import android.content.Context;
import android.opengl.GLSurfaceView;

class OpenGLSurfaceView extends GLSurfaceView
  {

  public OpenGLSurfaceView(Context context)
    {
    super(context);
    setEGLConfigChooser(8, 8, 8, 8, 16, 0);
    setEGLContextClientVersion(2);
    setRenderer(new OpenGLRenderer(context));
    setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
  }