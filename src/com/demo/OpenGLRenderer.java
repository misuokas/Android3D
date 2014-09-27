package com.demo;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements GLSurfaceView.Renderer
  {
  private Context mContext;
  private Game mGame;
  private GameThread mGameThread;
  private OpenGLSurfaceView mOpenGLSurfaceView;
  private Object mPauseLock;

  long startTime = System.nanoTime();
  int frames = 0;

  OpenGLRenderer(Context context, OpenGLSurfaceView openGLSurfaceView)
    {
    mContext = context;
    mGame = new Game(mContext);
    mOpenGLSurfaceView = openGLSurfaceView;
    mGameThread = new GameThread(mOpenGLSurfaceView, mGame);
    mPauseLock = new Object();
    }

  @Override
  public void onDrawFrame(GL10 unused)
    {
    mGame.draw();
    }

  @Override
  public void onSurfaceChanged(GL10 unused, int width, int height)
    {
    GLES20.glViewport(0, 0, width, height);
    mGame.setViewPort(width, height);
    System.gc();
    }

  @Override
  public void onSurfaceCreated(GL10 unused, EGLConfig config)
    {
    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    GLES20.glEnable(GLES20.GL_BLEND);
    GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
    GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    GLES20.glDepthFunc(GLES20.GL_LEQUAL);
    GLES20.glDepthMask(true);
    GLES20.glEnable(GLES20.GL_CULL_FACE);
    GLES20.glCullFace(GLES20.GL_BACK);
    mGame.init(mContext);
    if(mGameThread.getState() == Thread.State.NEW)
      {
      mGameThread = new GameThread(mOpenGLSurfaceView, mGame);
      mGameThread.start();
      mGameThread.setRunning(true);
      }
    System.gc();
    }

  public void onPause()
    {
    synchronized(mPauseLock)
      {
      mGameThread.setRunning(false);
      }
    }

  public void onResume()
    {
    if(mGameThread.getState() == Thread.State.TERMINATED)
      {
      mGameThread = new GameThread(mOpenGLSurfaceView, mGame);
      mGameThread.start();
      mGameThread.setRunning(true);
      }
    else if(mGameThread.getState() != Thread.State.NEW)
      {
      synchronized(mPauseLock)
        {
        mGameThread.setRunning(true);
        mGameThread.notify();
        }
      }
    }

  public void onTouchEvent(MotionEvent event)
    {
    mGame.input(event);
    }
  }