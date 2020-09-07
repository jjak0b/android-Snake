package com.game.snake.screens;

import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.util.Log;

import com.game.framework.Game;
//import com.game.framework.Options;
//import com.game.framework.exceptions.ResourceException;
import com.game.framework.gl.GameAtlas;
import com.game.framework.gl.GameBackgroundAtlas;
import com.game.framework.gl.GameScreen;
import com.game.framework.objects.GameObject;
import com.game.snake.Level;
import com.game.snake.Utility;
import com.game.snake.screens.PhGameMenuScreen;

public class PhGamePlayScreen extends GameScreen {

	private Level 		level;
	private boolean 	playerReady;
	private GameObject 	readyButton;
	private GameObject 	gameOverButton;
	private GameObject 	background;

	
	public PhGamePlayScreen(Game game, Context context, GL10 gl10) {
		super(game, context, gl10);
		readyButton = new GameObject(240, 400, 254, 254,GameAtlas.getGameImage(256, 481, 254, 254),GameObject.CollisionMask.NONE);
		gameOverButton = new GameObject(240, 400, 254, 254,GameAtlas.getGameImage(1, 481, 254, 254),GameObject.CollisionMask.NONE);
		background = new GameObject(240,400,Level.SCREEN_WIDTH,Level.SCREEN_HEIGHT,GameBackgroundAtlas.getBackgroundImage(960, 0, 480, 800),GameObject.CollisionMask.NONE);

		readyButton.setActive(true);
		gameOverButton.setActive(false);
		playerReady = false;
		level = new Level(game, context, gl10);
		//gameLevels = new GameLevels(gl10);
		
	}

	@Override
	public void update(float deltaTime) {
		// Log.d("Test","update:" + deltaTime);

		// level.updateBackgrounds(deltaTime);
		level.setTime( level.getTime() + deltaTime);

		if (readyButton.isActive() && readyButton.isTouched(touchX, touchY))
		{
			readyButton.setActive( false );
			return;
		}

		if( !gameOverButton.isActive() && level.gameover )
		{
			gameOverButton.setActive( true );
		}
		if( gameOverButton.isActive() && level.gameover && gameOverButton.isTouched(touchX, touchY) )
		{
			PhGameMenuScreen menu = new PhGameMenuScreen(game, context, gl10);
			game.setCurrentScreen(menu);
		}

		// level.updateScore(deltaTime);
		if( !level.gameover && !readyButton.isActive() ) {
			level.player.move(touchX, touchY);
			if (level.getTime() >= 0.1) {
				level.updatePlayer(deltaTime);
				level.snakeEater();
				Log.d("test", "x: " + level.snake.get(0).getOrigin()[0] + "; y: " + level.snake.get(0).getOrigin()[1]);
				level.setTime(0f);
			}
			level.updateScore( deltaTime );
			level.updateFood( deltaTime );
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
		GameAtlas.setActive();//questa si leva.....
		level.drawSnake(gl10);
		level.drawFood(gl10);
		level.drawScore(gl10);
		if( !gameOverButton.isActive() && !readyButton.isActive()) {
			level.drawJoystick(gl10);
		}

		if (readyButton.isActive()) {
			readyButton.draw(gl10);
		}
		else if (gameOverButton.isActive()) {
			gameOverButton.draw(gl10);
		}

		gl10.glDisable(GL10.GL_BLEND);

	}

	@Override
	public void pause() {
		/*if (Options.enableMusic) {
			gameLevels.getMusic().pause();
		}
		if (gameLevels.getLife() > 0) {
			playerReady = false;
			readyButton.setActive(true);
		} else {
			gameOverButton.setActive(false);
		}*/
	}

	@Override
	public void resume() {
		GameAtlas.reloadAtlas();
		GameBackgroundAtlas.reloadBackgrounds();
	/*	if (Options.enableMusic) {
			gameLevels.getMusic().play();
		}*/
	}

	@Override
	public void dispose() {
	/*	if (Options.enableMusic) {
			gameLevels.getMusic().dispose();
		}
		if (Options.enableSfx) {
			gameLevels.getExplosionSfx().dispose();
			gameLevels.getShipFireSfx().dispose();
		}*/
		GameAtlas.releaseAtlas();
		GameBackgroundAtlas.releaseBackgrounds();
	}


}
