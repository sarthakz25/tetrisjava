package main;

import mino.*;

import java.awt.*;
import java.util.Random;

public class PlayManager {
    //    main play area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    //    mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;

    //    miscellaneous
    public static int dropInterval = 60; // drop in every 60 frames

    public PlayManager() {
//        main play area frame
        left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

//        set starting mino
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
    }

    private Mino pickMino() {
//        pick a random mino
        int i = new Random().nextInt(7);

        return switch (i) {
            case 0 -> new Mino_L1();
            case 1 -> new Mino_L2();
            case 2 -> new Mino_Square();
            case 3 -> new Mino_Bar();
            case 4 -> new Mino_T();
            case 5 -> new Mino_Z1();
            case 6 -> new Mino_Z2();
            default -> null;
        };
    }

    public void update() {
        currentMino.update();
    }

    public void draw(Graphics2D g2) {
//        draw play area frame
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

//        draw next mino frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Courier New", Font.BOLD, 25));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 70, y + 50);

//        draw current mino
        if (currentMino != null) {
            currentMino.draw(g2);
        }

//        draw pause
        g2.setColor(Color.gray);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (KeyHandler.pausePressed) {
            x = 160;
            y = top_y + 320;
            g2.drawString("PAUSED (Press SPACE to continue)", x, y);
        }
    }
}
