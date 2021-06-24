package com.kami127.tankdemo;

/**
 * @author KAMI
 * @version 1.0
 * @Date 2021/4/15 10:55
 **/
public class Bomb implements Runnable {
    private int x, y;
    /**
     * 循环展示次数
     */
    private int loopB = 100;
    private boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
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

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    @Override
    public void run() {
        while (this.isLive) {
            //休眠50ms，爆炸时长
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println("爆炸" + Thread.currentThread().getName() + "到时销毁");
            this.isLive = false;
        }
    }
}
