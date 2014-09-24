package com.demo;

import android.content.Context;

class Demo
  {

  private Scene mScene;
  private int ax = 0;
  private int ay = 0;
  private int az = 0;

  public Demo(Context context)
    {
    mScene = new Scene(context.getResources().openRawResource(R.raw.model), context.getResources().openRawResource(
        R.raw.vertexshader), context.getResources().openRawResource(R.raw.fragmentshader));
    }

  public void init()
    {
    mScene.initialize();
    }

  public void setViewPort(int width, int height)
    {
    mScene.setProjectionMatrix(-1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 10.0f);
    }

  public void draw()
    {
    mScene.setViewMatrix(0.0f, 0.0f, -3.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    for(int i = 0; i < 4; i++)
      {
      mScene.getObject(i).setRotationXMatrix(ax);
      mScene.getObject(i).setRotationYMatrix(ay);
      mScene.getObject(i).setRotationZMatrix(az);
      }
    mScene.draw();

    ax++;
    if(ax > 359)
      {
      ax = 0;
      }
    ay++;
    if(ay > 359)
      {
      ay = 0;
      }
    az++;
    if(az > 359)
      {
      az = 0;
      }
    }
  }