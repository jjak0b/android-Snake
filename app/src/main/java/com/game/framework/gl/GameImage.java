package com.game.framework.gl;

public class GameImage {

	private float u1, v1;
	private float u2, v2;

	private float width;
	private float height;
	
	private int textureWidth;
	private int textureHeight;
	
	private float x;
	private float y;
	
	public GameImage(float x, float y, float width, float height,
			int textureWidth, int textureHeight) {
		this.width=width;
		this.height=height;
		this.textureWidth=textureWidth;
		this.textureHeight=textureHeight;
		this.x=x;
		this.y=y;
		computeUvCoord();
		
	}

	public float getU1() {
		return u1;
	}

	public float getV1() {
		return v1;
	}

	public float getU2() {
		return u2;
	}

	public float getV2() {
		return v2;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void computeUvCoord(){
		u1 = x / textureWidth;
		v1 = y / textureHeight;
		u2 = u1 + width / textureWidth;
		v2 = v1 + height / textureHeight;
	}
}
