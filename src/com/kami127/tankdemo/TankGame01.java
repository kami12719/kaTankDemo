package com.kami127.tankdemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author KAMI
 * @version 1.0
 * @Date 2021/4/2 22:10
 */
public class TankGame01 extends JFrame {//JFrame对应窗口，理解成是一个画框

    //定义MyPanel（定义一个面板，放到画框内）
    MyPanel mp;

    public static void main(String[] args) {
        new StartPanel();
//        new TankGame01();
    }

    public TankGame01() {
        //设置窗体标题
        this.setTitle("坦克大战实验项目——kami");
        mp = new MyPanel();
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.setBackground(Color.black);
//        this.pack();
        this.setSize(1315, 788);
        this.setLocationRelativeTo(null);//居中
//        this.setBounds(2,30,1000,750);
//        this.setLayout(new BorderLayout());

        /**
         * 窗口JFrame对象可以监听键盘事件，即可以监听到面板发生的键盘事件
         */
        this.addKeyListener(mp);
        /**
         * 关闭窗口结束进程
         */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /**
         * 允许JVM画图
         */
        this.setVisible(true);
        /**
         * 在JGFrame中增加相应关闭窗口的处理
         */
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!MyPanel.isGameOver()) {
                    System.out.println("开始保存数据……");
                    Recorder.storeRecord();
                    System.out.println("数据已保存，即将退出!");
                }

                System.exit(0);
            }
        });
    }
}
