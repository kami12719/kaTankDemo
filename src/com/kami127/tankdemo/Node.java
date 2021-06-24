package com.kami127.tankdemo;

/**
 * @author KAMI
 * @version 1.0
 * @Date 2021/5/19 10:56
 **/
public class Node {
    private int x;
    private int y;
    private int direct;
    private String name;

    public Node(String name,int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirect() {
        return direct;
    }

    public String getName() {
        return name;
    }

}
