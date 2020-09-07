package com.game.snake;

import com.game.framework.gl.GameAtlas;
import com.game.framework.gl.GameImage;
import com.game.framework.objects.GameObject;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Jacopo on 07/11/2016.
 */

public class Ent extends GameObject{

    public static final int BOX_WIDTH = 32;
    public static final int BOX_HEIGHT = 32;
    // public static final GameImage SNAKE_HUD = new GameImage(598,279,616,298,BOX_WIDTH,BOX_HEIGHT);
    // public static final GameImage SNAKE_HUD = GameAtlas.getGameImage(283, 384, 46, 46);

    // snake head
    /*
    public static final GameImage SNAKE_HEAD_DOWN_HUD   = GameAtlas.getGameImage(908, 492, 68, 105);
    public static final GameImage SNAKE_HEAD_UP_HUD     = GameAtlas.getGameImage(836, 492, 68, 105);
    public static final GameImage SNAKE_HEAD_LEFT_HUD   = GameAtlas.getGameImage(801, 598, 105, 68);
    public static final GameImage SNAKE_HEAD_RIGHT_HUD  = GameAtlas.getGameImage(911, 598, 105, 68);
    */
    public static final GameImage SNAKE_HEAD_RIGHT_HUD   = GameAtlas.getGameImage(1  ,739, 256, 256);
    public static final GameImage SNAKE_HEAD_LEFT_HUD   = GameAtlas.getGameImage(258  ,739, 256, 256);
    public static final GameImage SNAKE_HEAD_DOWN_HUD   = GameAtlas.getGameImage(515  ,739, 256, 256);
    public static final GameImage SNAKE_HEAD_UP_HUD   = GameAtlas.getGameImage(772  ,739, 256, 256);

    public static final GameImage SNAKE_TAIL_RIGHT_HUD   = GameAtlas.getGameImage(1  ,996, 256, 256);
    public static final GameImage SNAKE_TAIL_LEFT_HUD   = GameAtlas.getGameImage(258  ,996, 256, 256);
    public static final GameImage SNAKE_TAIL_DOWN_HUD   = GameAtlas.getGameImage(515  ,996, 256, 256);
    public static final GameImage SNAKE_TAIL_UP_HUD   = GameAtlas.getGameImage(772  ,996, 256, 256);


    // snake body
    public static final GameImage SNAKE_HUD = GameAtlas.getGameImage(923, 455, 36, 36);
    // snake tail
    // public static final GameImage SNAKE_TAIL_HUD = GameAtlas.getGameImage(283, 384, 68, 105);

    public static final GameImage FOOD_HUD =GameAtlas.getGameImage(237, 338, 46, 46);
    // public static final int MOVE_SCALE = 16;
    // public static final GameImage FOOD_HUD = new GameImage(837,131,892,189,16,16);

    private int[] origin = new int[2];
    private int angle;
    private GameController.Direction direction;

    public Ent()
    {
        super(0, 0, BOX_WIDTH, BOX_HEIGHT, SNAKE_HUD, CollisionMask.NONE);
        this.setOrigin(0,0);
        this.setAngle(90);
        this.setDirection(GameController.Direction.UP);
    }

    public Ent( Ent entity )
    {
        super( entity.getOrigin()[0], entity.getOrigin()[1], entity.getWidth(), entity.getHeight(), entity.getGameImage(), entity.getCollisionMask() );
        this.setOrigin( entity.getOrigin()[0], entity.getOrigin()[1] );
        this.setAngle( entity.getAngle() );
        this.setDirection( entity.getDirection() );
    }

    /**
     * @param origin p(x, y)
     * @param angle 0<= x < 360
     * @param HUD Image to apply at this entity
     */
    public Ent(int[] origin, int angle, GameImage HUD)
    {
        super(origin[0], origin[1], BOX_WIDTH, BOX_HEIGHT, HUD, CollisionMask.RECTANGLE);
        this.setOrigin( origin[0], origin[1] );
        this.setAngle( angle );
        this.setDirection( GameController.Direction.UP );
    }

    public int getAngle() {
        return angle;
    }
    public void setAngle(int angle) {
        this.angle = angle % 360;
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
     * @param origin
     * origin[0] coordinata x
     * origin[1] coordinata y
     */
    public void setOrigin(int[] origin) {

        // DA NON FARE ASSOLUTAMENTE this.origin = origin !!!!!!!!!!!!! -> si sporca l'array se passato per riferimento
        this.origin[0] = origin[0];
        this.origin[1] = origin[1];
        this.setX( origin[0] );
        this.setY( origin[1] );
    }
    public void setOrigin(int x, int y) {
        this.origin[0] = x;
        this.setX( x );
        this.origin[1] = y;
        this.setY( y );
    }

    public GameController.Direction getDirection() {
        return direction;
    }
    public void setDirection(GameController.Direction direction) {
        this.direction = direction;
    }

    public void setEntToDirection(GameController.Direction state )
    {
        // imposta la direzione della testa prevedendo l'uscita dal mondo di gioco
        switch ( state )
        {
            case UP:
                if( this.getOrigin()[1] + BOX_HEIGHT >= Level.SCREEN_HEIGHT)
                    this.setOrigin( this.getOrigin()[0], 0 + BOX_HEIGHT );
                else
                    this.setOrigin( this.getOrigin()[0], this.getOrigin()[1] + BOX_HEIGHT);
                this.setAngle(90);
                this.setGameImage( SNAKE_HEAD_UP_HUD );
                break;

            case DOWN:
                if( this.getOrigin()[1] - BOX_HEIGHT  < 0)
                    this.setOrigin( this.getOrigin()[0], Level.SCREEN_HEIGHT - BOX_HEIGHT );
                else
                    this.setOrigin( this.getOrigin()[0], this.getOrigin()[1] - BOX_HEIGHT);
                this.setAngle(270);
                this.setGameImage( SNAKE_HEAD_DOWN_HUD );
                break;

            case RIGHT:
                if( this.getOrigin()[0] + BOX_WIDTH >= Level.SCREEN_WIDTH)
                    this.setOrigin( 0 + BOX_WIDTH, this.getOrigin()[1] );
                else
                    this.setOrigin( this.getOrigin()[0] + BOX_WIDTH, this.getOrigin()[1]);
                this.setAngle(0);
                this.setGameImage( SNAKE_HEAD_RIGHT_HUD );
                break;

            case LEFT:
                if( this.getOrigin()[0] - BOX_WIDTH < 0)
                    this.setOrigin( Level.SCREEN_WIDTH - BOX_WIDTH, this.getOrigin()[1] );
                else
                    this.setOrigin( this.getOrigin()[0] - BOX_WIDTH, this.getOrigin()[1]);
                this.setAngle(180);
                this.setGameImage( SNAKE_HEAD_LEFT_HUD );
                break;

            default:
                break;
        }
        this.setDirection( state );
    }

    public void drawEntity(GL10 gl10)
    {
        this.draw(gl10);

    }

    @Override
    public void draw(GL10 gl) {
        float angle = 0;
        ShortBuffer triangles = getMesh().getTrianglesBuffer();
        FloatBuffer buffer = getMesh().getVerticesBuffer();

        buffer.position(0);
        gl.glVertexPointer(2, GL10.GL_FLOAT, (2 + 2) * 4, buffer);
        buffer.position(2);

        gl.glTexCoordPointer(2, GL10.GL_FLOAT, (2 + 2) * 4, buffer);
        gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT,triangles);

    }
}
