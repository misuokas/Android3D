package com.demo;

public class GameThread extends Thread
  {
  private boolean mRunning;
  private Game mGame;
  private OpenGLSurfaceView mRenderer;

  public GameThread(OpenGLSurfaceView renderer, Game game)
    {
    mRunning = false;

    mGame = game;
    mRenderer = renderer;
    }

  public void setRunning(boolean running)
    {
    mRunning = running;

    if(mRunning == true)
      {
      mGame.resume();
      }
    }

  @Override
  public void run()
    {
    while(mRunning)
      {
      mGame.update(System.nanoTime());
      mRenderer.requestRender();
      }
    }
  }
