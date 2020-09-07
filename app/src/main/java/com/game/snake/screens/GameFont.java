package com.game.snake.screens;

import com.game.framework.gl.GameImage;
import com.game.framework.objects.GameObject;

/**
 * Created by Jacopo on 16/11/2016.
 */

public class GameFont extends GameObject{

    private char value;


    public GameFont(char value, float x, float y, int width, int height, GameImage image) {
        super(x, y, width, height, image, CollisionMask.NONE);
        this.value = value;
    }
    public GameFont( GameFont gamefont )
    {
        super(gamefont);
        this.setValue( gamefont.getValue() );
    }

    public char getValue() {
        return value;
    }
    private void setValue(char value) {
        this.value = value;
    }



}
