package com.demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import android.opengl.GLES20;

public class Shader
    {

    private String mVertexShaderCode;
    private String mFragmentShaderCode;
    private int mVertexShaderHandle;
    private int mFragmentShaderHandle;

    private int loadShader( int type, String shaderCode )
        {
        int shaderHandle = GLES20.glCreateShader( type );

        if( 0 != shaderHandle )
            {
            GLES20.glShaderSource( shaderHandle, shaderCode );
            GLES20.glCompileShader( shaderHandle );

            final int[ ] compileStatus = new int[ 1 ];
            GLES20.glGetShaderiv( shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0 );

            if( 0 == compileStatus[ 0 ] )
                {
                GLES20.glDeleteShader( shaderHandle );
                shaderHandle = 0;
                }
            }

        return shaderHandle;
        }

    private String parseShaderCode( BufferedReader reader )
        {
        String line = null;
        String shaderCode = null;

        do
            {
            try
                {
                line = reader.readLine( );

                if( null != line )
                    {
                    if( null == shaderCode )
                        {
                        shaderCode = new String( "" );
                        }
                    shaderCode = shaderCode + " " + line;
                    }
                } catch( IOException e )
                {
                System.out.println( "Shader::parseVertexShader( ): I/O error." );
                }
            } while( null != line );

        return shaderCode;
        }

    Shader( InputStream vertexShaderInputStream, InputStream fragmentShaderInputStream )
        {
        mVertexShaderCode = load( vertexShaderInputStream );
        mFragmentShaderCode = load( fragmentShaderInputStream );

        if( null == mVertexShaderCode || null == mFragmentShaderCode )
            {
            throw new RuntimeException( "Error loading shaders." );
            }
        }

    int getVertexShader( )
        {
        return mVertexShaderHandle;
        }

    int getFragmentShader( )
        {
        return mFragmentShaderHandle;
        }

    String load( InputStream inputStream )
        {
        BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
        String shaderCode = null;

        if( null != reader )
            {
            shaderCode = parseShaderCode( reader );
            }

        return shaderCode;
        }

    void compile( )
        {
        if( null != mVertexShaderCode )
            {
            mVertexShaderHandle = loadShader( GLES20.GL_VERTEX_SHADER, mVertexShaderCode );
            }

        if( null != mFragmentShaderCode )
            {
            mFragmentShaderHandle = loadShader( GLES20.GL_FRAGMENT_SHADER, mFragmentShaderCode );
            }

        if( 0 == mVertexShaderHandle || 0 == mFragmentShaderHandle )
            {
            throw new RuntimeException( "Error compiling shaders." );
            }
        }
    }
