package com.game.framework.objects;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.game.framework.gl.GameAnimation;
import com.game.framework.gl.GameImage;
import com.game.framework.gl.GameAnimation.Mode;

public class GameObject {

	protected float x;
	protected float y;
	protected int width;
	protected int height;

	protected GameImage 		gameImage;
	protected GameAnimation 	gameImageAnimation;
	private   GameObjectMesh 	mesh;

	public enum CollisionMask {
		RECTANGLE, CIRCLE, NONE
	}

	protected boolean active = true;
	private CollisionMask collisionMask;

	public GameObject(float x, float y, int width, int height,
			GameImage gameImage, CollisionMask collisionMask) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gameImage = gameImage;
		this.mesh = new GameObjectMesh(x, y, width, height);
		this.mesh.mapTextureUV(gameImage);
		this.collisionMask = collisionMask;
	}

	public GameObject(GameObject gameObject) {
		this.x = gameObject.getX();
		this.y = gameObject.getY();
		this.width = gameObject.getWidth();
		this.height = gameObject.getHeight();
		this.gameImage = gameObject.getGameImage();
		this.mesh = new GameObjectMesh(this.x, this.y, this.width, this.height);
		this.mesh.mapTextureUV(this.gameImage);
		this.collisionMask = gameObject.getCollisionMask();
	}

	public GameObject(float x, float y, int width, int height,
			GameAnimation gameImageAnimation, CollisionMask collisionMask) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.mesh = new GameObjectMesh(x, y, width, height);
		this.gameImageAnimation = gameImageAnimation;
		this.collisionMask = collisionMask;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		this.mesh.setX(x);
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		this.mesh.setY(y);
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

	public GameImage getGameImage() {
		return gameImage;
	}

	public void setGameImage(GameImage gameImage) {
		this.gameImage = gameImage;
		this.mesh.mapTextureUV(gameImage);
	}

	public GameAnimation getGameImageAnimation() {
		return gameImageAnimation;
	}

	public void setGameImageAnimation(GameAnimation gameImageAnimation) {
		this.gameImageAnimation = gameImageAnimation;
	}

	public GameObjectMesh getMesh() {
		return mesh;
	}

	public boolean isTouched(float x, float y) {
		if (x >= (this.x - width / 2) && x <= (this.x + width / 2)
				&& y >= (this.y - height / 2) && y <= (this.y + height / 2)) {
			return true;
		} else {
			return false;
		}
	}

	public void draw(GL10 gl) {
		ShortBuffer triangles = mesh.getTrianglesBuffer();
		FloatBuffer buffer = mesh.getVerticesBuffer();
		buffer.position(0);
		gl.glVertexPointer(2, GL10.GL_FLOAT, (2 + 2) * 4, buffer);
		buffer.position(2);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, (2 + 2) * 4, buffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT,
				triangles);
	}

	public void drawAnimation(GL10 gl, Mode mode, float time) {
		this.mesh.mapTextureUV(gameImageAnimation.getFrame(time, mode));
		draw(gl);
	}

	public CollisionMask getCollisionMask() {
		return collisionMask;
	}

	public void setCollisionMask(CollisionMask collisionMask) {
		this.collisionMask = collisionMask;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
