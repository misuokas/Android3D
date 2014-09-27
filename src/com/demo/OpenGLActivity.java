package com.demo;

import android.app.Activity;
import android.os.Bundle;

public class OpenGLActivity extends Activity
  {
  private OpenGLSurfaceView mOpenGLView;

  @Override
  public void onCreate(Bundle savedInstanceState)
    {
    super.onCreate(savedInstanceState);
    mOpenGLView = new OpenGLSurfaceView(this);
    setContentView(mOpenGLView);
    }

  @Override
  protected void onPause()
    {
    super.onPause();
    mOpenGLView.onPause();
    }

  @Override
  protected void onResume()
    {
    super.onResume();
    mOpenGLView.onResume();
    }
  }