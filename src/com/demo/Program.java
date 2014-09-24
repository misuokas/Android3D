package com.demo;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Program
  {

  private int mProgramHandle;

  private float[] mProjectionMatrix = new float[16];
  private float[] mModelMatrix = new float[16];
  private float[] mViewMatrix = new float[16];
  private float[] mModelViewProjectionMatrix = new float[16];

  private float[] mTempMatrix = new float[16];

  Program(int vertexShaderHandle, int fragmentShaderHandle)
    {
    mProgramHandle = GLES20.glCreateProgram();

    GLES20.glAttachShader(mProgramHandle, vertexShaderHandle);
    GLES20.glAttachShader(mProgramHandle, fragmentShaderHandle);

    GLES20.glLinkProgram(mProgramHandle);

    final int[] linkStatus = new int[1];
    GLES20.glGetProgramiv(mProgramHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

    if(0 == linkStatus[0])
      {
      GLES20.glDeleteProgram(mProgramHandle);
      mProgramHandle = 0;
      }

    if(mProgramHandle == 0)
      {
      throw new RuntimeException("Error creating program.");
      }

    GLES20.glUseProgram(mProgramHandle);
    }

  void draw(Object object)
    {
    Matrix.multiplyMM(mTempMatrix, 0, object.getRotationXMatrix(), 0, object.getRotationYMatrix(), 0);
    Matrix.multiplyMM(mModelMatrix, 0, mTempMatrix, 0, object.getRotationZMatrix(), 0);

    Matrix.multiplyMM(mTempMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
    Matrix.multiplyMM(mModelViewProjectionMatrix, 0, mTempMatrix, 0, mModelMatrix, 0);

    int modelViewProjectionMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, "modelViewProjectionMatrix");
    GLES20.glUniformMatrix4fv(modelViewProjectionMatrixHandle, 1, false, mModelViewProjectionMatrix, 0);

    int modelMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, "modelMatrix");
    GLES20.glUniformMatrix4fv(modelMatrixHandle, 1, false, mModelMatrix, 0);

    int viewMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, "viewMatrix");
    GLES20.glUniformMatrix4fv(viewMatrixHandle, 1, false, mViewMatrix, 0);
    
    float[] lightPosition = new float[3];
    lightPosition[0] = 2.0f;
    lightPosition[1] = 2.0f;
    lightPosition[2] = -5.0f;
    int lightHandle = GLES20.glGetUniformLocation(mProgramHandle, "lightPosition");
    GLES20.glUniform3f(lightHandle, lightPosition[0], lightPosition[1], lightPosition[2]);
    
    object.drawVBO(mProgramHandle);
    }

  void setProjectionMatrix(float left, float right, float bottom, float top, float near, float far)
    {
    Matrix.setIdentityM(mProjectionMatrix, 0);
    Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

  void setViewMatrix(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY,
      float upZ)
    {
    Matrix.setIdentityM(mViewMatrix, 0);
    Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }
  }
