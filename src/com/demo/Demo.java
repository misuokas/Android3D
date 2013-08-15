package com.demo;

import android.content.Context;

class Demo
    {

    private Scene mScene;
    private int ax = 0;
    private int ay = 40;
    private int az = 200;

    public Demo( Context context )
        {
        mScene = new Scene( context.getResources( ).openRawResource( R.raw.model ), context
            .getResources( ).openRawResource( R.raw.vertexshader ), context.getResources( )
            .openRawResource( R.raw.fragmentshader ) );
        }

    public void init( )
        {
        mScene.initialize( );
        }

    public void setViewPort( int width, int height )
        {
        mScene.setProjectionMatrix( -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 10.0f );
        }

    public void draw( )
        {
        mScene.setViewMatrix( 0.0f, 0.0f, -2.5f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f );
        mScene.getObject( 0 ).setRotationXMatrix( ax );
        mScene.getObject( 0 ).setRotationYMatrix( ay );
        mScene.getObject( 0 ).setRotationZMatrix( az );
        mScene.draw( );

        ax++;
        if( ax > 359 )
            {
            ax = 0;
            }
        ay++;
        if( ay > 359 )
            {
            ay = 0;
            }
        az++;
        if( az > 359 )
            {
            az = 0;
            }
        }
    }