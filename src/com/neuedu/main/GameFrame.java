package com.neuedu.main;

import com.neuedu.constant.FrameConstant;
import com.neuedu.runtime.*;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameFrame extends Frame {

    //创建背景对象
    private Background background = new Background();

    //创建飞机对象
    private Plane plane = new Plane();

    //创建子弹集合
    public final List<Bullet> bulletCopyOnWriteArrayList = new CopyOnWriteArrayList<>();

    //创建敌方子弹集合
    public  final List<EnemyBullet> enemyBulletList = new CopyOnWriteArrayList();

    //创建敌方飞机集合
    public final List<EnemyPlane> enemyPlaneList = new CopyOnWriteArrayList();

    public boolean gameOver = false;

    @Override
    public void paint(Graphics g) {

        if (!gameOver){
            background.draw(g);
            plane.draw(g);

            for (Bullet bullet : bulletCopyOnWriteArrayList) {
                bullet.draw(g);
            }


            for (EnemyBullet enemyBullet : enemyBulletList) {
                enemyBullet.draw(g);
            }

            for (EnemyPlane enemyPlane : enemyPlaneList) {
                enemyPlane.draw(g);
            }

            for (Bullet bullet : bulletCopyOnWriteArrayList){
                bullet.collisionTesting(enemyPlaneList);
            }

            for (EnemyBullet enemyBullet : enemyBulletList) {
                enemyBullet.collisionTesting(plane);
            }

            //g.setColor(Color.RED);
            //g.drawString("" + enemyBulletList.size(),100,100);

        }

    }

    /**
     * 使用这个方法初始化窗口
     */
    public void init(){
        //设置好尺寸
        setSize(FrameConstant.FRAME_WIDTH,FrameConstant.FRAME_HEIGHT);
        //设置居中
        setLocationRelativeTo(null);
        //禁止启动窗口时有图片
        enableInputMethods(false);

        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //添加键盘监听
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                plane.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                plane.keyReleased(e);
            }
        });


        new Thread(){
            @Override
            public void run() {
                //死循环
                while(true){
                    repaint();
                    try {
                        //睡眠，刷新
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }.start();

        //游戏初始化时添加一些敌方飞机
        enemyPlaneList.add(new EnemyPlane(50,30, ImageMap.get("ep01")));
        enemyPlaneList.add(new EnemyPlane(250,30, ImageMap.get("ep01")));
        enemyPlaneList.add(new EnemyPlane(400,30, ImageMap.get("ep01")));
        enemyPlaneList.add(new EnemyPlane(600,30, ImageMap.get("ep01")));

        setVisible(true);

    }

    //创建缓冲区
    private Image offScreenImage = null;

    @Override
    public void update(Graphics g){
        if (offScreenImage ==  null){
            offScreenImage = this.createImage(FrameConstant.FRAME_WIDTH,FrameConstant.FRAME_HEIGHT);
        }
        Graphics goff = offScreenImage.getGraphics();//创建离线图片的实例，在图片缓冲区绘图
        paint(goff);
        g.drawImage(offScreenImage,0,0,null);//将缓冲图片绘制到窗口目标
    }

}
