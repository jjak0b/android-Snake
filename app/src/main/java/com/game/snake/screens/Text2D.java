package com.game.snake.screens;

import com.game.framework.gl.GameAtlas;
import com.game.framework.objects.GameObject;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Jacopo on 16/11/2016.
 */

public class Text2D extends GameObject{

    public static int   FONT_SIZE = 32;
    public static int   FONT_PADDING = 18;
    private float       scale;
    private String      text;
    private GameFont[]  textBuffer;
    private GameFont[]  chars;  // contiene le immagini dei font

    // coordinate dove inizia il testo

    public Text2D(String text, float scale , float x_start, float y_start) {
        super(  x_start,
                y_start,
                (int)( FONT_SIZE * scale * text.length() ),
                (int)( FONT_SIZE * scale ),
                GameAtlas.getGameImage(0, 0, 0, 0),
                CollisionMask.NONE);

        this.setScale( scale );
        init();
        this.setText( text );
        this.setXText( x_start );
        this.setYText( y_start );

        int width = 0;
        int height = 0;
        for( int i = 0; i < this.getText().length(); i++ )
        {
            width += textBuffer[i].getWidth();
            height = Math.max( height, textBuffer[i].getHeight() );
        }
        // imposta la larghezza del "bottone" come la somma delle larghezze dei vari fonts
        this.setWidth( width );
        // imposta l'altezza del "bottone" del carattere più alto
        this.setHeight( height);
    }

    public void setScale( float scale )
    {
        this.scale = scale;
    }
    public float getScale()
    {
        return this.scale;
    }

