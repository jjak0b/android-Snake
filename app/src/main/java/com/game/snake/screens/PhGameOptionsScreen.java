package com.game.snake.screens;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.game.framework.Game;
//import com.game.framework.Options;
//import com.game.framework.exceptions.ResourceException;
import com.game.framework.gl.GameAtlas;
import com.game.framework.gl.GameBackgroundAtlas;
import com.game.framework.gl.GameImage;
import com.game.framework.gl.GameScreen;
import com.game.framework.objects.GameObject;

public class PhGameOptionsScreen extends GameScreen {

	private GameObject background;
	private GameObject musicButton;
	private GameObject sfxButton;
	private GameObject lowDifficultButton;
	private GameObject medDifficultButton;
	private GameObject hardDifficultButton;
	private GameObject backButton;
   
	private GameImage  musicImage;
	private GameImage  sfxImage;
	
	private GameImage  diffLowImage;
	private GameImage  diffMedImage;
	private GameImage  diffHighImage;
	
	
	public PhGameOptionsScreen(Game game,Context context,GL10 gl10) {
		super(game,context,gl10);
		loadSoundImages();
	    loadDifficultImages();
	    backButton=new GameObject(350, 50,254,46,GameAtlas.getGameImage(193, 289, 254, 46),GameObject.CollisionMask.NONE);
		background = new GameObject(240,400,480,800,GameBackgroundAtlas.getBackgroundImage(480, 0, 480, 800),GameObject.CollisionMask.NONE);
	    setResetTouchMode(true);
	}

	@Override
	public void update(float deltaTime) {
	
		if(musicButton.isTouched(touchX,touchY) && actionUp){
		//	Options.enableMusic=!Options.enableMusic;
			loadSoundImages();
		}else if(sfxButton.isTouched(touchX, touchY) && actionUp){
			//Options.enableSfx=!Options.enableSfx;	
			loadSoundImages();
		}else if(lowDifficultButton.isTouched(touchX, touchY) ){
			//Options.difficult=Options.Difficult.EASY;
			loadDifficultImages();
		}else if(medDifficultButton.isTouched(touchX, touchY)){
			//Options.difficult=Options.Difficult.NORMAL;
			loadDifficultImages();
		}else if(hardDifficultButton.isTouched(touchX, touchY)){
			//Options.difficult=Options.Difficult.HARD;
			loadDifficultImages();
		}else if(backButton.isTouched(touchX, touchY)){
			/*try {
				Options.save();
			    } catch (ResourceException e) {
			}*/
			PhGameMenuScreen menuScreen=new PhGameMenuScreen(game, context, gl10);
			game.setCurrentScreen(menuScreen);
		}
	
	}

	@Override
	public void present(float deltaTime) {
     
		gl10.glClearColor(0, 0, 0, 1);
		gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl10.glEnable(GL10.GL_BLEND);
		gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		GameBackgroundAtlas.setActive();
		background.draw(gl10);
		
		GameAtlas.setActive();
	
		musicButton.draw(gl10);
		sfxButton.draw(gl10);
		lowDifficultButton.draw(gl10);
		medDifficultButton.draw(gl10);
		hardDifficultButton.draw(gl10);
		backButton.draw(gl10);

		gl10.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		GameAtlas.reloadAtlas();
		GameBackgroundAtlas.reloadBackgrounds();
	}

	@Override
	public void dispose() {
	    GameAtlas.releaseAtlas();
	    GameBackgroundAtlas.releaseBackgrounds();
	}

	private void loadSoundImages(){
		//musicImage = Options.enableMusic?GameAtlas.getGameImage(417, 337, 94, 30):GameAtlas.getGameImage(321, 337, 94, 30);
		//sfxImage=Options.enableSfx?GameAtlas.getGameImage(417, 337, 94, 30):GameAtlas.getGameImage(321, 337, 94, 30);
		musicImage =GameAtlas.getGameImage(417, 337, 94, 30);
		sfxImage=GameAtlas.getGameImage(417, 337, 94, 30);
				
		if(musicButton==null)
		musicButton = new GameObject(376, 570,94,54,musicImage,GameObject.CollisionMask.NONE);
		else
		musicButton.setGameImage(musicImage);	
		
		if(sfxButton==null)
		sfxButton = new GameObject(363, 450,94,54,sfxImage,GameObject.CollisionMask.NONE);
		else
		sfxButton.setGameImage(sfxImage);
	}
	
	private void loadDifficultImages(){
	
		
	/*	if(Options.difficult==Options.Difficult.EASY){
			   diffLowImage=GameAtlas.getGameImage(97, 289, 94, 30);
			   diffMedImage=GameAtlas.getGameImage(193, 257, 94, 30);
			   diffHighImage=GameAtlas.getGameImage(1, 257, 94, 30);
		}else if(Options.difficult==Options.Difficult.NORMAL){
			   diffLowImage=GameAtlas.getGameImage(1, 289, 94, 30);
			   diffMedImage=GameAtlas.getGameImage(289, 257, 94, 30);
			   diffHighImage=GameAtlas.getGameImage(1, 257, 94, 30);
		}else if(Options.difficult==Options.Difficult.HARD){
			   diffLowImage=GameAtlas.getGameImage(1, 289, 94, 30);
			   diffMedImage=GameAtlas.getGameImage(193, 257, 94, 30);
			   diffHighImage=GameAtlas.getGameImage(97, 257, 94, 30);
		}*/
		
		   diffLowImage=GameAtlas.getGameImage(97, 289, 94, 30);
		   diffMedImage=GameAtlas.getGameImage(193, 257, 94, 30);
		   diffHighImage=GameAtlas.getGameImage(1, 257, 94, 30);
		   
        if(lowDifficultButton==null)
        	lowDifficultButton = new GameObject(100, 200,100,48,diffLowImage,GameObject.CollisionMask.NONE);
        else
        	lowDifficultButton.setGameImage(diffLowImage);
      
        if(medDifficultButton==null)
        	medDifficultButton = new GameObject(250, 200,100,48,diffMedImage,GameObject.CollisionMask.NONE);
        else
        	medDifficultButton.setGameImage(diffMedImage);
        if(hardDifficultButton==null)
        	hardDifficultButton = new GameObject(400, 200,100,48,diffHighImage,GameObject.CollisionMask.NONE);
        else
        	hardDifficultButton.setGameImage(diffHighImage);
	}
}
