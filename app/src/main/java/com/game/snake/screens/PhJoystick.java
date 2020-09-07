package com.game.snake.screens;

import android.graphics.Path;

import javax.microedition.khronos.opengles.GL10;
import com.game.framework.gl.GameAtlas;
import com.game.framework.objects.GameObject;

/**
 * Created by Jacopo on 07/11/2016.
 */

public class PhJoystick implements GameController{

    private GameObject buttonUp;
    private GameObject buttonDown;
    private GameObject buttonRight;
    private GameObject buttonLeft;
    private GameObject buttonFire;

    private Direction state;
    private boolean   fireState;
    private GL10 gl10;

    public PhJoystick(GL10 gl10) {
        buttonUp = new GameObject(150, 150,90,90,GameAtlas.getGameImage(237, 338, 46, 46),GameObject.CollisionMask.NONE);
        buttonDown = new GameObject(150,45,90,90,GameAtlas.getGameImage(235, 432, 46, 46),GameObject.CollisionMask.NONE);
        buttonLeft = new GameObject(50, 95,90,90,GameAtlas.getGameImage(186, 384, 46, 46),GameObject.CollisionMask.NONE);
        buttonRight = new GameObject(250, 95,90,90,GameAtlas.getGameImage(283, 384, 46, 46),GameObject.CollisionMask.NONE);
        buttonFire = new GameObject(480-48, 94,90,90,GameAtlas.getGameImage(1, 385, 95, 95),GameObject.CollisionMask.NONE);
        this.gl10=gl10;
        this.state= Path.Direction.STAND;
        this.fireState=false;
    }

    @Override
    public Direction move(float touchX, float touchY, boolean actionUp) {
        if(buttonUp.isTouched(touchX, touchY)) {
            state=Direction.UP;
        } else if(buttonDown.isTouched(touchX, touchY)) {
            state=Direction.DOWN;
        } else if(buttonRight.isTouched(touchX, touchY)) {
            state=Direction.RIGHT;
        } else if(buttonLeft.isTouched(touchX, touchY)) {
            state=Direction.LEFT;
        }

        return state;
    }

    @Override
    public boolean fire(float touchX, float touchY) {
        fireState=false;
        if(buttonFire.isTouched(touchX, touchY)){
            fireState=true;
        }

        return fireState;
    }

    public void draw() {
        buttonFire.draw(gl10);
        buttonDown.draw(gl10);
        buttonUp.draw(gl10);
        buttonLeft.draw(gl10);
        buttonRight.draw(gl10);
    }
}