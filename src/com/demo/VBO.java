package com.demo;

import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class VBO
    {
    private int[ ] mVerticesBufferId;
    private int[ ] mFacesBufferId;
    private int[ ] mTextureId;

    VBO( FloatBuffer verticesBuffer, ShortBuffer facesBuffer )
        {
        mVerticesBufferId = createFloatBuffer( GLES20.GL_ARRAY_BUFFER, verticesBuffer,
                GLES20.GL_STATIC_DRAW );
        mFacesBufferId = createShortBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, facesBuffer,
                GLES20.GL_STATIC_DRAW );
        }

    int[ ] getVerticesBufferId( )
        {
        return mVerticesBufferId;
        }

    int[ ] getFacesBufferId( )
        {
        return mFacesBufferId;
        }

    void createTexture( Texture texture )
        {
        mTextureId = new int[ 1 ];

        GLES20.glGenTextures( 1, mTextureId, 0 );
        GLES20.glBindTexture( GLES20.GL_TEXTURE_2D, mTextureId[ 0 ] );

        GLES20.glTexParameteri( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_CLAMP_TO_EDGE );
        GLES20.glTexParameteri( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_CLAMP_TO_EDGE );

        GLES20.glTexParameteri( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST );
        GLES20.glTexParameteri( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_NEAREST );
        GLUtils.texImage2D( GLES20.GL_TEXTURE_2D, 0, texture.diffuseTexture, 0 );
        }

    int[ ] getTextureId( )
        {
        return mTextureId;
        }

    private int[ ] createFloatBuffer( int target, FloatBuffer buffer, int usage )
        {
        int[ ] id = new int[ 1 ];

        GLES20.glGenBuffers( 1, id, 0 );
        GLES20.glBindBuffer( target, id[ 0 ] );
        GLES20.glBufferData( target, buffer.capacity( ) * 4, buffer, usage );
        GLES20.glBindBuffer( target, 0 );

        return id;
        }

    private int[ ] createShortBuffer( int target, ShortBuffer buffer, int usage )
        {
        int[ ] id = new int[ 1 ];

        GLES20.glGenBuffers( 1, id, 0 );
        GLES20.glBindBuffer( target, id[ 0 ] );
        GLES20.glBufferData( target, buffer.capacity( ) * 2, buffer, usage );
        GLES20.glBindBuffer( target, 0 );

        return id;
        }
    }
