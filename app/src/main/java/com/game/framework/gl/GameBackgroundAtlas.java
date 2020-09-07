package com.game.framework.gl;

import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import com.game.framework.IOManager;
import com.game.framework.exceptions.ResourceException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class GameBackgroundAtlas {
	
	private static GL10 gl;

	
	private static int backTextureId;
	private static int minFilter;
	private static int magFilter;
	private static int width=1024;
	private static int height=1024;
	
	private static final String BACKGROUND_FILE="graphics/backgrounds.png";

    public static void initializeAtlas(GL10 gl10){
    	gl=gl10;
    	loadBackgrounds();
    }
    
	/*public GameBackgroundAtlas(GL10 gl, IOManager ioManager) {
		this.gl = gl;
		this.ioManager = ioManager;
		loadBackgrounds();
	}*/
	
	private static void loadBackgrounds(){
		
		int[] textureIds=new int[1];
		gl.glGenTextures(1, textureIds, 0);
		backTextureId = textureIds[0];
		
		InputStream asset = null;
		
		try {
			asset = IOManager.openAsset(BACKGROUND_FILE);
			Bitmap bitmap = BitmapFactory.decodeStream(asset);
			width = bitmap.getWidth();
	        height = bitmap.getHeight();
			gl.glBindTexture(GL10.GL_TEXTURE_2D, backTextureId);
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
			setMagFilter(GL10.GL_NEAREST);
			setMinFilter(GL10.GL_NEAREST);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			bitmap.recycle();
		} catch (ResourceException e) {
			throw new RuntimeException("Unable to load texture!");
		} finally{
			IOManager.closeAsset();
		}
	}
	
	public static void reloadBackgrounds(){
		loadBackgrounds();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, backTextureId);
	}
	
	public static void releaseBackgrounds(){
		gl.glBindTexture(GL10.GL_TEXTURE_2D, backTextureId);
		int[] textureIds = {backTextureId};
		gl.glDeleteTextures(1, textureIds, 0);	
	}
	
	public static void setActive(){
		gl.glBindTexture(GL10.GL_TEXTURE_2D, backTextureId);
	}
	
	public static int getMinFilter() {
		return minFilter;
	}


	public static int getMagFilter() {
		return magFilter;
	}


	public static void setMinFilter(int minF) {
		minFilter = minF;
	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, minFilter);
	}


	public static void setMagFilter(int magF) {
		magFilter = magF;
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, magFilter);
	}


	public static GameImage getBackgroundImage(int imageX,int imageY,int imageWidth,int imageHeight){
		GameImage backgroundImage = new GameImage(imageX,imageY,imageWidth,imageHeight,width,height);
		return backgroundImage;
	}

}
