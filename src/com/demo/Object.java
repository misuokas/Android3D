package com.demo;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class Object
    {

    private String mName;

    private float[ ] mVertices;
    private float[ ] mTextureVertices;
    private float[ ] mVertexNormals;
    private int[ ] mFaces;

    private FloatBuffer mVerticesBuffer;
    private FloatBuffer mTextureVerticesBuffer;
    private FloatBuffer mVertexNormalsBuffer;
    private IntBuffer mFacesBuffer;

    private VBO mVBO;

    private float[ ] mRotationXMatrix;
    private float[ ] mRotationYMatrix;
    private float[ ] mRotationZMatrix;

    Object( String name, float[ ] vertices, float[ ] textureVertices, float[ ] vertexNormals,
        int[ ] faces )
        {
        mVertices = vertices;
        mTextureVertices = textureVertices;
        mVertexNormals = vertexNormals;
        mFaces = faces;

        mName = name;
        mVerticesBuffer = createFloatBuffer( mVertices );
        if( null != mTextureVertices )
            {
            mTextureVerticesBuffer = createFloatBuffer( mTextureVertices );
            }
        if( null != mVertexNormals )
            {
            mVertexNormalsBuffer = createFloatBuffer( mVertexNormals );
            }
        mFacesBuffer = createIntegerBuffer( mFaces );

        mVBO = null;

        mRotationXMatrix = new float[ 16 ];
        mRotationYMatrix = new float[ 16 ];
        mRotationZMatrix = new float[ 16 ];

        Matrix.setIdentityM( mRotationXMatrix, 0 );
        Matrix.setIdentityM( mRotationYMatrix, 0 );
        Matrix.setIdentityM( mRotationZMatrix, 0 );
        }

    void initializeVBO( )
        {
        mVBO = new VBO( this );
        }

    void drawVBO( int programHandle )
        {
        int vertexHandle = GLES20.glGetAttribLocation( programHandle, "vertex" );
        GLES20.glBindBuffer( GLES20.GL_ARRAY_BUFFER, mVBO.getVerticesBufferId( )[ 0 ] );
        GLES20.glEnableVertexAttribArray( vertexHandle );
        GLES20.glVertexAttribPointer( vertexHandle, 3, GLES20.GL_FLOAT, false, 3 * 4, 0 );

        int textureHandle = GLES20.glGetAttribLocation( programHandle, "texture" );
        if( null != mVBO.getTextureVerticesBufferId( ) )
            {
            GLES20.glBindBuffer( GLES20.GL_ARRAY_BUFFER, mVBO.getTextureVerticesBufferId( )[ 0 ] );
            GLES20.glEnableVertexAttribArray( textureHandle );
            GLES20.glVertexAttribPointer( textureHandle, 3, GLES20.GL_FLOAT, false, 3 * 4, 0 );
            }

        int normalHandle = GLES20.glGetAttribLocation( programHandle, "normal" );
        if( null != mVBO.getVertexNormalsBufferId( ) )
            {
            GLES20.glBindBuffer( GLES20.GL_ARRAY_BUFFER, mVBO.getVertexNormalsBufferId( )[ 0 ] );
            GLES20.glEnableVertexAttribArray( normalHandle );
            GLES20.glVertexAttribPointer( normalHandle, 3, GLES20.GL_FLOAT, false, 3 * 4, 0 );
            }

        GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, mVBO.getFacesNormalsBufferId( )[ 0 ] );

        GLES20.glDrawElements( GLES20.GL_TRIANGLES, mFacesBuffer.capacity( ),
            GLES20.GL_UNSIGNED_INT, 0 );

        GLES20.glDisableVertexAttribArray( vertexHandle );
        if( null != mVBO.getTextureVerticesBufferId( ) )
            {
            GLES20.glDisableVertexAttribArray( textureHandle );
            }
        if( null != mVBO.getVertexNormalsBufferId( ) )
            {
            GLES20.glDisableVertexAttribArray( normalHandle );
            }

        GLES20.glBindBuffer( GLES20.GL_ARRAY_BUFFER, 0 );
        GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, 0 );
        }

    String getName( )
        {
        return mName;
        }

    FloatBuffer getVerticesBuffer( )
        {
        return mVertexNormalsBuffer;
        }

    FloatBuffer getTextureVerticesBuffer( )
        {
        return mTextureVerticesBuffer;
        }

    FloatBuffer getVertexNormalsBuffer( )
        {
        return mVerticesBuffer;
        }

    IntBuffer getFacesBuffer( )
        {
        return mFacesBuffer;
        }

    float[ ] getRotationXMatrix( )
        {
        return mRotationXMatrix;
        }

    float[ ] getRotationYMatrix( )
        {
        return mRotationYMatrix;
        }

    float[ ] getRotationZMatrix( )
        {
        return mRotationZMatrix;
        }

    void setRotationXMatrix( int angle )
        {
        Matrix.setRotateM( mRotationXMatrix, 0, angle, 1.0f, 0.0f, 0.0f );
        }

    void setRotationYMatrix( int angle )
        {
        Matrix.setRotateM( mRotationYMatrix, 0, angle, 0.0f, 1.0f, 0.0f );
        }

    void setRotationZMatrix( int angle )
        {
        Matrix.setRotateM( mRotationZMatrix, 0, angle, 0.0f, 0.0f, 1.0f );
        }

    private FloatBuffer createFloatBuffer( float[ ] array )
        {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect( array.length * 4 );
        byteBuffer.order( ByteOrder.nativeOrder( ) );

        FloatBuffer buffer = byteBuffer.asFloatBuffer( );
        buffer.put( array );
        buffer.position( 0 );

        return buffer;
        }

    private IntBuffer createIntegerBuffer( int[ ] array )
        {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect( array.length * 4 );
        byteBuffer.order( ByteOrder.nativeOrder( ) );

        IntBuffer buffer = byteBuffer.asIntBuffer( );
        buffer.put( array );
        buffer.position( 0 );

        return buffer;
        }

    private ShortBuffer createShortBuffer( short[ ] array )
        {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect( array.length * 2 );
        byteBuffer.order( ByteOrder.nativeOrder( ) );

        ShortBuffer buffer = byteBuffer.asShortBuffer( );
        buffer.put( array );
        buffer.position( 0 );

        return buffer;
        }
    }
