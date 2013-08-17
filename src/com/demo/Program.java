package com.demo;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Program
    {

    private int mProgramHandle;

    private float[ ] mProjectionMatrix = new float[ 16 ];
    private float[ ] mViewMatrix = new float[ 16 ];
    private float[ ] mModelViewProjectionMatrix = new float[ 16 ];
    private float[ ] mNormalMatrix = new float[ 16 ];

    private float[ ] mTempMatrix = new float[ 16 ];
    private float[ ] mTempMatrix2 = new float[ 16 ];

    Program( int vertexShaderHandle, int fragmentShaderHandle )
        {
        mProgramHandle = GLES20.glCreateProgram( );

        GLES20.glAttachShader( mProgramHandle, vertexShaderHandle );
        GLES20.glAttachShader( mProgramHandle, fragmentShaderHandle );

        GLES20.glBindAttribLocation( mProgramHandle, 0, "vertex" );
        GLES20.glBindAttribLocation( mProgramHandle, 1, "texture" );
        GLES20.glBindAttribLocation( mProgramHandle, 2, "normal" );

        GLES20.glLinkProgram( mProgramHandle );

        final int[ ] linkStatus = new int[ 1 ];
        GLES20.glGetProgramiv( mProgramHandle, GLES20.GL_LINK_STATUS, linkStatus, 0 );

        if( 0 == linkStatus[ 0 ] )
            {
            GLES20.glDeleteProgram( mProgramHandle );
            mProgramHandle = 0;
            }

        if( mProgramHandle == 0 )
            {
            throw new RuntimeException( "Error creating program." );
            }

        GLES20.glUseProgram( mProgramHandle );
        }

    void draw( Object object )
        {
        Matrix.multiplyMM( mTempMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0 );
        Matrix.multiplyMM( mTempMatrix2, 0, mTempMatrix, 0, object.getRotationXMatrix( ), 0 );
        Matrix.multiplyMM( mTempMatrix, 0, mTempMatrix2, 0, object.getRotationYMatrix( ), 0 );
        Matrix.multiplyMM( mModelViewProjectionMatrix, 0, mTempMatrix, 0,
            object.getRotationZMatrix( ), 0 );

        Matrix.invertM( mNormalMatrix, 0, mModelViewProjectionMatrix, 0 );
        Matrix.transposeM( mNormalMatrix, 0, mNormalMatrix, 0 );

        int modelViewProjectionMatrix = GLES20.glGetUniformLocation( mProgramHandle,
            "modelViewProjectionMatrix" );
        GLES20.glUniformMatrix4fv( modelViewProjectionMatrix, 1, false, mModelViewProjectionMatrix,
            0 );

        int normalMatrix = GLES20.glGetUniformLocation( mProgramHandle, "normalMatrix" );
        GLES20.glUniformMatrix3fv( normalMatrix, 1, false, mNormalMatrix, 0 );

        object.drawVBO( mProgramHandle );
        }

    void setProjectionMatrix( float left, float right, float bottom, float top, float near,
        float far )
        {
        Matrix.setIdentityM( mProjectionMatrix, 0 );
        Matrix.frustumM( mProjectionMatrix, 0, left, right, bottom, top, near, far );
        }

    void setViewMatrix( float eyeX, float eyeY, float eyeZ, float centerX, float centerY,
        float centerZ, float upX, float upY, float upZ )
        {
        Matrix.setIdentityM( mViewMatrix, 0 );
        Matrix.setLookAtM( mViewMatrix, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY,
            upZ );
        }
    }
