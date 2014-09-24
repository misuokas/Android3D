package com.demo;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public final class Loader
  {

  static ArrayList<Object> loadObj(InputStream inputStream)
    {
    return FileObj.load(new BufferedReader(new InputStreamReader(inputStream)));
    }

  static ArrayList<Texture> loadMtl(InputStream inputStream, Resources resources)
    {
    return FileMtl.load(new BufferedReader(new InputStreamReader(inputStream)), resources);
    }
  }
