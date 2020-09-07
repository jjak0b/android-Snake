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
//import com.game.phantom.GameLevels;
//import com.game.phantom.MainActivity;


public class PhGamePlayScreen extends GameScreen {

	//private GameLevels 	gameLevels;
	private boolean 	playerReady;
	private GameObject 	readyButton;
	private GameObject 	gameOverButton;

	
	public PhGamePlayScreen(Game game, Context context, GL10 gl10) {
		super(game, context, gl10);
		readyButton = new GameObject(240, 400, 254, 254,
				GameAtlas.getGameImage(256, 481, 254, 254),
				GameObject.CollisionMask.NONE);
		gameOverButton = new GameObject(240, 400, 254, 254,
				GameAtlas.getGameImage(1, 481, 254, 254),
				GameObject.CollisionMask.NONE);
		readyButton.setActive(true);
		gameOverButton.setActive(false);
		playerReady = false;
		//gameLevels = new GameLevels(gl10);
		
	}

	@Override
	public void update(float deltaTime) {
		if (readyButton.isActive()
				&& readyButton.isTouched(touchX, touchY)) {
		PhGameMenuScreen menu = new PhGameMenuScreen(game, context,
				gl10);
		game.setCurrentScreen(menu);
	}
	/*	if (gameOverButton.isActive()
				&& gameOverButton.isTouched(touchX, touchY)) {
			Options.addScore(gameLevels.getScore());
			try {
				Options.save();
			} catch (ResourceException e) {
				e.printStackTrace();
			}
			PhGameScoresScreen scores = new PhGameScoresScreen(game, context,
					gl10);
			game.setCurrentScreen(scores);

		} else if (readyButton.isActive()
				&& readyButton.isTouched(touchX, touchY)) {
			playerReady = true;
			readyButton.setActive(false);
		}

		gameLevels.updateBackgrounds(deltaTime);
		gameLevels.updateScore(deltaTime);
		if (game.getState() == Game.State.Running && playerReady) {
			gameLevels.updateJoystickAndPlayer(touchX, touchY, actionUp,
					deltaTime);
			
			gameLevels.updateEnemyAndExplosions(deltaTime);
			
			gameLevels.checkCollisionShipFireAndEnemy();
			gameLevels.checkCollisionShipAndEnemy();
			
		}
			
		gameLevels.updateShipHitted();
		

		if (gameLevels.getLife() == 0) {
			if (!gameOverButton.isActive()) {
				gameOverButton.setActive(true);
				playerReady = false;
			}
			gameLevels.updatePlayer(touchX, touchY, actionUp, deltaTime);
			gameLevels.updateEnemyAndExplosions(deltaTime);
		}
		resetTouch();*/
	}

	@Override
	public void present(float deltaTime) {
		gl10.glClearColor(0, 0, 0, 1);
		gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl10.glEnable(GL10.GL_BLEND);
		gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

	/*	gameLevels.drawBackgrounds(gl10);

		gameLevels.drawEnemyAndExplosion(deltaTime, gl10);
		gameLevels.drawJoystickAndPlayer(gl10);
		gameLevels.drawScore(gl10);
*/

		
		GameAtlas.setActive();//questa si leva.....
		if (readyButton.isActive()) {
			readyButton.draw(gl10);
		} else if (gameOverButton.isActive()) {
			gameOverButton.draw(gl10);
		}

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
