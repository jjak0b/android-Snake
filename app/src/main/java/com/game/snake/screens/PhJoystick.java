package com.game.snake.screens;

import javax.microedition.khronos.opengles.GL10;
import com.game.framework.gl.GameAtlas;
import com.game.framework.objects.GameObject;
import com.game.snake.GameController;

/**
 * Created by Jacopo on 07/11/2016.
 */

public class PhJoystick implements GameController {

    private GameObject buttonUp;
    private GameObject buttonDown;
    private GameObject buttonRight;
    private GameObject buttonLeft;

    private Direction state;
    private GL10 gl10;

    public Direction getState() {
        return state;
    }

    public void setState(Direction state) {
        this.state = state;
    }


    public PhJoystick(GL10 gl10) {
        buttonUp = new GameObject(150, 150,90,90,GameAtlas.getGameImage(237, 338, 46, 46),GameObject.CollisionMask.NONE);
        buttonDown = new GameObject(150,45,90,90,GameAtlas.getGameImage(235, 432, 46, 46),GameObject.CollisionMask.NONE);
        buttonLeft = new GameObject(50, 95,90,90,GameAtlas.getGameImage(186, 384, 46, 46),GameObject.CollisionMask.NONE);
        buttonRight = new GameObject(250, 95,90,90,GameAtlas.getGameImage(283, 384, 46, 46),GameObject.CollisionMask.NONE);
        // imposto alpha
        // da fare...

        this.gl10=gl10;
        this.state = Direction.RIGHT;
    }

    public Direction move(float touchX, float touchY) {
        if(buttonUp.isTouched(touchX, touchY)) {
            state=Direction.UP;
        }
        else if(buttonDown.isTouched(touchX, touchY)) {
            state=Direction.DOWN;
        }
        else if(buttonRight.isTouched(touchX, touchY)) {
            state=Direction.RIGHT;
        }
        else if(buttonLeft.isTouched(touchX, touchY)) {
            state=Direction.LEFT;
        }

        return state;
    }


    public void draw() {
        buttonDown.draw(gl10);
        buttonUp.draw(gl10);
        buttonLeft.draw(gl10);
        buttonRight.draw(gl10);
    }

}