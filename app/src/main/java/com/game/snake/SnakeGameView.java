package com.game.snake;

import com.game.framework.gl.GameScreen;
import com.game.framework.gl.GameView;
import com.game.snake.screens.PhGameMenuScreen;

public class SnakeGameView extends GameView{


	public SnakeGameView(Object monitor) {
		super(monitor);
	}

	@Override
	protected GameScreen getStartScreen() {
		return new PhGameMenuScreen(game,MainActivity.context,gl10);
	}
  	
}
