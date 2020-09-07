package com.game.snake.screens;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.game.framework.Game;
//import com.game.framework.Options;
//import com.game.framework.exceptions.ResourceException;
import com.game.framework.gl.GameAtlas;
import com.game.framework.gl.GameBackgroundAtlas;
import com.game.framework.gl.GameScreen;
import com.game.framework.objects.GameObject;
//import com.game.phantom.PhGameFont;

public class PhGameScoresScreen extends GameScreen {

	
	private GameObject backButton;
	private GameObject background;
	//private PhGameFont font;
	private StringBuilder text;
	
	public PhGameScoresScreen(Game game,Context context,GL10 gl10) {
		super(game,context,gl10);
		backButton=new GameObject(350, 50,254,46,GameAtlas.getGameImage(193, 289, 254, 46),GameObject.CollisionMask.NONE);
		background = new GameObject(240,400,480,800,GameBackgroundAtlas.getBackgroundImage(1440, 0, 480, 800),GameObject.CollisionMask.NONE);
        setResetTouchMode(true);
      /*  try {
			Options.load();
		} catch (ResourceException e) {
		}*/
        //font=new PhGameFont();
        text=new StringBuilder();
	}

	@Override
	public void update(float deltaTime) {
		if(backButton.isTouched(touchX, touchY) ){
		 	PhGameMenuScreen menuScreen=new PhGameMenuScreen(game, context, gl10);
			game.setCurrentScreen(menuScreen);
		}
		
		
	}

	@Override
	public void present(float deltaTime) {
       //disegno dello screen corrente
		gl10.glClearColor(0, 0, 0, 1);
		gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl10.glEnable(GL10.GL_BLEND);
		gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		GameBackgroundAtlas.setActive();
		background.draw(gl10);
		GameAtlas.setActive();
		/*text.delete(0, text.length());
		int index=1;
		int y=650;
		for(int  point : Options.highscores){
			text.append(index+". "+point);
			y-=50;
			font.drawText(text, 200, y, gl10);
			text.delete(0, text.length());
			index++;
		}*/
		
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

}
