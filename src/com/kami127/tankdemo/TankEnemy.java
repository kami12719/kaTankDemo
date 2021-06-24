package com.kami127.tankdemo;

import java.awt.event.KeyEvent;
import java.util.Vector;

/**
 * @author KAMI
 * @version 1.0
 * @Date 2021/4/6 21:56
 */
public class TankEnemy extends Tank implements Runnable {
    /**
     * 定义玩家坦克
     */
    private TankHero tEHero;

    /**
     * 定义玩家老家坐标
     */
    private int homeX = 500;
    private int homeY = 700;

    /**
     * hero射速间隔，默认1s
     */
    private int maxShotSpeed = 500;

    public TankEnemy(int x, int y, int direct, TankHero tEHero) {
        super(x, y, direct);
        this.tEHero = tEHero;
    }

    /**
     * @param th
     * @return a[0]目标X坐标，a[1] 目标Y坐标
     */
    public int[] getGoal(TankHero th) {
        /**
         * 计算到老家与到己方坦克的距离
         */
        double toHome = java.lang.Math.sqrt(Math.pow((this.getX() - homeX), 2) + Math.pow((this.getY() - homeY), 2));
        double toHero = java.lang.Math.sqrt(Math.pow((this.getX() - th.getX()), 2) + Math.pow((this.getY() - th.getY()), 2));
        int a[] = new int[2];
//        System.out.println("敌方"+ Thread.currentThread().getName()+"到家的距离为："+toHome+"，到己方的距离为："+toHero);
        if (toHome < toHero) {
            a[0] = homeX;
            a[1] = homeY;
        } else {
            a[0] = th.getX();
            a[1] = th.getY();
        }
        return a;
    }

