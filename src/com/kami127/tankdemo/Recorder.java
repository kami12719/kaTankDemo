package com.kami127.tankdemo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

/**
 * @author KAMI
 * @version 1.0
 * @Date 2021/5/12 10:03
 * 保存相关信息到文件
 **/
public class Recorder {
    /**
     * 记录我方击毁敌人坦克数
     */
    private static int hittedNum = 0;

    //定义IO对象,准备写数据到文件中
//    private static FileWriter fw = null;
    private static BufferedWriter bw = null;
    private final static String recordFile = ".\\myData.txt";
    private static Vector<Tank> tanks = new Vector<>();

    private static BufferedReader br = null;
    //定义一个Node的Vector，用于保存坦克信息
    private static Vector<Node> nodes = new Vector<>();

    public static int getHittedNum() {
        return hittedNum;
    }

    public static void setTanks(Vector<Tank> tanks) {
        Recorder.tanks = tanks;
    }

    /**
     * 每当击毁敌方坦克，则调用增加击毁数
     */
    public static void hittedNumAdd() {
        Recorder.hittedNum++;
    }

    public static void storeRecord() {
        try {
            /*bw = new BufferedWriter(new OutputStreamWriter
                    (new FileOutputStream(recordFile),
                            StandardCharsets.UTF_8));*/
            bw = new BufferedWriter(new FileWriter(recordFile));
            //BufferedWriter仅输出数字会乱码,解决方法就是将int转成string后再写
            bw.write("hittedNum:" + hittedNum);
            bw.newLine();
            for (Tank tank : tanks) {
                bw.append("tankName:" + tank.getName() + ",tankX:" + tank.getX() + ",tankY:" + tank.getY() + ",tankDirect:" + tank.getDirect());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取记录文件，恢复相关信息
     */
    public static Vector<Node> readRecord() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            //读取第一行的击杀数
            hittedNum = Integer.parseInt(br.readLine().split(":")[1]);

            //循环读取，生成坦克基本信息nodes集合
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] tempLine = line.split(",");    //先用“，”分割各项数据
                //获取文件中的各项数据，再用":"分割数据名与数据
                Node node = new Node(tempLine[0].split(":")[1], //名字
                        Integer.parseInt(tempLine[1].split(":")[1]),    //X坐标
                        Integer.parseInt(tempLine[2].split(":")[1]),    //Y坐标
                        Integer.parseInt(tempLine[3].split(":")[1])     //方向
                );
                //放到nodes的Vector
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return nodes;

        }
    }

    public static boolean recordFileExist(){
        return new File(recordFile).exists();
    }
}
