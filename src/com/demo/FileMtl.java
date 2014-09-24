package com.demo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public final class FileMtl
  {

  private static final String MATERIAL_NAME = "newmtl";
  private static final String DIFFUSE_TEXTURE = "map_Kd";

  static ArrayList<Texture> load(BufferedReader reader, Resources resources)
    {
    return parse(reader, resources);
    }

  private static boolean isField(String line, String field)
    {
    boolean returnValue = false;

    if(true == line.startsWith(field + " "))
      {
      returnValue = true;
      }

    return returnValue;
    }

  private static ArrayList<Texture> parse(BufferedReader reader, Resources resources)
    {
    String line = null;
    Texture texture = null;
    ArrayList<Texture> textures = new ArrayList<Texture>();

    do
      {
      try
        {
        line = reader.readLine();
        if(null != line)
          {
          if(true == isField(line, MATERIAL_NAME))
            {
            if(null != texture)
              {
              textures.add(texture);
              }
            texture = new Texture();
            texture.name = parseName(line, MATERIAL_NAME);
            }
          if(true == isField(line, DIFFUSE_TEXTURE))
            {
            texture.diffuseTexture = parseBitmap(line, DIFFUSE_TEXTURE, resources);
            }
          }
        } catch(IOException e)
        {
        }
      } while(null != line);

    if(null != texture)
      {
      textures.add(texture);
      }

    return textures;
    }

  private static String parseName(String line, String field)
    {
    Scanner scanner = new Scanner(line);
    String s = null;

    if(true == scanner.hasNext() && 0 == scanner.next().compareTo(field))
      {
      if(true == scanner.hasNext())
        {
        s = scanner.next();
        }
      }

    scanner.close();

    return s;
    }

  private static Bitmap parseBitmap(String line, String field, Resources resources)
    {
    Scanner scanner = new Scanner(line);
    Bitmap b = null;

    if(true == scanner.hasNext() && 0 == scanner.next().compareTo(field))
      {
      if(true == scanner.hasNext())
        {
        String s = scanner.next();
        s = s.substring(0, s.lastIndexOf('.'));
        int id = resources.getIdentifier(s, "raw", "com.demo");
        b = BitmapFactory.decodeResource(resources, id);
        }
      }

    scanner.close();

    return b;
    }
  }
