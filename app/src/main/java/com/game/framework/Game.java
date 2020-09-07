package com.game.framework;

import com.game.framework.gl.GameScreen;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public abstract class Game {

	public enum State{
		Initialized,
		Running,
		Paused,
		Finished,
		Idle
	}
	
	protected State  state=State.Initialized; 
	protected GameScreen currentScreen;
	protected Object monitor = new Object();
	protected long startTime = System.nanoTime();
	protected WakeLock wakeLock;
	private boolean transition=false;
	
	
	protected Game(Context context,String activityName){
		PowerManager powerManager=(PowerManager)
				context.getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, activityName);
	}
	

	public  void  onResume(){
		wakeLock.acquire();
	}
	
	public  void onPause(boolean isFinishing){
		synchronized(monitor){
			if(isFinishing){
				state = State.Finished;
			}else{
				state = State.Paused;
			}
			
			while(true){
				try{
					monitor.wait();
					break;
				}catch(InterruptedException e){}
			}
		}
		wakeLock.release();
	}

	public GameScreen getCurrentScreen() {
		return currentScreen;
	}

	public void setCurrentScreen(GameScreen currentScreen) {

		if(currentScreen==null){
			throw new IllegalArgumentException("Screen is null!");
		}
		
		if(this.currentScreen!=null){
		 this.currentScreen.pause();
		 this.currentScreen.dispose();
		}
		currentScreen.resume();
		currentScreen.update(0);
		transition=true;
		this.currentScreen = currentScreen;
	}

	public Object getMonitor() {
		return monitor;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public boolean isTransition() {
		boolean value = transition;
		transition=false;
		return value;
	}

}
