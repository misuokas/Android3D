package com.demo;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Object
  {

  private final int SIZE_VERTEX = 3;
  private final int SIZE_VERTEX_NORMAL = 3;
  private final int SIZE_TEXTURE_VERTEX = 2;

  private final int STRIDE_VERTEX = SIZE_VERTEX * 4 + SIZE_VERTEX_NORMAL * 4 + SIZE_TEXTURE_VERTEX * 4 + 4;

  private final int OFFSET_VERTEX = 0;
  private final int OFFSET_VERTEX_NORMAL = SIZE_VERTEX * 4;
  private final int OFFSET_TEXTURE_VERTEX = SIZE_VERTEX * 4 + SIZE_VERTEX_NORMAL * 4;

  private String mName;

  private float[] mVertices;
  private int[] mFaces;

  private FloatBuffer mVerticesBuffer;
  private IntBuffer mFacesBuffer;

  private VBO mVBO;

  private float[] mRotationXMatrix;
  private float[] mRotationYMatrix;
  private float[] mRotationZMatrix;

  Object(String name, float[] vertices, int[] faces, String texture)
    {
    mName = name;
    mVertices = vertices;
    mFaces = faces;

    mVerticesBuffer = createFloatBuffer(mVertices);
    mFacesBuffer = createIntBuffer(mFaces);

    mVBO = null;

    mRotationXMatrix = new float[16];
    mRotationYMatrix = new float[16];
    mRotationZMatrix = new float[16];

    Matrix.setIdentityM(mRotationXMatrix, 0);
    Matrix.setIdentityM(mRotationYMatrix, 0);
    Matrix.setIdentityM(mRotationZMatrix, 0);
    }

  void initializeVBO()
    {
    mVBO = new VBO(mVerticesBuffer, mFacesBuffer);
    }

  void initializeTexture(TextureManager textureManager)
    {
    }

  void drawVBO(int programHandle)
    {
    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVBO.getVerticesBufferId()[0]);
    int vertexHandle = GLES20.glGetAttribLocation(programHandle, "vertex");
    GLES20.glEnableVertexAttribArray(vertexHandle);
    GLES20.glVertexAttribPointer(vertexHandle, SIZE_VERTEX, GLES20.GL_FLOAT, false, STRIDE_VERTEX, OFFSET_VERTEX);

    int normalHandle = GLES20.glGetAttribLocation(programHandle, "normalVertex");
    GLES20.glEnableVertexAttribArray(normalHandle);
    GLES20.glVertexAttribPointer(normalHandle, SIZE_VERTEX_NORMAL, GLES20.GL_FLOAT, false, STRIDE_VERTEX, OFFSET_VERTEX_NORMAL);

    int textureHandle = GLES20.glGetAttribLocation(programHandle, "textureVertex");
    GLES20.glEnableVertexAttribArray(textureHandle);
    GLES20.glVertexAttribPointer(textureHandle, SIZE_TEXTURE_VERTEX, GLES20.GL_FLOAT, false, STRIDE_VERTEX,
        OFFSET_TEXTURE_VERTEX);

    GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mVBO.getFacesBufferId()[0]);

    int ambientTexturedHandle = GLES20.glGetUniformLocation(programHandle, "isAmbientTextured");
    GLES20.glEnableVertexAttribArray(ambientTexturedHandle);
    GLES20.glUniform1i(ambientTexturedHandle, 0);
    int colorHandle = GLES20.glGetUniformLocation(programHandle, "ambientColor");
    GLES20.glEnableVertexAttribArray(colorHandle);
    float[] ambient = new float[4];
    ambient[0] = 0.4f;
    ambient[1] = 0.36f;
    ambient[2] = 0.05f;
    ambient[3] = 1.0f;
    GLES20.glUniform4fv(colorHandle, 1, ambient, 0);

    int diffuseTexturedHandle = GLES20.glGetUniformLocation(programHandle, "isDiffuseTextured");
    GLES20.glEnableVertexAttribArray(diffuseTexturedHandle);
    GLES20.glUniform1i(diffuseTexturedHandle, 0);
    colorHandle = GLES20.glGetUniformLocation(programHandle, "diffuseColor");
    GLES20.glEnableVertexAttribArray(colorHandle);
    float[] diffuse = new float[4];
    diffuse[0] = 0.8f;
    diffuse[1] = 0.77f;
    diffuse[2] = 0.10f;
    diffuse[3] = 1.0f;
    GLES20.glUniform4fv(colorHandle, 1, diffuse, 0);

    int specularTexturedHandle = GLES20.glGetUniformLocation(programHandle, "isSpecularTextured");
    GLES20.glEnableVertexAttribArray(specularTexturedHandle);
    GLES20.glUniform1i(specularTexturedHandle, 0);
    colorHandle = GLES20.glGetUniformLocation(programHandle, "specularColor");
    GLES20.glEnableVertexAttribArray(colorHandle);
    float[] specular = new float[4];
    specular[0] = 0.8f;
    specular[1] = 0.77f;
    specular[2] = 0.10f;
    specular[3] = 1.0f;
    GLES20.glUniform4fv(colorHandle, 1, specular, 0);
 
    GLES20.glDrawElements(GLES20.GL_TRIANGLES, mFacesBuffer.capacity(), GLES20.GL_UNSIGNED_INT, 0);

    GLES20.glDisableVertexAttribArray(vertexHandle);
    GLES20.glDisableVertexAttribArray(normalHandle);
    GLES20.glDisableVertexAttribArray(textureHandle);

    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

  String getName()
    {
    return mName;
    }

  float[] getRotationXMatrix()
    {
    return mRotationXMatrix;
    }

  float[] getRotationYMatrix()
    {
    return mRotationYMatrix;
    }

  float[] getRotationZMatrix()
    {
    return mRotationZMatrix;
    }

  void setRotationXMatrix(int angle)
    {
    Matrix.setRotateM(mRotationXMatrix, 0, angle, 1.0f, 0.0f, 0.0f);
    }

  void setRotationYMatrix(int angle)
    {
    Matrix.setRotateM(mRotationYMatrix, 0, angle, 0.0f, 1.0f, 0.0f);
    }

  void setRotationZMatrix(int angle)
    {
    Matrix.setRotateM(mRotationZMatrix, 0, angle, 0.0f, 0.0f, 1.0f);
    }

  private FloatBuffer createFloatBuffer(float[] array)
    {
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(array.length * 4);
    byteBuffer.order(ByteOrder.nativeOrder());

    FloatBuffer buffer = byteBuffer.asFloatBuffer();
    buffer.put(array);
    buffer.position(0);

    return buffer;
    }

  private IntBuffer createIntBuffer(int[] array)
    {
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(array.length * 4);
    byteBuffer.order(ByteOrder.nativeOrder());

    IntBuffer buffer = byteBuffer.asIntBuffer();
    buffer.put(array);
    buffer.position(0);

    return buffer;
    }
  }
