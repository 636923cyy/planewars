package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.util.Random;

public class Prop extends BaseSprite implements Moveable, Drawable {

    private Image image;

    private int speed = FrameConstant.GAME_SPEED * 3;

    private Random random = new Random();

    public Prop() {
        this(0,0, ImageMap.get("ab01"));
    }

    public Prop(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        move();
        g.drawImage(image,getX(),getY(),image.getWidth(null),image.getHeight(null),null);

    }

    @Override
    public void move() {
        borderTesting();
        setY(getY() + speed);

    }

    public void borderTesting(){
        if (getY() > FrameConstant.FRAME_HEIGHT){
            GameFrame gameFrame = new DataStore().get("gameFrame");
            gameFrame.propList.remove(this);
        }
    }


    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image.getWidth(null),image.getHeight(null));
    }

    //碰撞检测
    public void collisionTesting(Plane plane){
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())) {
            gameFrame.propList.remove(this);
            gameFrame.hp += 10;
            if (gameFrame.hp <= 0){
                gameFrame.gameOver = true;
            }
        }

    }

}
