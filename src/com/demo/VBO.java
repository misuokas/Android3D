package com.demo;

import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class VBO
    {
    private int[ ] mVerticesBufferId;
    private int[ ] mFacesBufferId;

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
