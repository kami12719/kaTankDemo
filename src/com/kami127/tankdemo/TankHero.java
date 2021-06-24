package com.kami127.tankdemo;

import java.util.Vector;

/**
 * @author KAMI
 * @version 1.0
 * @Date 2021/4/1 15:38
 * 己方坦克
 **/
public class TankHero extends Tank {

    /**
     * hero最多同屏炮弹数
     */
    private int maxShots = 5;

    /**
     * hero射速间隔，默认500ms
     */
    private int maxShotSpeed = 500;

    /**
     * 构造方法
     * @param x 坐标
     * @param y 坐标
     * @param direct 方向0上、1下、2左、3右
     */
    public TankHero(int x, int y, int direct) {
        super(x, y, direct);
    }



    public int getMaxShots() {
        return maxShots;
    }

    public void setMaxShots(int maxShot) {
        this.maxShots = maxShot;
    }

    public int getMaxShotSpeed() {
        return maxShotSpeed;
    }

    public void setMaxShotSpeed(int maxShotSpeed) {
        this.maxShotSpeed = maxShotSpeed;
    }
}
