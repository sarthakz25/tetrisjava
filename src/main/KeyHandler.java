package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public static boolean upKeyPressed, downKeyPressed, leftKeyPressed, rightKeyPressed, pausePressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upKeyPressed = true;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downKeyPressed = true;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftKeyPressed = true;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightKeyPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            if (pausePressed) {
                pausePressed = false;
                GamePanel.music.play(0, true);
                GamePanel.music.loop();
            } else {
                pausePressed = true;
                GamePanel.music.stop();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
