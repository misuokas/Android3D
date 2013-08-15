package com.demo;

import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class VBO
    {
    private int[ ] mVerticesBufferId;
    private int[ ] mTextureVerticesBufferId;
    private int[ ] mVertexNormalsBufferId;
    private int[ ] mFacesBufferId;

    int[ ] v;
    int[ ] f;

    VBO( Object object )
        {
        mVerticesBufferId = createFloatBuffer( GLES20.GL_ARRAY_BUFFER, object.getVerticesBuffer( ),
            GLES20.GL_STATIC_DRAW );

        if( null != object.getTextureVerticesBuffer( )
            && 0 != object.getTextureVerticesBuffer( ).capacity( ) )
            {
            mTextureVerticesBufferId = createFloatBuffer( GLES20.GL_ARRAY_BUFFER,
                object.getTextureVerticesBuffer( ), GLES20.GL_STATIC_DRAW );
            }
        else
            {
            mTextureVerticesBufferId = null;
            }

        if( null != object.getVertexNormalsBuffer( )
            && 0 != object.getVertexNormalsBuffer( ).capacity( ) )
            {
            mVertexNormalsBufferId = createFloatBuffer( GLES20.GL_ARRAY_BUFFER,
                object.getVertexNormalsBuffer( ), GLES20.GL_STATIC_DRAW );
            }
        else
            {
            mVertexNormalsBufferId = null;
            }

        mFacesBufferId = createIntBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, object.getFacesBuffer( ),
            GLES20.GL_STATIC_DRAW );
        }

    int[ ] getVerticesBufferId( )
        {
        return mVerticesBufferId;
        }

    int[ ] getTextureVerticesBufferId( )
        {
        return mTextureVerticesBufferId;
        }

    int[ ] getVertexNormalsBufferId( )
        {
        return mVertexNormalsBufferId;
        }

    int[ ] getFacesNormalsBufferId( )
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

    private int[ ] createIntBuffer( int target, IntBuffer buffer, int usage )
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