    /**
     * 先随机选择向x或y移动，再根据目标坐标移动
     */
    public void move2Goal() {
        //获取敌方坦克方向
        if ((int) (Math.random() + 0.5) == 0) {
            if (this.getY() > this.getGoal(tEHero)[1]) {
                for (int i = 0; i < 30; i++) {
                    runUp();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                for (int i = 0; i < 30; i++) {
                    runDown();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (this.getX() > this.getGoal(tEHero)[0]) {
            for (int i = 0; i < 30; i++) {
                runLeft();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (int i = 0; i < 30; i++) {
                runRight();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 返回th是否在靠近自己
     *
     * @param th 目标
     * @return true   or   false
     */
    public boolean isClose(TankHero th) {
        double far1 = java.lang.Math.sqrt(Math.pow((this.getX() - th.getX()), 2) + Math.pow((this.getY() - th.getY()), 2));
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double far2 = java.lang.Math.sqrt(Math.pow((this.getX() - th.getX()), 2) + Math.pow((this.getY() - th.getY()), 2));
        System.out.println("坦克" + Thread.currentThread().getName() + "与目标距离差：" + (far1 - far2));
        if (far1 - far2 >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public void shotPlus(TankHero th) {
        /**
         * time 子弹发出到接触到最近边的距离
         * distance  对方这段时间移动的距离（像素）
         */
        int time, distance;
        boolean mayShotted = false;
        Shot shot = new Shot(this.getShotPoint()[0], this.getShotPoint()[1], this.getDirect());
        switch (this.getDirect()) {
            case 0:
                if (th.getY() < this.getY()) {
                    time = (int) Math.abs((th.getPoint(2)[1] - this.getShotPoint()[1]) / shot.getSpeed());
                    distance = time * th.getSpeed();
//                    System.out.println("敌方坦克" + Thread.currentThread().getName() + "击中时间：" + time + "，我方移动距离：" + distance);
                    mayShotted = (
                            (th.getDirect() == 3 && ((th.getPoint(2)[0] + distance) <= this.getX()) && ((th.getPoint(3)[0] + distance) >= this.getX())) ||
                            (th.getDirect() == 2 && ((th.getPoint(2)[0] - distance) <= this.getX()) && ((th.getPoint(3)[0] - distance) >= this.getX())) ||
                            (th.getPoint(2)[0] <= this.getX() && th.getPoint(3)[0] >= this.getX() && (
                                    th.getDirect() == 1 || (th.getDirect() == 0 && th.getSpeed() < shot.getSpeed())
                            )));
                }
                break;
                //本机方向向下
            case 1:
                //判断玩家在本机下方
                if (th.getY() > this.getY()) {
                    //计算玩家与本机之间Y轴差值，除以子弹速度
                    time = (int) Math.abs((th.getPoint(0)[1] - this.getShotPoint()[1]) / shot.getSpeed());
                    //计算子弹到达玩家Y坐标后，玩家移动的距离
                    distance = time * th.getSpeed();
//                    System.out.println("敌方坦克" + Thread.currentThread().getName() + "击中时间：" + time + "，我方移动距离：" + distance);
                    //子弹是否能接触到玩家
                    mayShotted = (
                            //玩家向右移动，且玩家左上角预计位置X坐标<=子弹射出X坐标，右上角预计位置X坐标>=子弹射出X坐标
                            (th.getDirect() == 3 && (th.getPoint(0)[0] + distance) <= this.getX() && (th.getPoint(1)[0] + distance) >= this.getX()) ||
                            //玩家向左移动，且玩家左上角预计位置X坐标<=子弹射出X坐标，右上角预计位置X坐标>=子弹射出X坐标
                            (th.getDirect() == 2 && (th.getPoint(0)[0] - distance) <= this.getX() && (th.getPoint(1)[0] - distance) >= this.getX()) ||
                            //玩家左上角X坐标<=子弹射出X坐标，右上角X坐标>=子弹射出X坐标
                            (th.getPoint(0)[0] <= this.getX() && th.getPoint(1)[0] >= this.getX() && (
                                    //玩家上下移动，且玩家向下移动时玩家移动速度<本机子弹速度
                                    th.getDirect() == 0 || (th.getDirect() == 1 && th.getSpeed() < shot.getSpeed())
                            )));
                }
                break;
            case 2:
                if (th.getX() < this.getX()) {
                    time = (int) Math.abs((th.getPoint(1)[0] - this.getShotPoint()[0]) / shot.getSpeed());
                    distance = time * th.getSpeed();
//                    System.out.println("敌方坦克" + Thread.currentThread().getName() + "击中时间：" + time + "，我方移动距离：" + distance);
                    mayShotted = (
                            (th.getDirect() == 0 && (th.getPoint(1)[1] - distance) <= this.getY() && (th.getPoint(3)[1] - distance) >= this.getY()) ||
                            (th.getDirect() == 1 && (th.getPoint(1)[1] + distance) <= this.getY() && (th.getPoint(3)[1] + distance) >= this.getY()) ||
                            (th.getPoint(1)[1] <= this.getY() && th.getPoint(3)[1] >= this.getY() && (
                                    th.getDirect() == 3 || (th.getDirect() == 2 && th.getSpeed() < shot.getSpeed())
                            )));
                }
                break;
            case 3:
                if (th.getX() > this.getX()) {
                    time = (int) Math.abs((th.getPoint(0)[0] - this.getShotPoint()[0]) / shot.getSpeed());
                    distance = time * th.getSpeed();
//                    System.out.println("敌方坦克" + Thread.currentThread().getName() + "击中时间：" + time + "，我方移动距离：" + distance);
                    mayShotted = (
                            (th.getDirect() == 0 && (th.getPoint(0)[1] - distance) <= this.getY() && (th.getPoint(2)[1] - distance) >= this.getY()) ||
                            (th.getDirect() == 1 && (th.getPoint(0)[1] + distance) <= this.getY() && (th.getPoint(2)[1] + distance) >= this.getY()) ||
                            (th.getPoint(1)[1] <= this.getY() && th.getPoint(3)[1] >= this.getY() && (
                                    th.getDirect() == 2 || (th.getDirect() == 3 && th.getSpeed() < shot.getSpeed())
                            )));
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.getDirect());
        }
        if (mayShotted) {
            System.out.println("在预测范围内，" + Thread.currentThread().getName() + "射击!");
            this.getShots().add(shot);
            //启动线程
            new Thread(shot).start();
        }
    }

    @Override
    public void run() {
        long lastShotTime = 0;
        boolean isTouch = false;
        while (this.isLive()) {
            move2Goal();
            if ((System.currentTimeMillis() - lastShotTime > this.maxShotSpeed)) {
                this.shotPlus(tEHero);
                lastShotTime = System.currentTimeMillis();
            }
        }
    }
}
