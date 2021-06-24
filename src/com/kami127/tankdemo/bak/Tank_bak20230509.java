package com.kami127.tankdemo.bak;

import com.kami127.tankdemo.Shot;
import com.kami127.tankdemo.TankEnemy;
import com.kami127.tankdemo.TankHero;

import java.util.Vector;

/**
 * @author KAMI
 * @Date 2021/4/1 10:31
 **/
public class Tank_bak20230509 {
    private int x;  //横坐标
    private int y;  //纵坐标
    private int direct; //方向0上，1下，2左，3右
    private int speed = 2;  //默认速度2
    private boolean isLive = true;

    /**
     * 定义玩家坦克
     */
    private TankHero tHero;

    /**
     * 定义敌方坦克
     */
    private Vector<TankEnemy> enemies = new Vector<>();

    /**
     * 定义所有坦克
     */
    private Vector<Tank_bak20230509> tank = new Vector<>();

    /**
     * 定义一个Shot对象，表示一个射击（线程）
     */
    private Vector<Shot> shots = new Vector<>();

    public Tank_bak20230509(int x, int y, int direct) {
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

    public TankHero gettHero() {
        return tHero;
    }

    public void settHero(TankHero tHero) {
        this.tHero = tHero;
    }

    public Vector<TankEnemy> getEnemies() {
        return enemies;
    }

    //用于将MyPanel的enemies成员传递给TankEnemy类
    public void setEnemies(Vector<TankEnemy> enemies) {
        this.enemies = enemies;
    }

    public Vector<Tank_bak20230509> getTank() {
        return tank;
    }

    public void setTank(Vector<Tank_bak20230509> tank) {
        this.tank = tank;
    }

    public void runUp() {
        this.direct = 0;
        if (!this.isTouched()) {
            if (this.getPoint(0)[1] > 0) {
                this.y -= this.speed;
            } else {
                this.y = (0 + 30);
            }
        }
    }

    public void runDown() {
        this.direct = 1;
        if (!this.isTouched()) {
            if (this.getPoint(2)[1] < 750) {
                this.y += this.speed;
            } else {
                this.y = (750 - 30);
            }
        }
    }

    public void runLeft() {
        this.direct = 2;
        if (!this.isTouched()) {
            if (this.getPoint(0)[0] > 0) {
                this.x -= this.speed;
            } else {
                this.x = (0 + 30);
            }
        }
    }

    public void runRight() {
        this.direct = 3;
        if (!this.isTouched()) {
            if (this.getPoint(1)[0] < 1000) {
                this.x += this.speed;
            } else {
                this.x = (1000 - 30);
            }
        }
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Vector<Shot> getShots() {
        return shots;
    }

    public void setShots(Vector<Shot> shots) {
        this.shots = shots;
    }

    /**
     * 获取炮口坐标
     *
     * @return x=a[0]   y=a[1]
     */
    public int[] getShotPoint() {
//        int a[] = new int[2];
        switch (direct) {
            //向上
            case 0:
                /*a[0] = getX();
                a[1] = getPoint(0)[1];
                break;*/
                return new int[]{getX(),getPoint(0)[1]};
            //下
            case 1:
                /*a[0] = getX();
                a[1] = getPoint(2)[1];
                break;*/
                return new int[]{getX(),getPoint(2)[1]};
            //左
            case 2:
                /*a[0] = getPoint(0)[0];
                a[1] = getY();
                break;*/
                return new int[]{getPoint(0)[0],getY()};
            //右
            case 3:
                /*a[0] = getPoint(1)[0];
                a[1] = getY();
                break;*/
                return new int[]{getPoint(1)[0],getY()};
            default:
                throw new IllegalStateException("Unexpected value: " + getDirect());
        }
//        return a;
    }

    /**
     * 获取坦克四角坐标
     *
     * @param point 0左上角 1右上角 2左下角 3右下角
     * @return xx角 坐标b[0] x坐标，b[1] y
     */
    public int[] getPoint(int point) {
        /**
         * [0]左边x，[1]上边y，[2]右边x，[3]下边y
         */
        int[] a = new int[4];
        switch (direct) {
            case 0:
            case 1:
                a[0] = x - 20;
                a[1] = y - 30;
                a[2] = x + 20;
                a[3] = y + 30;
                break;
            case 2:
            case 3:
                a[0] = x - 30;
                a[1] = y - 20;
                a[2] = x + 30;
                a[3] = y + 20;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direct);
        }
        /**
         * b[0] x坐标，b[1] y
         */
        int[] b = new int[2];
        switch (point) {
            case 0:
                b[0] = a[0];
                b[1] = a[1];
                System.out.print(Thread.currentThread().getName() +"方向："+ direct + "左上角：(");
                break;
            //return new int[2]{a[0],a[1]};
            case 1:
                b[0] = a[2];
                b[1] = a[1];
                System.out.print(Thread.currentThread().getName() +"方向："+ direct  + "右上角：(");
                break;
            //return new int[2]{a[2],a[1]};
            case 2:
                b[0] = a[0];
                b[1] = a[3];
                System.out.print(Thread.currentThread().getName() +"方向："+ direct  + "左下角：(");
                break;
//            return new int[2]{a[0],a[3]};
            case 3:
                b[0] = a[2];
                b[1] = a[3];
                System.out.print(Thread.currentThread().getName() +"方向："+ direct  + "右下角：(" );
                break;
//            return new int[2]{a[2],a[3]};
            default:
                throw new IllegalStateException("Unexpected value: " + point);
        }
        System.out.println(b[0] + "," + b[1] + ")，中心点("+x+","+y+")");
        return b;
    }

    /**
     * 射击
     */
    public void tankShot() {
        //创建Shot对象，根据当前TankHero对象的位置和方向来创建Shot
        Shot shot = new Shot(getShotPoint()[0], getShotPoint()[1], getDirect());

        this.getShots().add(shot);
        //启动Shot线程
        new Thread(shot).start();
    }

    /**
     * 输入目标坦克，返回是否与本坦克接触
     *
     * @param th 目标坦克
     * @return true 已接触，false 未接触
     */
    public boolean isTouch(Tank_bak20230509 th) {

        /**
         判断本体是否在目标范围外
         */
        /*
        switch (this.getDirect()){
            case 0:
                if (
                        this.getPoint(0)[1] >= th.getPoint(2)[1] ||
                                this.getPoint(0)[0] >= th.getPoint(1)[0] ||
                                this.getPoint(1)[0] <= th.getPoint(0)[0]
                ){
                    return false;
                }
                break;
            case 1:
                if (
                        this.getPoint(2)[1] <= th.getPoint(0)[1] ||
                                this.getPoint(0)[0] >= th.getPoint(1)[0] ||
                                this.getPoint(1)[0] <= th.getPoint(0)[0]
                ){
                    return false;
                }
                break;
            case 2:
                if (
                        this.getPoint(0)[0] >= th.getPoint(1)[0] ||
                                this.getPoint(2)[1] <= th.getPoint(0)[1] ||
                                this.getPoint(0)[1] <= th.getPoint(2)[1]
                ){
                    return false;
                }
                break;
            case 3:
                if (
                        this.getPoint(1)[0] <= th.getPoint(0)[0] ||
                                this.getPoint(2)[1] <= th.getPoint(0)[1] ||
                                this.getPoint(0)[1] <= th.getPoint(2)[1]
                ){
                    return false;
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.getDirect());
        }
        return true;*/

        /**
         *  判断本机行进方向上的两个端点是否在 目标范围内，是返回true，否返回false
         */
        switch (this.getDirect()) {
            case 0:
                /*if (
                        (this.getPoint(0)[0] > th.getPoint(2)[0] && this.getPoint(0)[0] < th.getPoint(3)[0]) ||
                                (this.getPoint(1)[0] > th.getPoint(2)[0] && this.getPoint(1)[0] < th.getPoint(1)[0]) &&
                                        (this.getPoint(0)[1] > th.getPoint(0)[1] && this.getPoint(0)[1] < th.getPoint(2)[1])
                ) {
                    System.out.println(Thread.currentThread().getName() + "向上("+this.getX()+","+this.getY()+")，与其他("+th.getX()+","+th.getY()+")接触！");
                    return true;
                }
                break;*/
                return (this.getPoint(0)[0] > th.getPoint(2)[0] && this.getPoint(0)[0] < th.getPoint(3)[0]) ||
                        (this.getPoint(1)[0] > th.getPoint(2)[0] && this.getPoint(1)[0] < th.getPoint(3)[0]) &&
                                (this.getPoint(0)[1] > th.getPoint(0)[1] && this.getPoint(0)[1] < th.getPoint(2)[1]);
            case 1:
                return (this.getPoint(2)[0] > th.getPoint(0)[0] && this.getPoint(2)[0] < th.getPoint(1)[0]) ||
                        (this.getPoint(3)[0] > th.getPoint(0)[0] && this.getPoint(3)[0] < th.getPoint(1)[0]) &&
                                (this.getPoint(2)[1] > th.getPoint(0)[1] && this.getPoint(2)[1] < th.getPoint(2)[1]);

            case 2:
                return (this.getPoint(0)[1] > th.getPoint(1)[1] && this.getPoint(0)[1] < th.getPoint(3)[1]) ||
                        (this.getPoint(2)[1] > th.getPoint(1)[1] && this.getPoint(2)[1] < th.getPoint(3)[1]) &&
                                (this.getPoint(0)[0] >= th.getPoint(0)[0] && this.getPoint(0)[0] <= th.getPoint(1)[0]);

            case 3:
                return (this.getPoint(1)[1] > th.getPoint(0)[1] && this.getPoint(1)[1] < th.getPoint(2)[1]) ||
                        (this.getPoint(3)[1] > th.getPoint(0)[1] && this.getPoint(3)[1] < th.getPoint(2)[1]) &&
                                (this.getPoint(1)[0] > th.getPoint(0)[0] && this.getPoint(1)[0] < th.getPoint(1)[0]);

            default:
                throw new IllegalStateException("Unexpected value: " + this.getDirect());
        }

    }

/*    public boolean isTouched() {
        boolean isTouch = false;
        //System.out.println("敌方坦克数量："+ enemies.size());
        for (TankEnemy enemy : enemies) {
            if (!enemy.equals(this) && isTouch(enemy)) {
                isTouch = true;
                break;
            }
        }
//        System.out.println("己方坦克坐标:("+ tHero.getX() +","+tHero.getY()+")");
        if (!tHero.equals(this) && !isTouch && !isTouch(tHero)) {
            isTouch = false;
        }
        return isTouch;
    }*/

    public boolean isTouched() {
        //System.out.println("敌方坦克数量："+ enemies.size());
        for (Tank_bak20230509 tank : tank) {
            if (!tank.equals(this) && isTouch(tank)) {

                return true;
            }
        }
        return false;
    }
}
