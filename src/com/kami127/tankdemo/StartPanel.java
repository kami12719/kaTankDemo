package com.kami127.tankdemo;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author KAMI
 * @version 1.1
 * @Date 2021/5/20 15:39
 **/
public class StartPanel extends JFrame implements MouseListener {
    //设置窗体的基本属性  大小
    /**
     * 1.1、设置窗体基本属性大小 居中 边框隐藏 默认关闭按钮 logo图标
     * 1.2、创建背景面板MainPanel，实现背景图片功能
     * <p>
     * 2.图片按钮功能
     */
    /**
     *     2.1创建开始按钮 继续游戏 离开按钮 组件
     */
    JLabel start, restart, exit;

    /**
     *     初始化级别：0重新开始，1继续游戏
     */
    private static int init;

    public static int getInit() {
        return init;
    }

    public StartPanel() {//无参构造，创建对象。并在main函数中调用
        start = new JLabel("开始游戏");
        start.setFont(new java.awt.Font("宋体", 1, 20));
        start.setBounds(35, 35, 100, 50);
        start.setEnabled(false);//false按钮为灰色
        start.addMouseListener(this);
        this.add(start);

        restart = new JLabel("继续游戏");
        restart.setFont(new java.awt.Font("宋体", 1, 20));
        restart.setBounds(35, 90, 100, 50);
        restart.setEnabled(false);
        restart.addMouseListener(this);
        this.add(restart);

        exit = new JLabel("退出游戏");
        exit.setFont(new java.awt.Font("宋体", 1, 20));
        exit.setBounds(35, 145, 100, 50);
        exit.setEnabled(false);
        exit.addMouseListener(this);
        this.add(exit);

        //设置窗体标题
        this.setTitle("坦克大战开始菜单——kami");
        //设置窗体基本属性大小 居中 边框隐藏 默认关闭按钮 logo图标
        this.setSize(500, 300);//大小
        this.setLocationRelativeTo(null);//居中
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认关闭
        //★JPanel的setLayout布局与setBounds方法相冲突,取消JPanel的布局
        this.setLayout(null);
        this.setVisible(true);
    }

    /**
     *     以下五个方法均为添加 implements MouseListener 后，快捷出来的
     */
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
            if (Recorder.recordFileExist()) {
                init = 1;
            }else{
                JOptionPane.showMessageDialog(null,
                        "记录文件不存在，开始新游戏！");
                init = 0;
            }
            new TankGame01();
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
