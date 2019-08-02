package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.util.List;

public class Bullet extends BaseSprite implements Moveable, Drawable {

    private Image image;

    private int speed = FrameConstant.GAME_SPEED * 5;

    public Bullet() {
        this(0,0, ImageMap.get("mb01"));
    }

    public Bullet(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        move();
        borderTesting();
        g.drawImage(image,getX(),getY(),image.getWidth(null),image.getHeight(null),null);
    }

    @Override
    public void move() {
        setY(getY() - speed );
    }

    public void borderTesting(){
        if (getY() < 30 - image.getHeight(null)){
           GameFrame gameFrame = DataStore.get("gameFrame");
           gameFrame.bulletCopyOnWriteArrayList.remove(this);
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image.getWidth(null),image.getHeight(null));
    }
    //碰撞检测
    public void collisionTesting(List<EnemyPlane> enemyPlaneList){
        GameFrame gameFrame = DataStore.get("gameFrame");
        for (EnemyPlane enemyPlane : enemyPlaneList ) {
            if (enemyPlane.getRectangle().intersects(this.getRectangle())){
                enemyPlaneList.remove(enemyPlane);
                gameFrame.bulletCopyOnWriteArrayList.remove(this);
                gameFrame.score += enemyPlane.getType();
                gameFrame.hp++;
            }
        }
    }

    public void collisionTesting(Boss boss){
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (boss.getRectangle().intersects(this.getRectangle())) {
            gameFrame.bulletCopyOnWriteArrayList.remove(this);
            if(gameFrame.bosshp >= 0){
                gameFrame.bosshp --;
            }
        }

    }

}
