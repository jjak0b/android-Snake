package com.game.framework.gl;

import javax.microedition.khronos.opengles.GL10;

import com.game.framework.Game;

import android.content.Context;


public abstract class GameScreen {
    
	protected  Game game;
    protected  Context context;
	protected  GL10 gl10;
	
	protected float touchX;
	protected float touchY;
	protected float touch2X;
	protected float touch2Y;
	
	protected float upX;
	protected float upY;
	protected boolean actionUp;
	
	protected  int width;
	protected  int height;
    
	private boolean resetTouchMode;
	
    public GameScreen(Game game,Context context,GL10 gl10) {
        this.game = game;
        this.context=context;
        this.gl10=gl10;
    }

    public abstract void update(float deltaTime);

    public abstract void present(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();


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

	public float getTouch2X() {
		return touch2X;
	}

	public void setTouch2X(float touch2x) {
		touch2X = touch2x;
	}

	public float getTouch2Y() {
		return touch2Y;
	}

	public void setTouch2Y(float touch2y) {
		touch2Y = touch2y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isActionUp() {
		return actionUp;
	}

	public void setActionUp(boolean actionUp) {
		this.actionUp = actionUp;
	}

	public float getUpX() {
		return upX;
	}

	public void setUpX(float upX) {
		this.upX = upX;
	}

	public float getUpY() {
		return upY;
	}

	public void setUpY(float upY) {
		this.upY = upY;
	}

	public boolean isResetTouchMode() {
		return resetTouchMode;
	}

	public void setResetTouchMode(boolean resetTouchMode) {
		this.resetTouchMode = resetTouchMode;
	}
	
    
}