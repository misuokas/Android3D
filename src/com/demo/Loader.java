package com.demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public final class Loader
    {

    static ArrayList< Object > loadObj( InputStream inputStream )
        {
        return FileObj.load( new BufferedReader( new InputStreamReader( inputStream ) ) );
        }
    }
