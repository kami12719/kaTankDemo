package com.kami127.tankdemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author KAMI
 * @version 1.0
 * @Date 2021/4/1 15:39
 * 游戏绘图区
 **/
public class MyPanel extends JPanel implements KeyListener, Runnable {
    /**
     * 定义我的坦克
     */
    private TankHero hero;
    /**
     * 定义敌方坦克，放入Vector
     */
    private Vector<TankEnemy> enemies = new Vector<>();
    private int enemyNum = 3;
    private long lastShotTime = 0;

    /**
     * 定义所有坦克，放入Vector
     */
    private Vector<Tank> tanks = new Vector<>();

    //定义爆炸，放入到Vector
    private Vector<Bomb> bombs = new Vector<>();
    private Image boomImage;

    //游戏结束状态,true被击毁，false主动关闭
    private static boolean gameOver;

    public MyPanel() {
        switch (StartPanel.getInit()) {
            case 0:
                //初始化自己的坦克
                hero = new TankHero(150, 480, 0);
                tanks.add(hero);
                //初始化敌方的坦克
                for (int i = 0; i < enemyNum; i++) {
                    TankEnemy tankEnemy = new TankEnemy((i + 1) * 100, 100,
                            1, hero);
                    tankEnemy.setSpeed(1);
                    tankEnemy.setName("Ey0" + i);
                    //加入
                    enemies.add(tankEnemy);
                    tanks.add(tankEnemy);
                    Thread t1 = new Thread(tankEnemy);
                    t1.start();
                    t1.setName(tankEnemy.getName());
                }
                break;
            case 1:
                Vector<Node> nodes = Recorder.readRecord();
//                enemyNum = nodes.size() - 1;
                for (Node node : nodes) {
                    if ("He".equals(node.getName())) {
                        //需要首先完成hero的初始化，enemies的初始化依赖，需要先将hero放入tanks
                        hero = new TankHero(node.getX(), node.getY(), node.getDirect());
                        tanks.add(hero);
                    } else {
                        TankEnemy tankEnemy = new TankEnemy(node.getX(), node.getY(), node.getDirect(), hero);
                        tankEnemy.setSpeed(1);
                        tankEnemy.setName(node.getName());
                        //加入
                        enemies.add(tankEnemy);
                        tanks.add(tankEnemy);
                        Thread t1 = new Thread(tankEnemy);
                        t1.start();
                        t1.setName(node.getName());
                    }
                }
                enemyNum = enemies.size();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + StartPanel.getInit());
        }

        //己方坦克初始速度
        hero.setSpeed(5);
        hero.setName("He");

        Tank.setTanks(tanks);
        Recorder.setTanks(tanks);
        //加载爆炸效果
        boomImage = Toolkit.getDefaultToolkit().createImage(
                MyPanel.class.getResource(".\\pic\\boomEffect.gif"));

