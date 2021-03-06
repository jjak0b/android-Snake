package com.game.framework.gl;

import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import com.game.framework.IOManager;
import com.game.framework.exceptions.ResourceException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class GameAtlas {
	
	private static GL10 gl;
	
	private static int atlasTextureId;
	private static int minFilter;
	private static int magFilter;
	private static int width=1024;
	private static int height=512;
	
	private static final String ATLAS_FILE="graphics/atlas.png";

	
    public static void initializeAtlas(GL10 gl10){
    	gl=gl10;
    	loadAtlas();
    }
	
	/*public GameAtlas(GL10 gl, IOManager ioManager) {
		this.gl = gl;
		this.ioManager = ioManager;
		loadAtlas();
	}*/
	
	private static void loadAtlas(){
		
		int[] textureIds=new int[1];
		gl.glGenTextures(1, textureIds, 0);
		atlasTextureId = textureIds[0];
		
		InputStream asset = null;
		
		try {
			asset = IOManager.openAsset(ATLAS_FILE);
			Bitmap bitmap = BitmapFactory.decodeStream(asset);
			width = bitmap.getWidth();
	        height = bitmap.getHeight();
			gl.glBindTexture(GL10.GL_TEXTURE_2D, atlasTextureId);
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
	
	public static void reloadAtlas(){
		loadAtlas();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, atlasTextureId);
	}
	
	public static void releaseAtlas(){
		gl.glBindTexture(GL10.GL_TEXTURE_2D, atlasTextureId);
		int[] textureIds = {atlasTextureId};
		gl.glDeleteTextures(1, textureIds, 0);	
	}
	
	public static void setActive(){
		gl.glBindTexture(GL10.GL_TEXTURE_2D, atlasTextureId);
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


	public static GameImage getGameImage(int imageX,int imageY,int imageWidth,int imageHeight){
		GameImage spriteImage = new GameImage(imageX,imageY,imageWidth,imageHeight,width,height);
		return spriteImage;
	}
	
	public static GameImage[] getGameAnimation(int initialFrameX,int initialFrameY,int frameWidth,int frameHeight,int[] rowFrames){
		
		int numberOfFrames=0;
		
		for(int i=0;i<rowFrames.length;i++){
			numberOfFrames+=rowFrames[i];
		}
		
		GameImage[] frames = new GameImage[numberOfFrames];
		
		int offSetX=frameWidth+1;
		int offSetY=frameHeight+1;
		
		numberOfFrames=0;
		
		int currentColumn=0;
		int currentRow=0;
		int imageIndex=0;
		for(int i=0;i<rowFrames.length;i++){
			numberOfFrames=rowFrames[i];
			while(currentColumn < numberOfFrames){
	         frames[imageIndex]=
	         getGameImage(initialFrameX+currentColumn*offSetX,
	        		      initialFrameY+currentRow*offSetY,
	        		      frameWidth,
	        		      frameHeight);
	        	
			 currentColumn++;
			 imageIndex++;
		   }
		   currentRow++;	
		   currentColumn=0;
		}
	
	    		
				 currentColumn++;
		return frames;
	}
	

}
