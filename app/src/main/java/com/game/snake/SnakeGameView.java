package com.game.snake;

import com.game.framework.Game;
import com.game.framework.gl.GameAtlas;
import com.game.framework.gl.GameBackgroundAtlas;
import com.game.framework.gl.GameScreen;
import com.game.framework.gl.GameView;
import com.game.snake.screens.PhGameMenuScreen;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SnakeGameView extends GameView {


	public SnakeGameView(Object monitor) {
		super(monitor);
	}

	@Override
	protected GameScreen getStartScreen() {
		return new PhGameMenuScreen(game,MainActivity.context,gl10);
	}
  	
}
