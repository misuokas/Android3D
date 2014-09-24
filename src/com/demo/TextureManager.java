package com.demo;

import java.util.ArrayList;

public class TextureManager
  {

  private ArrayList<Texture> mTextures;

  TextureManager(ArrayList<Texture> textures)
    {
    mTextures = textures;
    }

  Texture getTexture(String name)
    {
    Texture texture = null;

    for(int i = 0; i < mTextures.size(); i++)
      {
      if(true == mTextures.get(i).name.equals(name))
        {
        texture = mTextures.get(i);
        }
      }

    return texture;
    }
  }
