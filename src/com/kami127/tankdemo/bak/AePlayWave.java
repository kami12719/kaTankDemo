package com.kami127.tankdemo.bak;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * @author KAMI
 * @version 1.0
 * @Date 2021/5/27 21:48
 * 播放wav等java自带的音乐格式
 */
public class AePlayWave extends Thread{
    private String filename;

    public AePlayWave(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        File soundFile = new File(filename);

        AudioInputStream audioInputStream = null;

        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        auline.start();
        int nBytesRead = 0;
        //这是缓冲
        byte[] abData = new byte[512];

        while (nBytesRead != -1) {
            try {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0) {
                    auline.write(abData, 0, nBytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            } finally {
                auline.drain();
                auline.close();
            }
        }

    }
}
