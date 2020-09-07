package com.game.framework.gl;

public class GameAnimation {
    
    public enum Mode{
    	ANIMATION_LOOP,ANIMATION_NO_LOOP
    }
    
    private GameImage[] frames;
    private float frameDuration;
    private boolean animationEnd;
    public int frameNumber;
    private float stateTime=0;
    
    
    public GameAnimation(float frameDuration, GameImage[] frames) {
        this.frameDuration = frameDuration;
        this.frames = frames;
    }
    
    public GameImage getFrame(float deltaTime, Mode mode) {
     
    	stateTime+=deltaTime;
    	
    	frameNumber = (int)(stateTime / frameDuration);
    	
        animationEnd=false;
        
        if(mode == Mode.ANIMATION_NO_LOOP) {
            frameNumber = Math.min(frames.length-1, frameNumber);            
        } else {
            frameNumber = frameNumber % frames.length;
        }    

        if(frameNumber==frames.length-1){
        	animationEnd=true;
        	stateTime=0;
        }
        return frames[frameNumber];
    }

	public float getFrameDuration() {
		return frameDuration;
	}

	public void setFrameDuration(float frameDuration) {
		this.frameDuration = frameDuration;
	}

	public boolean isAnimationEnd() {
		return animationEnd;
	}

	public int getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}
    
    
}
