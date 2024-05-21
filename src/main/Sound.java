package main;

import javax.sound.sampled.*;
import java.net.URL;

public class Sound {
    Clip musicClip;
    URL[] url = new URL[10];

    public Sound() {
        url[0] = getClass().getResource("/bgm.wav");
        url[1] = getClass().getResource("/deleteline.wav");
        url[2] = getClass().getResource("/gameover.wav");
        url[3] = getClass().getResource("/rotation.wav");
        url[4] = getClass().getResource("/touchfloor.wav");
    }

    public void play(int i, boolean music) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(url[i]);
            Clip clip = AudioSystem.getClip();

            if (music) {
                musicClip = clip;
            }

            clip.open(ais);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            ais.close();
            clip.start();

        } catch (Exception _) {
        }
    }

    public void loop() {
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        musicClip.stop();
        musicClip.close();
    }
}
