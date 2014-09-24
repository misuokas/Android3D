package com.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public final class FileObj
  {

  private static final String FACE = "f";
  private static final String OBJECT = "o";
  private static final String TEXTURE_VERTEX = "vt";
  private static final String VERTEX = "v";
  private static final String VERTEX_NORMAL = "vn";
  private static final String MATERIAL = "usemtl";

  static ArrayList<Object> load(BufferedReader reader)
    {
    return parse(reader);
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

  private static ArrayList<Object> parse(BufferedReader reader)
    {
    String line = null;
    ArrayList<Integer> indices = new ArrayList<Integer>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<Float> vertices = new ArrayList<Float>();
    ArrayList<Float> textureVertices = new ArrayList<Float>();
    ArrayList<Float> vertexNormals = new ArrayList<Float>();
    ArrayList<String> materials = new ArrayList<String>();
    ArrayList<Integer> faces = new ArrayList<Integer>();
    ArrayList<Object> objects = null;

    do
      {
      try
        {
        line = reader.readLine();
        if(null != line)
          {
          if(true == isField(line, OBJECT))
            {
            parseObject(line, OBJECT, names);
            indices.add(faces.size());
            }
          if(true == isField(line, VERTEX))
            {
            parseVertex(line, VERTEX, vertices);
            }
          if(true == isField(line, TEXTURE_VERTEX))
            {
            parseTextureVertex(line, TEXTURE_VERTEX, textureVertices);
            }
          if(true == isField(line, VERTEX_NORMAL))
            {
            parseVertex(line, VERTEX_NORMAL, vertexNormals);
            }
          if(true == isField(line, FACE))
            {
            parseFace(line, FACE, faces);
            }
          if(true == isField(line, MATERIAL))
            {
            parseMaterial(line, MATERIAL, materials);
            }
          }
        } catch(IOException e)
        {
        }
      } while(null != line);

    if(0 != vertices.size() && 0 != faces.size())
      {
      if(0 == names.size())
        {
        names.add("Unknown");
        indices.add(0);
        }
      indices.add(faces.size());

      objects = new ArrayList<Object>();

      for(int i = 0; i < names.size(); i++)
        {
        objects
            .add(createObject(names.get(i), i, indices, vertices, textureVertices, vertexNormals, faces, materials.get(i)));
        }
      }

    return objects;
    }

  private static void parseObject(String line, String field, ArrayList<String> list)
    {
    Scanner scanner = new Scanner(line);

    if(true == scanner.hasNext() && 0 == scanner.next().compareTo(field))
      {
      if(true == scanner.hasNext())
        {
        list.add(scanner.next());
        }
      }

    scanner.close();
    }

  private static void parseVertex(String line, String field, ArrayList<Float> list)
    {
    Scanner scanner = new Scanner(line);

    float x = 0.0f;
    float y = 0.0f;
    float z = 0.0f;
    float w = 1.0f;

    if(true == scanner.hasNext() && 0 == scanner.next().compareTo(field))
      {
      if(true == scanner.hasNext())
        {
        x = Float.valueOf(scanner.next());
        }

      if(true == scanner.hasNext())
        {
        y = Float.valueOf(scanner.next());
        }

      if(true == scanner.hasNext())
        {
        z = Float.valueOf(scanner.next());
        }

      if(true == scanner.hasNext())
        {
        w = Float.valueOf(scanner.next());
        }

      list.add(x / w);
      list.add(y / w);
      list.add(z / w);
      }

    scanner.close();
    }

  private static void parseTextureVertex(String line, String field, ArrayList<Float> list)
    {
    Scanner scanner = new Scanner(line);
    float u = 0.0f;
    float v = 0.0f;
    float w = 0.0f;

    if(true == scanner.hasNext() && 0 == scanner.next().compareTo(field))
      {
      if(true == scanner.hasNext())
        {
        u = Float.valueOf(scanner.next());
        }

      if(true == scanner.hasNext())
        {
        v = Float.valueOf(scanner.next());
        }

      if(true == scanner.hasNext())
        {
        w = Float.valueOf(scanner.next());
        }

      list.add(u);
      list.add(v);
      list.add(w);
      }

    scanner.close();
    }

  private static void parseFace(String line, String field, ArrayList<Integer> list)
    {
    Scanner scanner = new Scanner(line);
    int v = 0;
    int t = 0;
    int n = 0;

    if(true == scanner.hasNext() && 0 == scanner.next().compareTo(field))
      {
      for(int i = 0; i < 3; i++)
        {
        if(true == scanner.hasNext())
          {
          String s[] = scanner.next().split("/");
          v = Integer.valueOf(s[0]);
          if(2 == s.length)
            {
            t = Integer.valueOf(s[1]);
            }
          else if(3 == s.length)
            {
            if(0 != s[1].length())
              {
              t = Integer.valueOf(s[1]);
              }
            n = Integer.valueOf(s[2]);
            }

          list.add(v);
          list.add(t);
          list.add(n);
          }
        }
      }

    scanner.close();
    }

  private static void parseMaterial(String line, String field, ArrayList<String> list)
    {
    Scanner scanner = new Scanner(line);

    if(true == scanner.hasNext() && 0 == scanner.next().compareTo(field))
      {
      if(true == scanner.hasNext())
        {
        list.add(scanner.next());
        }
      }

    scanner.close();
    }

  private static Object createObject(String name, int o, ArrayList<Integer> indices, ArrayList<Float> vertices,
      ArrayList<Float> textureVertices, ArrayList<Float> vertexNormals, ArrayList<Integer> faces, String material)
    {
    Object object = null;

    int start = indices.get(o) / 3;
    int end = indices.get(o + 1) / 3;

    float[] verticesData = new float[(end - start) * 3 * 3];
    int[] facesData = new int[end - start];

    for(int i = start; i < end; i++)
      {
      int v = faces.get(i * 3) - 1;
      verticesData[(i - start) * 9] = vertices.get(v * 3);
      verticesData[(i - start) * 9 + 1] = vertices.get(v * 3 + 1);
      verticesData[(i - start) * 9 + 2] = vertices.get(v * 3 + 2);

      if(0 != vertexNormals.size())
        {
        int n = faces.get(i * 3 + 2) - 1;
        verticesData[(i - start) * 9 + 3] = vertexNormals.get(n * 3);
        verticesData[(i - start) * 9 + 4] = vertexNormals.get(n * 3 + 1);
        verticesData[(i - start) * 9 + 5] = vertexNormals.get(n * 3 + 2);
        }

      if(0 != textureVertices.size())
        {
        int t = faces.get(i * 3 + 1) - 1;
        verticesData[(i - start) * 9 + 6] = textureVertices.get(t * 3);
        verticesData[(i - start) * 9 + 7] = textureVertices.get(t * 3 + 1);
        verticesData[(i - start) * 9 + 8] = textureVertices.get(t * 3 + 2);
        }

      facesData[(i - start)] = i - start;
      }

    object = new Object(name, verticesData, facesData, material);

    return object;
    }
  }
