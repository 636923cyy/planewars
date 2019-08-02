package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Boss extends BaseSprite implements Moveable, Drawable {


    public  List<Image> imageList = new ArrayList<>();

    public int index = 0;

    Random random = new Random();

    private boolean right = true;
    private boolean top = true;

    private int speed = FrameConstant.GAME_SPEED * 2;

    public Boss() {

        for (int i = 1; i <10 ; i++) {
            imageList.add(ImageMap.get("boss" + i));
        }
    }

    public Boss(int x, int y, List<Image> imageList) {
        super(x, y);
        this.imageList = imageList;
    }


    @Override
    public void draw(Graphics g) {
        move();
        fire();

        g.drawImage(imageList.get(index++ / 2),getX(),getY(),imageList.get(0).getWidth(null),
          imageList.get(0).getHeight(null),null);
            if (index >= 18){
                index = 0;
            }

    }


    public  double angle;
    public void fire(){
        angle +=Math.PI / 4;
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (random.nextInt(1000) > 980){
            gameFrame.bossBulletList.add(new BossBullet(
                    getX() + imageList.get(0).getWidth(null)/ 2 + 30 +(int)Math.cos(angle),
                    getY() + imageList.get(0).getHeight(null) / 2 + 30 +(int)Math.sin(angle),
                    ImageMap.get("boss0b")));
        }
    }



    @Override
    public void move() {
        borderTesting();
        if (right) {
            setX(getX() + speed);
            setY(getY() + speed);
        }else {
            setX(getX() - speed);
            setY(getY() - speed);
        }

    }


    public void borderTesting(){
        if (getX() + imageList.get(0).getWidth(null) >= FrameConstant.FRAME_WIDTH){
            right = false;
        }else if (getX() < 0){
            right = true;
        }
        //if (getY() + imageList.get(0).getHeight(null) >= FrameConstant.FRAME_HEIGHT){
        //    top = false;
        //}else if (getY() < 0){
        //    top = true;
        //}

    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),imageList.get(0).getWidth(null),imageList.get(0).getHeight(null));
    }


    public void collisionTesting(Plane plane){
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())) {
            if(gameFrame.hp > 0){
                gameFrame.hp -= 10;
                if (gameFrame.hp == 0){
                    gameFrame.gameOver = true;
                }
            }
        }

    }

}
