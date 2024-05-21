package mino;

import main.GamePanel;
import main.KeyHandler;
import main.PlayManager;

import java.awt.*;

public class Mino {
    public Block[] b = new Block[4];
    public Block[] tempB = new Block[4];
    int autoDropCounter = 0;
    //    4 directions (1,2,3,4)
    public int direction = 1;
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactivating;
    int deactivateCounter = 0;

    public void create(Color c) {
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB[0] = new Block(c);
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);
    }

    public void setXY(int x, int y) {
    }

    public void updateXY(int direction) {
        checkRotationCollision();

        if (!leftCollision && !rightCollision && !bottomCollision) {
            this.direction = direction;
            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;
        }
    }

    public void getDirection1() {
    }

    public void getDirection2() {
    }

    public void getDirection3() {
    }

    public void getDirection4() {
    }

    public void checkMovementCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

//        to check frame collision
//        left wall
        for (Block value : b) {
            if (value.x == PlayManager.left_x) {
                leftCollision = true;
                break;
            }
        }
//        right wall
        for (Block value : b) {
            if (value.x + Block.SIZE == PlayManager.right_x) {
                rightCollision = true;
                break;
            }
        }
//        bottom wall
        for (Block value : b) {
            if (value.y + Block.SIZE == PlayManager.bottom_y) {
                bottomCollision = true;
                break;
            }
        }
    }

    public void checkRotationCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

//        check frame collision
//        left wall
        for (Block value : tempB) {
            if (value.x < PlayManager.left_x) {
                leftCollision = true;
                break;
            }
        }
//        right wall
        for (Block value : tempB) {
            if (value.x + Block.SIZE > PlayManager.right_x) {
                rightCollision = true;
                break;
            }
        }
//        bottom wall
        for (Block value : tempB) {
            if (value.y + Block.SIZE > PlayManager.bottom_y) {
                bottomCollision = true;
                break;
            }
        }
    }

    private void checkStaticBlockCollision() {
        for (int i = 0; i < PlayManager.staticBlocks.size(); i++) {
            int targetX = PlayManager.staticBlocks.get(i).x;
            int targetY = PlayManager.staticBlocks.get(i).y;

//            check down
            for (Block block : b) {
                if (block.x == targetX && block.y + Block.SIZE == targetY) {
                    bottomCollision = true;
                    break;
                }
            }
//            check left
            for (Block block : b) {
                if (block.x - Block.SIZE == targetX && block.y == targetY) {
                    leftCollision = true;
                    break;
                }
            }
//            check right
            for (Block block : b) {
                if (block.x + Block.SIZE == targetX && block.y == targetY) {
                    rightCollision = true;
                    break;
                }
            }
        }
    }

    public void update() {
        if (deactivating) {
            deactivating();
        }

//        move mino
        if (KeyHandler.upKeyPressed) {
            switch (direction) {
                case 1:
                    getDirection2();
                    break;
                case 2:
                    getDirection3();
                    break;
                case 3:
                    getDirection4();
                    break;
                case 4:
                    getDirection1();
                    break;
            }
            KeyHandler.upKeyPressed = false;
            GamePanel.se.play(3, false);
        }

        checkMovementCollision();

        if (KeyHandler.downKeyPressed) {
//            allow mino to move down if not hitting bottom
            if (!bottomCollision) {
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;

//            when moved down reset counter
                autoDropCounter = 0;
            }

            KeyHandler.downKeyPressed = false;
        }
        if (KeyHandler.leftKeyPressed) {
            if (!leftCollision) {
                b[0].x -= Block.SIZE;
                b[1].x -= Block.SIZE;
                b[2].x -= Block.SIZE;
                b[3].x -= Block.SIZE;

                KeyHandler.leftKeyPressed = false;
            }
        }
        if (KeyHandler.rightKeyPressed) {
            if (!rightCollision) {
                b[0].x += Block.SIZE;
                b[1].x += Block.SIZE;
                b[2].x += Block.SIZE;
                b[3].x += Block.SIZE;

                KeyHandler.rightKeyPressed = false;
            }
        }

        if (bottomCollision) {
            if (!deactivating) {
                GamePanel.se.play(4, false);
            }

            deactivating = true;
        } else {
//        increments every frame
            autoDropCounter++;
            if (autoDropCounter == PlayManager.dropInterval) {
//            mino drops
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;
                autoDropCounter = 0;
            }
        }
    }

    private void deactivating() {
        deactivateCounter++;

//        wait 45 frames until deactivate
        if (deactivateCounter == 45) {
            deactivateCounter = 0;
//            check if it's still at bottom
            checkMovementCollision();

//            deactivate mino
            if (bottomCollision) {
                active = false;
            }
        }
    }

    public void draw(Graphics2D g2) {
        int margin = 2;

        g2.setColor(b[0].c);
        g2.fillRect(b[0].x + margin, b[0].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g2.fillRect(b[1].x + margin, b[1].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g2.fillRect(b[2].x + margin, b[2].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g2.fillRect(b[3].x + margin, b[3].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
    }
}
