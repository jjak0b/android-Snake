package com.game.snake;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Debug;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import com.game.framework.Game;
import com.game.framework.gl.GameAtlas;
import com.game.framework.gl.GameBackgroundAtlas;
import com.game.framework.gl.GameImage;
import com.game.framework.gl.GameScreen;
import com.game.framework.gl.GameView;
import com.game.framework.objects.GameObject;
import com.game.snake.screens.PhJoystick;
import com.game.snake.screens.Text2D;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import static com.game.snake.MainActivity.activity;
import static com.game.snake.MainActivity.game;
import static com.game.snake.MainActivity.gameView;

/**
 * Created by Jacopo on 07/11/2016.
 */

public class Level extends GameObject{

    public static int SCREEN_WIDTH    = 480;
    public static int SCREEN_HEIGHT   = 800;

    // public static int SCREEN_WIDTH = activity.getWindowManager().getDefaultDisplay().getWidth();
    // public static int SCREEN_HEIGHT = activity.getWindowManager().getDefaultDisplay().getHeight();
    public static boolean   gameover        = false;
    private Text2D scoreHUD;
    private GameObject background;
    private float time = 0f;
    public List<Ent> snake;
    // private Ent snake[];
    private Ent food;
    private int score;
    public PhJoystick player;

    public float getTime() {
        return time;
    }
    public void setTime(float time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public Level(Game game, Context context, GL10 gl10)
    {
        super(0,0,0,0, GameAtlas.getGameImage( 0, 0,0, 0 ),GameObject.CollisionMask.NONE);
        this.setScore( 0 );
        gameover = false;
        this.food = this.spawnFood();
        this.food.setActive( false );
        this.snake = new ArrayList<Ent>();
        this.player = new PhJoystick( gl10 );
        // this.background = new GameObject(240,400,480,800, GameBackgroundAtlas.getBackgroundImage(480, 0, 480, 800),GameObject.CollisionMask.NONE);
        this.init();
    }
    public void init()
    {
        this.setScore(0);

        // imposto dimensione massima  dello snake
        // this.snake = new Ent[1024];

        // ora la testa dello Snake e la imposto al centro dello schermo
        for( int i = 0; i< 3; i++)
        {
            this.snake.add(i,new Ent( new int[]{ (int)(( SCREEN_WIDTH / 2) / Ent.BOX_WIDTH) * Ent.BOX_WIDTH,(int)(( SCREEN_HEIGHT / 2)/Ent.BOX_HEIGHT) *Ent.BOX_HEIGHT }, 0, Ent.SNAKE_HUD ));
            this.snake.get(i).setActive(true);
        }

        this.spawnFood();
        this.food.setActive( true );
        // la coda sarà indefinita (null)
        // ora genero il cibo

    }

    public void updateScore( float deltaTime )
    {
           scoreHUD = new Text2D( Integer.toString( this.getScore() ),1,  10, 10 );
    }

    public void updatePlayer( float deltaTime )
    {

        // Log.d("Test","deltaTime: " + deltaTime);
        // if (this.getTime() >= 0.1) {
            // Log.d("Test","myTime: " + getTime() );
        if ( player.getState().equals( GameController.Direction.DOWN   ) && snake.get( 0 ).getDirection().equals( GameController.Direction.UP           ))
        {
            player.setState(GameController.Direction.UP);
        }
        else if ( player.getState().equals( GameController.Direction.UP     ) && snake.get( 0 ).getDirection().equals( GameController.Direction.DOWN    ))
        {
            player.setState(GameController.Direction.DOWN);
        }
        else if ( player.getState().equals( GameController.Direction.LEFT   ) && snake.get( 0 ).getDirection().equals( GameController.Direction.RIGHT   ))
        {
            player.setState(GameController.Direction.RIGHT);
        }
        else if( player.getState().equals( GameController.Direction.RIGHT  ) && snake.get( 0 ).getDirection().equals( GameController.Direction.LEFT    ))
        {
            player.setState(GameController.Direction.LEFT);
        }

        for (int i = snake.size() - 1; i >= 0; i--) {

            if (i == 0) {
                // imposta ogni pezzo di coda, nell'ultima direzione scelta dall'utente
                snake.get(0).setEntToDirection(player.getState());
                snake.get(0).setActive(true);
            }
            else {
                // imposta ogni pezzo di coda, nella posizione di quello precendete

                // snake.get(i).setEntToDirection(snake.get(i - 1).getDirection()); // per il momento meglio lasciarlo alla testa...

                snake.get( i ).setDirection( snake.get( i - 1).getDirection());
                snake.get( i ).setOrigin( snake.get( i - 1).getOrigin());
                snake.get( i ).setAngle( snake.get( i - 1).getAngle());
                snake.get( i ).setActive(true);
                if( i == snake.size() - 1)
                {
                    Ent tail = snake.get(i);
                    if(tail.getDirection().equals(GameController.Direction.UP))
                    {
                        tail.setGameImage( Ent.SNAKE_TAIL_UP_HUD );
                    }
                    else if(tail.getDirection().equals(GameController.Direction.DOWN))
                    {
                        tail.setGameImage( Ent.SNAKE_TAIL_DOWN_HUD );
                    }
                    else if(tail.getDirection().equals(GameController.Direction.LEFT))
                    {
                        tail.setGameImage( Ent.SNAKE_TAIL_LEFT_HUD );
                    }
                    else if(tail.getDirection().equals(GameController.Direction.RIGHT))
                    {
                        tail.setGameImage( Ent.SNAKE_TAIL_RIGHT_HUD );
                    }
                }
            }

            // Log.d("Posizione","coda " + i + " origin: "+ "(" + snake.get( i ).getOrigin()[0] + "," + snake.get( i ).getOrigin()[1] + ")");
        }
        // Log.d("Test","mi sono mosso...");

        //  this.setTime(0f);
    // }
    }

    public void updateFood( float deltaTime )
    {
        if( food.isActive() )
            return;
        this.spawnFood();
        this.food.setActive( true );
    }

    public Ent spawnFood()
    {
        if( !Utility.isDefined( this.food ))
            this.food = new Ent( this.getRandomOrigin(),90, Ent.FOOD_HUD);
        this.food.setOrigin( this.getRandomOrigin());
        return this.food;
    }

    public int[] getRandomOrigin()
    {
        Random rand = new Random();
        int[] origin = new int[2];

        // devono essere multipli di 16
        origin[0] = (int)( rand.nextInt( SCREEN_WIDTH  ) / Ent.BOX_WIDTH ) * Ent.BOX_WIDTH;
        origin[1] = (int)( rand.nextInt( SCREEN_HEIGHT ) / Ent.BOX_HEIGHT ) * Ent.BOX_HEIGHT;
        return origin;
    }

    // da rifare con utilizzo collision
    public void snakeEater()
    {
        // mangia il cibo
        if( Arrays.equals( snake.get(0).getOrigin(), food.getOrigin() ) )
        {
            Log.d("Test", "Magno" );
            this.setScore(this.getScore()+1);
            // this.snake.add( 1, new Ent( snake.get( 0 ).getOrigin(),90, Ent.SNAKE_HUD ) );

            Ent testa = snake.get(0);
            int origine[] = new int[2];
            origine[0] = testa.getOrigin()[0];
            origine[1] = testa.getOrigin()[1];
            int angolo = testa.getAngle();
            Ent ent = new Ent(origine, angolo, Ent.SNAKE_HUD);

            this.snake.add(1, ent);
            this.food.setActive( false );
            Log.d("player", "Sccore: " + this.getScore() );
        }

        // nel caso mangia se stesso il giocatore ha perso...
        else
        {
            for( int i = 1; i < snake.size(); i++)
            {
                if( Arrays.equals( snake.get( 0 ).getOrigin(), snake.get(i).getOrigin() ) )
                {
                    gameover = true;
                    return;
                }
            }
        }
    }

    public void drawSnake(GL10 gl10)
    {
        for (Ent snake_piece : snake) {
            if(Utility.isDefined(snake_piece))
                snake_piece.drawEntity( gl10 );
        }
    }
    public void drawFood(GL10 gl10)
    {
        if( Utility.isDefined( food ) )//&& food.isActive() )
            food.drawEntity( gl10 );
    }
    public void drawJoystick(GL10 gl10)
    {
        if( Utility.isDefined( player ) )
            player.draw();
    }
    public void drawBackground( GL10 gl10)
    {
        this.background.draw( gl10 );
    }
    public void drawScore(GL10 gl10)
    {
        if( Utility.isDefined( scoreHUD ) )
            scoreHUD.drawText(gl10);
    }
    public void print(int x, int y, char string)
    {
        // prima o poi farò un metodo che stampa una stringa...
    };

    /*
    // vecchi update player con array ma non va
    public void updatePlayer( float deltaTime ) {
        Log.d("Test","deltatime:" + deltaTime);
        Ent clone_snake[] = new Ent[snake.length];

        for(int i = 0; i < snake.length; i++ )
        {
            if( Utility.isDefined( snake[i]) )
                clone_snake[i] = new Ent(snake[i]);
        }

        snake[0].setEntToDirection( player.getState() );
        snake[0].setActive( true );


        // ora imposto la coda e aggiunge / sottrae la coda in base al punteggio
        // for( int i = 0 ; i < (this.getScore() + 1) % snake.length; i++ )
        for( int i = 1 ; i <= (this.getScore() + 1); i++ )
        {
            // if(!Utility.isDefined( snake[i]) && Utility.isDefined( clone_snake[i - 1] ) )
            snake[i] = null;
            snake[i] = new Ent(clone_snake[i - 1].getOrigin(), 90, Ent.SNAKE_HUD);
            snake[i].setEntToDirection(clone_snake[i-1].getDirection());
            snake[i].setActive(true);
            // }
        }

    }
    */
}
