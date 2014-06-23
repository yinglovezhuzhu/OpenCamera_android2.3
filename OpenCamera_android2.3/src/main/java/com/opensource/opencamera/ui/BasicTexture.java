/*
 * Copyright (C) 2014 The Android Open Source Project.
 *
 *        yinglovezhuzhu@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.opensource.opencamera.ui;

import javax.microedition.khronos.opengles.GL11;

abstract class BasicTexture implements Texture {

    protected static final int UNSPECIFIED = -1;

    public static final int STATE_UNLOADED = 0;
    public static final int STATE_LOADED = 1;
    public static final int STATE_ERROR = -1;

    protected GL11 mGL;

    protected int mId;
    protected int mState;

    protected int mWidth = UNSPECIFIED;
    protected int mHeight = UNSPECIFIED;

    protected int mTextureWidth;
    protected int mTextureHeight;

    protected BasicTexture(GL11 gl, int id, int state) {
        mGL = gl;
        mId = id;
        mState = state;
    }

    protected BasicTexture() {
        this(null, 0, STATE_UNLOADED);
    }

    protected void setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    /**
     * Sets the size of the texture. Due to the limit of OpenGL, the texture
     * size must be of power of 2, the size of the content may not be the size
     * of the texture.
     */
    protected void setTextureSize(int width, int height) {
        mTextureWidth = width;
        mTextureHeight = height;
    }

    public int getId() {
        return mId;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void deleteFromGL() {
        if (mState == STATE_LOADED) {
            mGL.glDeleteTextures(1, new int[]{mId}, 0);
        }
        mState = STATE_UNLOADED;
    }

    public void draw(GLRootView root, int x, int y) {
        root.drawTexture(this, x, y, mWidth, mHeight);
    }

    public void draw(GLRootView root, int x, int y, int w, int h) {
        root.drawTexture(this, x, y, w, h);
    }

    abstract protected boolean bind(GLRootView root, GL11 gl);
}
