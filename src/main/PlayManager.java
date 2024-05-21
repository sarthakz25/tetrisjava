package main;

import mino.*;

import java.awt.*;
import java.util.ArrayList;
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
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>(); // for inactive minos

    //    misc
    public static int dropInterval = 60; // drop in every 60 frames
    boolean gameOver;

    //    effect
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    //    score
    int level = 1;
    int lines;
    int score;

    public PlayManager() {
//        main play area frame
        left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;

//        set starting mino
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);

        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
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
//        check if current mino is active
        if (!currentMino.active) {
//            if not, put into staticBlocks
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

//            check if game over
            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
//                current mino immediately collided i.e. x y same as next mino
                gameOver = true;
                GamePanel.music.stop();
                GamePanel.se.play(2, false);
            }

            currentMino.deactivating = false;

//            replace current with next
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

//            when minos are inactive
            checkDelete();
        } else {
            currentMino.update();
        }
    }

    //    check if lines can be deleted
    private void checkDelete() {
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        while (x < right_x && y < bottom_y) {
            for (Block staticBlock : staticBlocks) {
                if (staticBlock.x == x && staticBlock.y == y) {
//                    increase count
                    blockCount++;
                }
            }

            x += Block.SIZE;

            if (x == right_x) {
                if (blockCount == 12) {
                    effectCounterOn = true;
                    effectY.add(y);

//                    line filled with blocks, delete
                    for (int i = staticBlocks.size() - 1; i >= 0; i--) {
//                        remove all blocks in current y line
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    lineCount++;
                    lines++;

//                    increase drop speed acc to line score
                    if (lines % 10 == 0 && dropInterval > 1) {
                        level++;

                        if (dropInterval > 10) {
                            dropInterval -= 10;
                        } else {
                            dropInterval -= 1;
                        }
                    }

//                    line deleted so slide down above blocks
                    for (Block staticBlock : staticBlocks) {
//                        move block above current y down by block size
                        if (staticBlock.y < y) {
                            staticBlock.y += Block.SIZE;
                        }
                    }
                }

                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

//        add score
        if (lineCount > 0) {
            GamePanel.se.play(1, false);
            int singleLineScore = 10 * level;
            score += singleLineScore * lineCount;
        }
    }

    public void draw(Graphics2D g2) {
//        play area frame
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

//        next mino frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Courier New", Font.BOLD, 25));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 70, y + 50);

//        score frame
        g2.drawRect(x, top_y + 150, 250, 200);
        x += 35;
        y = top_y + 200;
        g2.drawString("Level: " + level, x, y);
        y += 50;
        g2.drawString("Lines: " + lines, x, y);
        y += 50;
        g2.drawString("Score: " + score, x, y);

//        current mino
        if (currentMino != null) {
            currentMino.draw(g2);
        }

//        next mino
        nextMino.draw(g2);

//        staticBlocks
        for (Block staticBlock : staticBlocks) {
            staticBlock.draw(g2);
        }

//        effect
        if (effectCounterOn) {
            effectCounter++;

            g2.setColor(Color.red);
            for (Integer integer : effectY) {
                g2.fillRect(left_x, integer, WIDTH, Block.SIZE);
            }

            if (effectCounter == 10) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

//        pause, game over
        g2.setColor(Color.pink);
        g2.setFont(new Font("Lucida Console", Font.ITALIC, 50));
        if (gameOver) {
            x = 80;
            y = top_y + 340;
            g2.drawString("GAME OVER", x, y);
        } else if (KeyHandler.pausePressed) {
            x = 130;
            y = top_y + 340;
            g2.drawString("PAUSED", x, y);
        }

//        game title
        x = 130;
        y = top_y + 260;
        g2.setColor(Color.white);
        g2.drawString("Tetris", x, y);
    }
}
