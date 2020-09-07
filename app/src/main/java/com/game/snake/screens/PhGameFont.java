package com.game.snake.screens;

import com.game.framework.gl.GameAtlas;
import com.game.framework.objects.GameObject;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Jacopo on 17/11/2016.
 */

public class PhGameFont {

    private GameObject[] chars;

    public PhGameFont(){
        chars = new GameObject[38];
        //A-P
        int x=1;
        int y=1;
        int width=32;
        for(int i=0;i<16;i++){
            chars[i]=new GameObject(-1, -1, 31, 31,
                    GameAtlas.getGameImage(x, y, 31, 31),
                    GameObject.CollisionMask.NONE);
            x+=width;
        }

        //Q-Z
        x=1;
        y=33;
        for(int i=16;i<26;i++){
            chars[i]=new GameObject(-1, -1, 31, 31,
                    GameAtlas.getGameImage(x, y, 31, 31),
                    GameObject.CollisionMask.NONE);
            x+=width;
        }
        x=321;
        y=33;
        width=16;
        for(int i=26;i<38;i++){
            chars[i]=new GameObject(-1, -1, 15, 28,
                    GameAtlas.getGameImage(x, y, 15, 31),
                    GameObject.CollisionMask.NONE);
            x+=width;
        }

    }

    public void drawText(StringBuilder word,float x,float y,GL10 gl){
        float width=18;

        for (int i=0;i<word.length();i++){
            int character = word.charAt(i);
            if(character>=65 && character<=90){
                character-=65;
            }else if(character>=48 && character<=57){
                character-=48;
                character+=26;
            }else if(character==46){
                character=36;
            }else {
                character=37;
            }
            GameObject object=chars[character];

            object.setX(x);
            object.setY(y);
            x+=width;
            object.draw(gl);
        }
    }
}