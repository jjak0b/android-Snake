package com.game.framework.objects;

import com.game.framework.gl.GameAnimation;
import com.game.framework.gl.GameImage;

public abstract class DynamicGameObject extends GameObject {
	
	public DynamicGameObject(float x, float y, int width, int height,
			GameAnimation gameImageAnimation,CollisionMask collisionMask) {
		super(x, y, width, height, gameImageAnimation,collisionMask);
	}

	public DynamicGameObject(float x, float y, int width, int height,
			GameImage gameImage,CollisionMask collisionMask) {
		super(x, y, width, height, gameImage,collisionMask);
	}
	
	public abstract void update(float deltaTime);

}
