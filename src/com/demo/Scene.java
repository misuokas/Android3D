package com.demo;

import android.content.res.Resources;

import java.io.InputStream;
import java.util.ArrayList;

public class Scene
    {

    private ArrayList< Object > mObjects;
    private Program mProgram;
    private Shader mShader;
    private TextureManager mTextureManager;

    Scene( InputStream model, InputStream vertexShader, InputStream fragmentShader,
            InputStream material, Resources resources )
        {
        mObjects = Loader.loadObj( model );
        mShader = new Shader( vertexShader, fragmentShader );
        mTextureManager = new TextureManager( Loader.loadMtl( material, resources ) );
        }

    void draw( )
        {
        for( int i = 0; i < mObjects.size( ); i++ )
            {
            mProgram.draw( mObjects.get( i ) );
            }
        }

    Object getObject( int index )
        {
        return mObjects.get( index );
        }

    void setProjectionMatrix( float left, float right, float bottom, float top, float near,
            float far )
        {
        mProgram.setProjectionMatrix( left, right, bottom, top, near, far );
        }

    void setViewMatrix( float eyeX, float eyeY, float eyeZ, float centerX, float centerY,
            float centerZ, float upX, float upY, float upZ )
        {
        mProgram.setViewMatrix( eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ );
        }

    void initialize( )
        {
        mShader.compile( );
        mProgram = new Program( mShader.getVertexShader( ), mShader.getFragmentShader( ) );

        for( int i = 0; i < mObjects.size( ); i++ )
            {
            mObjects.get( i ).initializeVBO( );
            mObjects.get( i ).initializeTexture( mTextureManager );
            }
        }
    }
