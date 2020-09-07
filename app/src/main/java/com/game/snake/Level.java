package com.game.snake;

import com.game.framework.Game;
import com.game.framework.gl.GameView;

/**
 * Created by Jacopo on 07/11/2016.
 */

public class Level extends GameView{

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 480;
    public Ent snake[];

    public static void init(Ent[] [])
    {
        // inizializzo lo snake
        this.snake = new Ent[1024];
        // ora la testa dello Snake
        // e la imposto al centro dello schermo
        this.snake[0] = new Ent( new int[]{ SCREEN_WIDTH / 2,SCREEN_HEIGHT / 2 }, 0, Ent.SNAKE_HUD );
        this.snake[0].setActive(true);
        // e ora la sua cosa
        for (int i = 1; i < this.snake.length; i++)
        {
            this.snake[i] = new Ent( new int[]{ 0, 0 }, 90, Ent.SNAKE_HUD );
            this.snake[i].setActive( false );
        }

    }
}