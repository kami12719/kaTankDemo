package com.kami127.tankdemo;

import java.util.Vector;

/**
 * @author KAMI
 * @version 1.1
 * @Date 2021/4/1 10:31
 **/
public class Tank {
    private int x;  //横坐标
    private int y;  //纵坐标
    private int direct; //方向0上，1下，2左，3右
    private int speed = 2;  //默认速度2
    private boolean isLive = true;
    private String name;

    /**
     * 定义所有坦克
     */
    private static Vector<Tank> tanks = new Vector<>();

    /**
     * 定义一个Shot对象，表示一个射击（线程）
     */
    private Vector<Shot> shots = new Vector<>();

    public Tank(int x, int y, int direct) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 将MyPanel定义的enemies、hero对象传递给Tank的tanks静态变量
     * @param tank
     */
    public static void setTanks(Vector<Tank> tank) {
        Tank.tanks = tank;
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

    public void runUp() {
        //lastDirect记录进入前的方向，如果转向后发生触碰，方向恢复。其余方向相同处理
        int lastDirect = direct;
        this.direct = 0;
        if (!this.isTouched()) {
            if (this.getPoint(0)[1] > 0) {
                this.y -= this.speed;
            } else {
                this.y = (0 + 30);
            }
        }else {
            direct = lastDirect;
        }
    }

    public void runDown() {
        int lastDirect = direct;
        this.direct = 1;
        if (!this.isTouched()) {
            if (this.getPoint(2)[1] < 750) {
                this.y += this.speed;
            } else {
                this.y = (750 - 30);
            }
        }else {
            direct = lastDirect;
        }
    }

    public void runLeft() {
        int lastDirect = direct;
        this.direct = 2;
        if (!this.isTouched()) {
            if (this.getPoint(0)[0] > 0) {
                this.x -= this.speed;
            } else {
                this.x = (0 + 30);
            }
        }else {
            direct = lastDirect;
        }
    }

    public void runRight() {
        int lastDirect = direct;
        this.direct = 3;
        if (!this.isTouched()) {
            if (this.getPoint(1)[0] < 1000) {
                this.x += this.speed;
            } else {
                this.x = (1000 - 30);
            }
        }else {
            direct = lastDirect;
        }
    }

    /**
     * 获取炮口坐标
     *
     * @return x=a[0]   y=a[1]
     */
    public int[] getShotPoint() {
        switch (direct) {
            //向上
            case 0:
                return new int[]{getX(), getPoint(0)[1]};
            //下
            case 1:
                return new int[]{getX(), getPoint(2)[1]};
            //左
            case 2:
                return new int[]{getPoint(0)[0], getY()};
            //右
            case 3:
                return new int[]{getPoint(1)[0], getY()};
            default:
                throw new IllegalStateException("Unexpected value: " + getDirect());
        }
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
        switch (point) {
            case 0:
            return new int[]{a[0],a[1]};
            case 1:
            return new int[]{a[2],a[1]};
            case 2:
            return new int[]{a[0],a[3]};
            case 3:
            return new int[]{a[2],a[3]};
            default:
                throw new IllegalStateException("Unexpected value: " + point);
        }
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
    public boolean isTouch(Tank th) {
        /**
         *  判断本机行进方向上的两个端点是否在 目标范围内，是返回true，否返回false
         */
        switch (this.direct) {
            //多条判断，按照逻辑增加括号，否则会出现乱七八糟的问题！！！
            case 0:
                return ((this.getPoint(0)[0] > th.getPoint(2)[0] && this.getPoint(0)[0] < th.getPoint(3)[0]) ||
                        (this.getPoint(1)[0] > th.getPoint(2)[0] && this.getPoint(1)[0] < th.getPoint(3)[0])) &&
                                (this.getPoint(0)[1] > th.getPoint(0)[1] && this.getPoint(0)[1] < th.getPoint(2)[1]);
            case 1:
                return ((this.getPoint(2)[0] > th.getPoint(0)[0] && this.getPoint(2)[0] < th.getPoint(1)[0]) ||
                        (this.getPoint(3)[0] > th.getPoint(0)[0] && this.getPoint(3)[0] < th.getPoint(1)[0])) &&
                                (this.getPoint(2)[1] > th.getPoint(0)[1] && this.getPoint(2)[1] < th.getPoint(2)[1]);

            case 2:
                return ((this.getPoint(0)[1] > th.getPoint(1)[1] && this.getPoint(0)[1] < th.getPoint(3)[1]) ||
                        (this.getPoint(2)[1] > th.getPoint(1)[1] && this.getPoint(2)[1] < th.getPoint(3)[1])) &&
                                (this.getPoint(0)[0] >= th.getPoint(0)[0] && this.getPoint(0)[0] <= th.getPoint(1)[0]);
            case 3:
                return ((this.getPoint(1)[1] > th.getPoint(0)[1] && this.getPoint(1)[1] < th.getPoint(2)[1]) ||
                        (this.getPoint(3)[1] > th.getPoint(0)[1] && this.getPoint(3)[1] < th.getPoint(2)[1])) &&
                                (this.getPoint(1)[0] > th.getPoint(0)[0] && this.getPoint(1)[0] < th.getPoint(1)[0]);
            default:
                throw new IllegalStateException("Unexpected value: " + this.getDirect());
        }

    }

    /**
     * 遍历本身以外的所有tank，判断是否有碰撞
     * @return true 已接触，false 未接触
     */
    public boolean isTouched() {
        //System.out.println("敌方坦克数量："+ enemies.size());
        for (Tank tank : tanks) {
            if (!this.equals(tank) && isTouch(tank)) {
/*                System.out.println(Thread.currentThread().getName()+"名字："+name+"四点坐标："
                        +"("+this.getPoint(0)[0]+"，"+this.getPoint(0)[1]+")"
                        +"("+this.getPoint(1)[0]+"，"+this.getPoint(1)[1]+")"
                        +"("+this.getPoint(2)[0]+"，"+this.getPoint(2)[1]+")"
                        +"("+this.getPoint(3)[0]+"，"+this.getPoint(3)[1]+")"
                +"对方坐标："
                        +"("+tank.getPoint(0)[0]+"，"+tank.getPoint(0)[1]+")"
                        +"("+tank.getPoint(1)[0]+"，"+tank.getPoint(1)[1]+")"
                        +"("+tank.getPoint(2)[0]+"，"+tank.getPoint(2)[1]+")"
                        +"("+tank.getPoint(3)[0]+"，"+tank.getPoint(3)[1]+")");*/
                return true;
            }
        }
        return false;
    }
}
