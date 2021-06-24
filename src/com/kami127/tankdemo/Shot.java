package com.kami127.tankdemo;

/**
 * @author KAMI
 * @version 1.0
 * @Date 2021/4/11 14:45
 */
public class Shot implements Runnable {

    private int x;
    private int y;
    /**
     * 子弹方向 0上1下2左3右
     */
    private int direct;
    /**
     * 子弹速度
     */
    private int speed = 4;
    /**
     * 是否存活
     */
    private boolean isLive = true;

    /**
     * 是否在地图内
     */
    private boolean isIn = true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public boolean isIn() {
        return isIn;
    }

    public void setIn(boolean in) {
        isIn = in;
    }

    @Override
    //射击行为
    public void run() {
        while (this.isLive) {
            //休眠50ms，子弹坐标修改频率 50ms
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向来改变炮弹的x,y坐标
            switch (direct) {
                case 0:
                    y -= speed;
                    break;
                case 1:
                    y += speed;
                    break;
                case 2:
                    x -= speed;
                    break;
                case 3:
                    x += speed;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + direct);
            }
            //System.out.println("炮弹"+ Thread.currentThread().getName() +"坐标：（"+ x + "，" + y+"）");

            //当炮弹移动到面板边界时，销毁
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750)) {
//                    System.out.println("炮弹" + Thread.currentThread().getName() + "出界销毁");
                this.isLive = false;
                this.isIn = false;
//                    break;
            }
        }

    }
}
