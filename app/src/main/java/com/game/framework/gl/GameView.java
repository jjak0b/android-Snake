package com.game.framework.gl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.game.framework.Game;
import com.game.framework.Game.State;
import com.game.snake.MainActivity;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;


public abstract class GameView extends GLSurfaceView {

	protected Game game;
	protected Object monitor;
	protected GL10 gl10;

	private float touchX;
	private float touchY;
	private boolean actionUp;


	private  class TouchToWorld{
		
		public  void worldXTransform(MotionEvent event){
			touchX = (event.getX(event.getActionIndex()) / (float) getWidth()) * 480;
		}
		
		public  void worldYTransform(MotionEvent event){
			touchY = (1 - event.getY(event.getActionIndex()) / (float) getHeight()) * 800;
		}
		
	}
	
	private TouchToWorld touchToWorld;
	
	private OnTouchListener touchInput = new OnTouchListener() {

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			
			switch (action) {
			case MotionEvent.ACTION_DOWN: 
				touchToWorld.worldXTransform(event);
				touchToWorld.worldYTransform(event);
				return true;
			case MotionEvent.ACTION_POINTER_DOWN:
				touchToWorld.worldXTransform(event);
				touchToWorld.worldYTransform(event);
				return true;
			case MotionEvent.ACTION_UP:
				touchToWorld.worldXTransform(event);
				touchToWorld.worldYTransform(event);
                actionUp=true;
				return true;	
			case MotionEvent.ACTION_POINTER_UP:
				touchToWorld.worldXTransform(event);
				touchToWorld.worldYTransform(event);
			    actionUp=true;
			    return true;
			}

			return false;
		}
	};

	private Renderer renderer = new Renderer() {

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			gl.glViewport(0, 0, getWidth(), getHeight());
			gl.glClearColor(0, 0, 0, 1);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 480, 0, 800, 1, -1);
			gl10 = gl;
			GameAtlas.initializeAtlas(gl10);
			GameBackgroundAtlas.initializeAtlas(gl10);
			synchronized (monitor) {
				if (game.getState() == State.Initialized) {
					game.setCurrentScreen(getStartScreen());
				}
				game.setState(State.Running);
				game.getCurrentScreen().resume();
				game.setStartTime(System.nanoTime());
			}
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {

		}

		@Override
		public void onDrawFrame(GL10 gl) {

			State state = null;

			synchronized (monitor) {
				state = game.getState();
			}

			GameScreen gameScreen = game.getCurrentScreen();

			if(game.isTransition()){
				touchX=0;
				touchY=0;
			}
			
			gameScreen.setTouchX(touchX);
			gameScreen.setTouchY(touchY);
			
			if(gameScreen.isResetTouchMode()){
			 touchX=0;
			 touchY=0;
			}
			
			gameScreen.setActionUp(actionUp);
            actionUp=false;
			
			gameScreen.setWidth(getWidth());
			gameScreen.setHeight(getHeight());

			if (state == State.Running) {
				float deltaTime = (System.nanoTime() - game.getStartTime()) / 1000000000f;
				game.setStartTime(System.nanoTime());
				gameScreen.update(deltaTime);
				gameScreen.present(deltaTime);
			}

			if (state == State.Paused) {
				gameScreen.pause();
				synchronized (monitor) {
					game.setState(State.Idle);
					monitor.notifyAll();
				}
			}

			if (state == State.Finished) {
				gameScreen.pause();
				gameScreen.dispose();
				synchronized (monitor) {
					game.setState(State.Idle);
					monitor.notifyAll();
				}
			}

		}

	};

	public GameView(Object monitor) {
		super(MainActivity.context);
		setRenderer(renderer);
		setOnTouchListener(touchInput);
		touchToWorld = new TouchToWorld();
		this.monitor=monitor;

	}

	public void setGame(Game game) {
		this.game = game;
		this.monitor = game.getMonitor();
	}

	public GL10 getGL10() {
		return gl10;
	}

	public float getTouchX() {
		return touchX;
	}

	public void setTouchX(float touchX) {
		this.touchX = touchX;
	}

	public float getTouchY() {
		return touchY;
	}

	public void setTouchY(float touchY) {
		this.touchY = touchY;
	}

	protected abstract GameScreen getStartScreen();
}
