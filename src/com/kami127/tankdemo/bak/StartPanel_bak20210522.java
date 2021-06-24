package com.kami127.tankdemo.bak;

import com.kami127.tankdemo.Node;
import com.kami127.tankdemo.Recorder;
import com.kami127.tankdemo.TankGame01;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

/**
 * @author KAMI
 * @version 1.0
 * @Date 2021/5/20 15:39
 **/
public class StartPanel_bak20210522 extends JFrame implements MouseListener {
    //设置窗体的基本属性  大小
    /**
     * 1.1、设置窗体基本属性大小 居中 边框隐藏 默认关闭按钮 logo图标
     * 1.2、创建背景面板MainPanel，实现背景图片功能
     * <p>
     * 2.图片按钮功能
     */
    //2.1创建开始按钮 继续游戏 离开按钮 组件
    JLabel start, restart, exit;

    JPanel MainPanel;

    //初始化级别：0重新开始，1继续游戏
    private static int init;

    /**
     * 定义一个存放Node对象的Vector，用于存放需要恢复的坦克信息
     */
    private Vector<Node> nodes = new Vector<>();

    public static int getInit() {
        return init;
    }

    public StartPanel_bak20210522() {//无参构造，创建对象。并在main函数中调用
        //2.2
        start = new JLabel("开始游戏");
//        start = new JLabel(new ImageIcon("Image/hh1.png"));//ImageIcon:图标
        start.setBounds(35, 35, 150, 40);
        start.setEnabled(false);//false按钮为灰色
        start.addMouseListener(this);
        this.add(start);

        restart = new JLabel("继续游戏");
        restart.setBounds(35, 80, 150, 40);
        restart.setEnabled(false);
        restart.addMouseListener(this);
        this.add(restart);

        exit = new JLabel("退出游戏");
        exit.setBounds(35, 125, 150, 40);
        exit.setEnabled(false);
        exit.addMouseListener(this);
        this.add(exit);

        //1.实现背景图片及窗体属性
        /*MainPanel panel = new MainPanel();
        this.add(panel);*/

        //设置窗体标题
        this.setTitle("坦克大战开始菜单——kami");
        //设置窗体基本属性大小 居中 边框隐藏 默认关闭按钮 logo图标
        this.setSize(600, 300);//大小
        this.setLocationRelativeTo(null);//居中
//        this.setUndecorated(true);//边框隐藏
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认关闭
//        this.setIconImage(new ImageIcon("Image/115.png").getImage());//logo
        this.setVisible(true);
    }

/*    //2、创建背景面板MainPanel，实现背景图片功能
    class MainPanel extends JPanel {//创建的MainPanel类，在MainFrame中调用
        *//*Image background;

        public MainPanel() {
            try {
                background = ImageIO.read(new File("./src/com/kami127/tankdemo/pic/bgp.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*//*

        @Override
        public void paint(Graphics g) {
            super.paint(g);
//            g.drawImage(background, 0, 0, 1000, 719, null);
            g.setColor(Color.GRAY);
            g.drawRect(30,30,300,150);
        }
    }*/


    //以下五个方法均为添加 implements MouseListener 后，快捷出来的
    @Override
    public void mouseClicked(MouseEvent e) {
        //鼠标点击
        if (e.getSource().equals(start)) {
            init = 0;
            //跳转到下一界面
            new TankGame01();
            //关闭当前界面
            dispose();
        } else if (e.getSource().equals(restart)) {
//            JOptionPane.showMessageDialog(null, "有疑问请联系开发者：Huey");
            init = 1;
            nodes = Recorder.readRecord();
            dispose();
        } else if (e.getSource().equals(exit)) {
            dispose();
        }

    }


    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }


    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }


    @Override
    public void mouseEntered(MouseEvent e) {
        // 鼠标移入
        if (e.getSource().equals(start)) {//e指一个事件。e.getSource()获取事件
            //如果鼠标移入到（start）组件（图片按钮）
            start.setEnabled(true);
        } else if (e.getSource().equals(restart)) {
            restart.setEnabled(true);
        } else if (e.getSource().equals(exit)) {
            exit.setEnabled(true);
        }
    }


    @Override
    public void mouseExited(MouseEvent e) {
        //鼠标移出
        if (e.getSource().equals(start)) {
            start.setEnabled(false);
        } else if (e.getSource().equals(restart)) {
            restart.setEnabled(false);
        } else if (e.getSource().equals(exit)) {
            exit.setEnabled(false);
        }
    }
}
