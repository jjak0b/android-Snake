package com.game.snake;

import com.game.framework.gl.GameAtlas;
import com.game.framework.gl.GameImage;
import com.game.framework.objects.GameObject;

/**
 * Created by Jacopo on 07/11/2016.
 */

public class Ent extends GameObject{

    public static final GameImage SNAKE_HUD = new GameImage(598,279,616,298,16,16);

    private int[] origin = new int[2];
    private int angle;

    /**
     *
     * @param origin p(x, y)
     * @param angle 0<= x < 360
     * @param HUD Image to applly at this entity
     */
    public Ent(int[] origin, int angle, GameImage HUD) {
        this.origin = origin;
        this.angle = angle;
        super(this.getOrigin()[0], this.getOrigin()[1], 16, 16, HUD, CollisionMask.CIRCLE);
    }

    /**
     * @return
     * origin[0] coordinata x
     * origin[1] coordinata y
     */
    public int[] getOrigin() {
        return origin;
    }

    /**
     *
     * @param origin
     * origin[0] coordinata x
     * origin[1] coordinata y
     */
    public void setOrigin(int[] origin) {
        this.origin = origin;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle % 360;
    }

    public GameObject getHUD() {
        return HUD;
    }

    public void setHUD(GameObject HUD) {
        this.HUD = HUD;
    }
}
