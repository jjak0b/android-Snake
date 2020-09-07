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
import com.game.snake.Level;
import com.game.snake.screens.PhGamePlayScreen;
//import com.game.framework.sfx.Music;
//import com.game.framework.sfx.MusicSfxLoader;


public class PhGameMenuScreen extends GameScreen {

	private GameObject background;
	// private GameObject play;
	// private GameObject options;
	// private GameObject scores;
	// private GameObject exit;
	private Text2D play;
	private Text2D options;
	private Text2D exit;
	private Text2D scores;
	//private Music music;


	public PhGameMenuScreen(Game game, Context context, GL10 gl10) {
		super(game, context, gl10);
		background 	= new GameObject(240, 400, Level.SCREEN_WIDTH, Level.SCREEN_HEIGHT,GameBackgroundAtlas.getBackgroundImage(0, 0, 480, 800),GameObject.CollisionMask.NONE);
		/*
		play 		= new GameObject(240, 500, 255, 95, GameAtlas.getGameImage(1, 65, 255, 95), GameObject.CollisionMask.NONE);
		scores 		= new GameObject(240, 400, 255, 85, GameAtlas.getGameImage(1, 161, 255, 95), GameObject.CollisionMask.NONE);
		options 	= new GameObject(240, 290, 255, 115, GameAtlas.getGameImage(257, 65, 255, 95), GameObject.CollisionMask.NONE);
		exit 		= new GameObject(240, 170, 255, 85, GameAtlas.getGameImage(257,161, 255, 95), GameObject.CollisionMask.NONE);
		*/

		play 	= new Text2D("play"		,1, 200, 600);
		options = new Text2D("options"	,1, 200, 500);
		scores 	= new Text2D("test"		,1, 200, 400);
		exit 	= new Text2D("exit"		,1, 200, 300);



	/*	try {
			Options.load();
		} catch (ResourceException e) {
		}
		if (Options.enableMusic) {
			music = MusicSfxLoader.newMusic("music/afterburner.ogg");
			music.setLooping(true);
			music.play();
		}*/
	}

	@Override
	public void update(float deltaTime) {

		if (play.isTouched(touchX, touchY)) {
			PhGamePlayScreen playScreen = new PhGamePlayScreen(game, context,
					gl10);
			game.setCurrentScreen(playScreen);
		} else if (options.isTouched(touchX, touchY)) {
			PhGameOptionsScreen optionsScreen = new PhGameOptionsScreen(game,context, gl10);
			// options = new Text2D( Character.toString((char)( (int)(options.getText().charAt(0))+1 ) )	, 2f, 200, 500);
			game.setCurrentScreen(optionsScreen);
		} else if (scores.isTouched(touchX, touchY)) {
			PhGameScoresScreen scoresScreen = new PhGameScoresScreen(game,
					context, gl10);
			game.setCurrentScreen(scoresScreen);
		} else if (exit.isTouched(touchX, touchY)) {
			game.setState(Game.State.Finished);
			System.exit(0);
		}
	}

	@Override
	public void present(float deltaTime) {
		// disegno dello screen corrente
		gl10.glClearColor(0, 0, 0, 1);
		gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl10.glEnable(GL10.GL_BLEND);
		gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		GameBackgroundAtlas.setActive();
		background.draw(gl10);

		play.setActive( true );
		options.setActive( true );
		exit.setActive( true );
		scores.setActive( true );
		GameAtlas.setActive();

		play.drawText( gl10 );
		options.drawText( gl10 );
		scores.drawText( gl10);
		exit.drawText( gl10 );
		// play.draw(gl10);
		// scores.draw(gl10);
		// options.draw(gl10);
		// exit.draw(gl10);

		gl10.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
		/*if (Options.enableMusic) {
			music.pause();
		}*/
	}

	@Override
	public void resume() {
		GameAtlas.reloadAtlas();
		GameBackgroundAtlas.reloadBackgrounds();
		/*if (Options.enableMusic) {
			music.play();
		}*/
	}

	@Override
	public void dispose() {
		/*if (Options.enableMusic) {
			music.dispose();
		}*/
		GameAtlas.releaseAtlas();
		GameBackgroundAtlas.releaseBackgrounds();
	}

}
