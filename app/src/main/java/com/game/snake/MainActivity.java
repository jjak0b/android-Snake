package com.game.snake;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.game.framework.Game;
import com.game.framework.gl.GameView;



public class MainActivity extends Activity {

	public static GameView gameView;
	public static Game game;
    public static Context context;
	public static Activity activity;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		context=this;
		activity=this;
		game = new SnakeGame(this, "MainActivity");
		gameView = new SnakeGameView(game.getMonitor());
		gameView.setGame(game);
		setContentView(gameView);
	   
	}
	

	@Override
	public void onResume(){
		super.onResume();
		game.onResume();
		gameView.onResume();
	}
	
	@Override
	public void onPause(){
		super.onPause();
		game.onPause(isFinishing());
		gameView.onPause();
	}


}

