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
import java.util.concurrent.CopyOnWriteArrayList;

public class Boss extends BaseSprite implements Moveable, Drawable {


    public  List<Image> imageList = new ArrayList<>();

    public int index = 0;

    Random random = new Random();

    private int speed = FrameConstant.GAME_SPEED * 3;

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
        g.drawImage(imageList.get(index++ / 2),100,100,imageList.get(0).getWidth(null),
                imageList.get(0).getHeight(null),null);
        if (index >= 18){
            index = 0;
        }

    }


    public void fire(){
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (random.nextInt(1000) > 975){
            gameFrame.bossBulletList.add(new BossBullet(getX() + (imageList.get(0).getWidth(null)/2) - (ImageMap.get("boss0b").getWidth(null)/2),
                    getY() + imageList.get(0).getHeight(null),
                    ImageMap.get("boss0b")));

        }
    }



    @Override
    public void move() {
        borderTesting();

    }


    public void borderTesting(){

    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),imageList.get(0).getWidth(null),imageList.get(0).getHeight(null));
    }



}
