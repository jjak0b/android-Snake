package com.game.snake;

/**
 * Created by Jacopo on 11/11/2016.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.game.framework.IOManager;
import com.game.framework.exceptions.ResourceException;
import com.game.framework.gl.GameImage;

import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

// per modificare questo bisogna effettivamente modificare game view che lavora con più materiali... ma ora solo con GameAtlas e GameBackground
// riga 82 - 83 mettere ogni init di ogni materiale caricato...
// guardare http://stackoverflow.com/questions/5979069/creating-a-gl-texture-outside-of-glsurfaceview-renderer

public class GameMaterial {

    private static GL10 gl;

    private int atlasTextureId;
    private int minFilter;
    private int magFilter;
    private int width=1024;
    private int height=512;

    private String ATLAS_FILE= "graphics/atlas.png";


    public void init(GL10 gl10){
        gl=gl10;
        load();
    }

	public GameMaterial(GL10 gl, String ATLAS_FILE) {

		this.gl = gl;
        this.ATLAS_FILE = ATLAS_FILE;
		load();
	}

    private void load(){

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

    public void reload(){
        load();
        gl.glBindTexture(GL10.GL_TEXTURE_2D, atlasTextureId);
    }

    public void release(){
        gl.glBindTexture(GL10.GL_TEXTURE_2D, atlasTextureId);
        int[] textureIds = {atlasTextureId};
        gl.glDeleteTextures(1, textureIds, 0);
    }

    public void setActive(){
        gl.glBindTexture(GL10.GL_TEXTURE_2D, atlasTextureId);
    }

    public int getMinFilter() {
        return minFilter;
    }


    public int getMagFilter() {
        return magFilter;
    }


    public void setMinFilter(int minF) {
        minFilter = minF;
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, minFilter);
    }


    public void setMagFilter(int magF) {
        magFilter = magF;
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, magFilter);
    }


    public GameImage getGameImage(int imageX, int imageY, int imageWidth, int imageHeight){
        GameImage spriteImage = new GameImage(imageX,imageY,imageWidth,imageHeight,width,height);
        return spriteImage;
    }

    public GameImage[] getGameAnimation(int initialFrameX,int initialFrameY,int frameWidth,int frameHeight,int[] rowFrames){

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
