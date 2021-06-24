package com.kami127.tankdemo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * @author KAMI
 * @version 1.0
 * @Date 2021/5/29 17:00
 */
public class MusicPlayer implements Runnable {
    private static Player player = null;

    /**
     * 文件路径
     */
    private String bgmFile;

    /**
     * 单位s
     */
    private int time;

    public MusicPlayer(String bgmFile, int time) {
        this.bgmFile = bgmFile;
        this.time = time;
    }

    @Override
    public void run() {
        File bgm = new File(bgmFile);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(bgm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedInputStream stream = new BufferedInputStream(fis);
        try {
            player = new Player(stream);
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (player != null) {
                player.close();
            }
        }
    }


    /**
     * 播放 20 秒并结束播放
     */
    /*public static void play(int time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File bgm = new File(bgmfile);
                    FileInputStream fis = new FileInputStream(bgm);
                    BufferedInputStream stream = new BufferedInputStream(fis);
                    player = new Player(stream);
                    player.play();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }).start();
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            player.close();
        }
    }*/
}
