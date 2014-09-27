package com.demo;

import android.content.Context;
import android.opengl.GLES20;
import android.view.MotionEvent;

import com.rny925.mankeli.*;

public class Game
  {
  private Object mGameLock;

  private final float FRAME_PER_SECOND = 60.0f;
  private final float FRAME_LENGTH = 1.0f / FRAME_PER_SECOND;
  private final int MAX_ITERATIONS = 10;
  private double mLastTime;
  private float mCumulativeTime;

  private Display mDisplay;
  private Mankeli mMankeli;

  private boolean mInitialized;
  
  private int ax = 0, ay = 0, az = 0;

  public Game(Context context)
    {
    mGameLock = new Object();
    mDisplay = new Display();
    mMankeli = new Mankeli(context, "obj", "model.3ds", "vertexshader.txt", "fragmentshader.txt");
    mInitialized = false;
    }

  public void draw()
    {
    synchronized(mGameLock)
      {
      GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
      mMankeli.setViewMatrix(0.0f, 0.0f, -3.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
      mMankeli.draw();
      }
    }

  public void init(Context context)
    {
    if(mInitialized == false)
      {
      mInitialized = true;
      mMankeli.init(context);
      }
    }

  public void input(MotionEvent event)
    {
    }

  public void resume()
    {
    mLastTime = 0.0f;
    }

  public void setViewPort(int width, int height)
    {
    mDisplay.set(width, height);
    mMankeli.setProjectionMatrix(-mDisplay.getRatio(), mDisplay.getRatio(), -1.0f, 1.0f, 1.0f, 10.0f);
    }

  public void update(long currentTime)
    {
    int iterations = 0;

    synchronized(mGameLock)
      {
      if(mLastTime != 0)
        {
        double dt = currentTime - mLastTime;
        mCumulativeTime += (float)dt / 1000000000;
        mLastTime = currentTime;
        while(mCumulativeTime > FRAME_LENGTH)
          {
          mMankeli.rotate(270, ay / 2, az);
          ax++;
          ay++;
          az++;
          if(ax > 360) ax = 0;
          if(ay > 720) ay = 0;
          if(az > 360) az = 0;
          mCumulativeTime -= FRAME_LENGTH;
          iterations++;
          if(iterations > MAX_ITERATIONS)
            {
            break;
            }
          }
        }
      else
        {
        mLastTime = currentTime;
        }
      }
    }
  }