    private void init()
    {
        chars = new GameFont[38];
        //A-P
        int x=1;
        int y=1;
        int width = 32;
        for(int i=0;i<16;i++){
            chars[i]=new GameFont((char)(65+i),-1, -1, (int)(31*getScale()), (int)(31*getScale()), GameAtlas.getGameImage(x, y, 31, 31));
            x+=width;
        }

        //Q-Z
        x=1;
        y=33;
        for(int i=16;i<26;i++){
            chars[i]=new GameFont((char)(65+i),-1, -1, (int)(31*getScale()), (int)(31*getScale()), GameAtlas.getGameImage(x, y, 31, 31));
            x+=width;
        }
        x=321;
        y=33;
        width= 16;
        int j = 0;
        for(int i=26;i<38;i++){
            // 0 - 9
            if(i < 36)
                chars[i] = new GameFont((char)(48+j),-1, -1, (int)(15*getScale()), (int)(28*getScale()),GameAtlas.getGameImage(x, y, 15, 31));
            // "." e " "
            else if ( i == 36)
                chars[i] = new GameFont((char)(46),-1, -1,  (int)(15*getScale()), (int)(28*getScale()),GameAtlas.getGameImage(x, y, 15, 31));
            else
                chars[i] = new GameFont((char)(32),-1, -1, (int)(15*getScale()), (int)(28*getScale()),GameAtlas.getGameImage(x, y, 15, 31));

            x+=width;
            j++;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.fillTextFontBuffer( text );
    }

    private void fillTextFontBuffer(String text)
    {
        this.textBuffer = new GameFont[ text.length() ];
        for(int i = 0; i < text.length(); i++)
        {
            textBuffer[i] = this.getFont( text.charAt( i ) );
        }
    }

    public void setXText(float x)
    {
        this.setX(x);
        for(int i = 0; i < this.getText().length(); i++)
        {
            this.textBuffer[ i ].setX( x );
            x += FONT_PADDING * getScale();
        }
    }
    public void setYText(float y)
    {
        this.setY(y);
        for(int i = 0; i < this.getText().length(); i++)
        {
            this.textBuffer[ i ].setY( y );
        }
    }

    public GameFont getFont(char value )
    {

       for(int i = 0; i < chars.length; i++ )
       {
           if( Character.toString( chars[ i ].getValue()    ).toUpperCase().equals(
               Character.toString( value                    ).toUpperCase()        ) )
           {
               return new GameFont( chars[ i ] );
           }
       }
        return null;
    }

    public void setActive(boolean show)
    {
        for ( GameFont font : textBuffer) {
            font.setActive( show );
        }
    }
/*
    public static GameImage getFontImageByChar(char value)
    {
        GameImage image;
        int x = 0, y = 0; // punto di partenza in alto a sinistra dell'immagine
        int width = 0, height = 0; // entro quanti pixel deve prendere l'imamgine

        switch( value )
        {
            case 'a':
            case 'A':
                x       = 3;
                y       = 0;
                width   = 25;
                height  = 32;
                break;

            case 'b':
            case 'B':
                x       = 36;
                y       = 0;
                width   = 22;
                height  = 32;
                break;

            case 'c':
            case 'C':
                x       = 69;
                y       = 0;
                width   = 22;
                height  = 32;
                break;

            case 'd':
            case 'D':
                x       = 101;
                y       = 0;
                width   = 22;
                height  = 32;
                break;

            case 'e':
            case 'E':
                x       = 136;
                y       = 0;
                width   = 18;
                height  = 32;
                break;

            case 'f':
            case 'F':
                x       = 168;
                y       = 0;
                width   = 17;
                height  = 32;
                break;

            case 'g':
            case 'G':
                x       = 199;
                y       = 0;
                width   = 21;
                height  = 32;
                break;

            case 'h':
            case 'H':
                x       = 229;
                y       = 0;
                width   = 22;
                height  = 32;
                break;

            case 'i':
            case 'I':
                x       = 266;
                y       = 0;
                width   = 12;
                height  = 32;
                break;

            case 'j':
            case 'J':
                x       = 297;
                y       = 0;
                width   = 14;
                height  = 32;
                break;

            case 'k':
            case 'K':
                x       = 325;
                y       = 0;
                width   = 24;
                height  = 32;
                break;

            case 'l':
            case 'L':
                x       = 359;
                y       = 0;
                width   = 16;
                height  = 32;
                break;

            case 'm':
            case 'M':
                x       = 386;
                y       = 0;
                width   = 28;
                height  = 32;
                break;

            case 'n':
            case 'N':
                x       = 421;
                y       = 0;
                width   = 22;
                height  = 32;
                break;

            case 'o':
            case 'O':
                x       = 453;
                y       = 0;
                width   = 22;
                height  = 32;
                break;

            case 'p':
            case 'P':
                x       = 486;
                y       = 0;
                width   = 22;
                height  = 32;
                break;

            case 'q':
            case 'Q':
                x       = 5;
                y       = 32;
                width   = 22;
                height  = 33;
                break;

            case 'r':
            case 'R':
                x       = 38;
                y       = 32;
                width   = 22;
                height  = 32;
                break;

            case 's':
            case 'S':
                x       = 69;
                y       = 32;
                width   = 23;
                height  = 32;
                break;

            case 't':
            case 'T':
                x       = 101;
                y       = 32;
                width   = 21;
                height  = 32;
                break;

            case 'u':
            case 'U':
                x       = 133;
                y       = 32;
                width   = 22;
                height  = 32;
                break;

            case 'v':
            case 'V':
                x       = 163;
                y       = 32;
                width   = 25;
                height  = 32;
                break;

            case 'w':
            case 'W':
                x       = 192;
                y       = 32;
                width   = 33;
                height  = 32;
                break;

            case 'x':
            case 'X':
                x       = 227;
                y       = 32;
                width   = 25;
                height  = 32;
                break;

            case 'y':
            case 'Y':
                x       = 261;
                y       = 32;
                width   = 22;
                height  = 32;
                break;

            case 'z':
            case 'Z':
                x       = 294;
                y       = 32;
                width   = 18;
                height  = 32;
                break;

            case '0':
                x       = 320;
                y       = 32;
                width   = 16;
                height  = 33;
                break;

            case '1':
                x       = 336;
                y       = 32;
                width   = 13;
                height  = 33;
                break;

            case '2':
                x       = 353;
                y       = 32;
                width   = 16;
                height  = 33;
                break;

            case '3':
                x       = 369;
                y       = 32;
                width   = 16;
                height  = 33;
                break;

            case '4':
                x       = 385;
                y       = 32;
                width   = 16;
                height  = 33;
                break;

            case '5':
                x       = 401;
                y       = 32;
                width   = 16;
                height  = 33;
                break;

            case '6':
                x       = 417;
                y       = 32;
                width   = 16;
                height  = 33;
                break;

            case '7':
                x       = 434;
                y       = 32;
                width   = 14;
                height  = 33;
                break;

            case '8':
                x       = 449;
                y       = 32;
                width   = 16;
                height  = 33;
                break;

            case '9':
                x       = 465;
                y       = 32;
                width   = 16;
                height  = 33;
                break;

            default:
                x       = 484;
                y       = 32;
                width   = 9;
                height  = 33;
                break;

        }

        image = GameAtlas.getGameImage( x, y, width, height );
        return image;
    }
*/
    public void drawText(GL10 gl10)
    {
        for ( GameFont font : textBuffer ) {
            font.draw( gl10 );
        }
    }
}