        //播放OP
        //MusicPlayer mp = new MusicPlayer("E:\\ideaProjects\\tankDemo\\src\\com\\kami127\\tankdemo\\bgm\\battleCity.mp3", 20);
        MusicPlayer mp = new MusicPlayer("bgm\\battleCity.mp3", 20);
        Thread m1 = new Thread(mp);
        m1.start();

    }

    /**
     * 1、MyPanel对象就是一个画板
     * 2、Graphics g 理解成一支画笔
     * 3、Graphics提供了很多绘图的方法
     * 4、paint方法在以下情况下会呗调用：
     * 1、当组件第一次在屏幕显示的时候，会调用paint()方法来绘制组件
     * 2、窗口大小发生变化
     * 3、repaint函数被调用
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        //调用父类方法，完成初始化
        super.paint(g);
        //填充矩形，默认黑色
        g.setColor(Color.black);
        g.fillRect(0, 0, 1000, 750);
        //画出己方坦克——封装方法
        drawTank(hero.getX(), hero.getY(), g,
                hero.getDirect(), 0, hero.getName());
        //画出己方炮弹
        if (!hero.getShots().isEmpty()) {
            for (int i = 0; i < hero.getShots().size(); i++) {
                Shot shot = hero.getShots().get(i);
                //if (shot != null && shot.isLive()) {
                if (shot.isLive()) {
                    drawBullet(shot.getX(), shot.getY(), g, 0);
                }
            }
        }
        //画出敌方坦克
        for (int i = 0; i < enemyNum; i++) {
            TankEnemy te = enemies.elementAt(i);
            //判断敌方坦克状态
            if (te.isLive()) {
                drawTank(te.getX(), te.getY(), g, te.getDirect(), 1, te.getName());
            }
            if (!te.getShots().isEmpty()) {
                //画出tankEnemy所有炮弹
                for (int i1 = 0; i1 < te.getShots().size(); i1++) {
                    //取出炮弹
                    Shot shot = te.getShots().get(i1);
                    //绘制isLive == true  or  false
                    if (shot.isLive()) {
                        g.draw3DRect(shot.getX(), shot.getY(), 1, 1, false);
                    } else {
                        //从Vector移除
                        te.getShots().remove(shot);
                    }
                }
            }
        }
        //如果bombs集合中有对象就画出爆炸
        for (int i = 0; i < bombs.size(); i++) {
            //取出爆炸
            Bomb b = bombs.get(i);
            if (b.isLive()) {
                g.drawImage(boomImage, b.getX() - 30, b.getY() - 30, 60, 60, this);
                //System.out.println("绘出爆炸！" + (i + 1));
            } else {
                bombs.remove(b);
            }
        }
        showInfo(g);
    }

    /**
     * 画出坦克
     *
     * @param x      坦克中心横坐标点
     * @param y      坦克中心纵坐标点
     * @param g      画笔
     * @param direct 方向0上，1下，2左，3右
     * @param type   坦克类型0己方，1敌方
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type, String name) {
//        System.out.println("坦克"+ name + "方向"+ direct+"坐标("+x+","+y+")");
        //不同阵营坦克不同颜色
        switch (type) {
            //己方坦克，青色
            case 0 -> g.setColor(Color.cyan);
            //敌方坦克
            case 1 -> g.setColor(Color.yellow);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
        g.drawString(name, x, y);
        /**
         * 绘制3D高亮矩形，坦克履带、机身、舱盖、炮筒
         */
        switch (direct) {
            case 0 -> {
                g.fill3DRect(x - 20, y - 30, 10, 60, false);
                g.fill3DRect(x - 10, y - 20, 20, 40, false);
                g.fill3DRect(x + 10, y - 30, 10, 60, false);
                g.fillOval(x - 10, y - 10, 20, 20);
                g.drawLine(x, y, x, y - 30);
            }
            case 1 -> {
                g.fill3DRect(x - 20, y - 30, 10, 60, false);
                g.fill3DRect(x - 10, y - 20, 20, 40, false);
                g.fill3DRect(x + 10, y - 30, 10, 60, false);
                g.fillOval(x - 10, y - 10, 20, 20);
                g.drawLine(x, y, x, y + 30);
            }
            case 2 -> {
                g.fill3DRect(x - 30, y - 20, 60, 10, false);
                g.fill3DRect(x - 20, y - 10, 40, 20, false);
                g.fill3DRect(x - 30, y + 10, 60, 10, false);
                g.fillOval(x - 10, y - 10, 20, 20);
                g.drawLine(x, y, x - 30, y);
            }
            case 3 -> {
                g.fill3DRect(x - 30, y - 20, 60, 10, false);
                g.fill3DRect(x - 20, y - 10, 40, 20, false);
                g.fill3DRect(x - 30, y + 10, 60, 10, false);
                g.fillOval(x - 10, y - 10, 20, 20);
                g.drawLine(x, y, x + 30, y);
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    /**
     * 画出炮弹
     */
    public void drawBullet(int x, int y, Graphics g, int type) {
        //不同阵营坦克不同颜色
        //己方坦克  青色
        //敌方坦克
        switch (type) {
            case 0 -> g.setColor(Color.cyan);
            case 1 -> g.setColor(Color.yellow);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
        //g.fill3DRect(x,y,1,1,false);
        g.draw3DRect(x, y, 1, 1, false);
    }

    /**
     * 炮弹击中坦克
     */
    public void tankHitted(Tank shotTank, Tank hittedTank) {
        for (int i = 0; i < shotTank.getShots().size(); i++) {
            Shot shot = shotTank.getShots().get(i);
            //判断s击中坦克
            if ((shot.getX() > hittedTank.getPoint(0)[0])
                    && (shot.getX() < hittedTank.getPoint(1)[0])
                    && (shot.getY() > hittedTank.getPoint(0)[1])
                    && (shot.getY() < hittedTank.getPoint(2)[1])) {
                hittedTank.setLive(false);
                shot.setLive(false);
                //增加击毁数
                if (shotTank.equals(hero)) {
                    Recorder.hittedNumAdd();
                } else {
                    JOptionPane.showMessageDialog(null, "您已被敌方击毁，游戏结束！");
                    gameOver = true;
                }
            }

            //销毁射击
            if (!shot.isLive()) {
                if (shotTank.getShots().remove(shot)) {
                    System.out.println("炮弹 " + i + " 击中销毁了！");
                }
            }
            //创建Bomb对象，加入到bombs集合
            if (!shot.isLive() && shot.isIn()) {
                Bomb bomb = new Bomb(shot.getX(), shot.getY());
//                Bomb bomb = new Bomb(hittedTank.getX(), hittedTank.getY());
                bombs.add(bomb);
                System.out.println("加入爆炸！");
                //销毁敌方坦克，并重新获取敌方坦克数量
                enemies.remove(hittedTank);
                tanks.remove(hittedTank);
                enemyNum = enemies.size();
                new Thread(bomb).start();
//                System.out.println("爆炸线程：" + new Thread(bomb).isAlive() + "剩余敌方坦克："+ enemyNum);
            }
        }
    }

    //遍历所有射击
    public void traversalShots() {
        if (!enemies.isEmpty()) {
            for (int i = 0; i < enemies.size(); i++) {
                //后判断hero是否击中enemies，防止enemies.remove后空指针
                tankHitted(enemies.get(i), hero);
                tankHitted(hero, enemies.get(i));
            }
        }
    }

    public void showInfo(Graphics g) {
        //画出玩家的总成绩
        g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("累计击毁敌方坦克：", 1020, 30);
        drawTank(1060, 80, g, 0, 1, "sE");
        g.setColor(Color.black);
        g.drawString(Recorder.getHittedNum() + "", 1120, 80);

    }

    public static boolean isGameOver() {
        return gameOver;
    }

    /**
     * 有字符输出时，该方法会触发
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * 某个键按下时，会触发
     */
    @Override
    public void keyPressed(KeyEvent e) {

        //根据用户按下的不同键，来处理移动
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.runUp();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.runLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.runDown();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.runRight();
        }
//        System.out.println("己方坦克位置：("+ hero.getX()+","+hero.getY()+")★");
        //输入J，启动shot线程
        if (e.getKeyCode() == KeyEvent.VK_J && (System.currentTimeMillis() - lastShotTime > hero.getMaxShotSpeed())) {
            hero.tankShot();
            lastShotTime = System.currentTimeMillis();
        }
        //让面板重绘
        this.repaint();
    }

    //当某个键释放，会触发
    @Override
    public void keyReleased(KeyEvent e) {

    }

    //每隔100毫秒重绘区域
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //遍历所有射击
            traversalShots();
            this.repaint();
        }

    }
}
