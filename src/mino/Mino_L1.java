package mino;

import java.awt.*;

public class Mino_L1 extends Mino {
    public Mino_L1() {
        create(Color.orange);
    }

    @Override
    public void setXY(int x, int y) {
        super.setXY(x, y);
//        0
//        0
//        0 0
        b[0].x = x;
        b[0].y = y;
        b[1].x = b[0].x;
        b[1].y = b[0].y - Block.SIZE;
        b[2].x = b[0].x;
        b[2].y = b[0].y + Block.SIZE;
        b[3].x = b[0].x + Block.SIZE;
        b[3].y = b[0].y + Block.SIZE;
    }

    @Override
    public void getDirection1() {
        super.getDirection1();
//        0
//        0
//        0 0
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x + Block.SIZE;
        tempB[3].y = b[0].y + Block.SIZE;

        updateXY(1);
    }

    @Override
    public void getDirection2() {
        super.getDirection2();
//        0 0 0
//        0
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x + Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x - Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x - Block.SIZE;
        tempB[3].y = b[0].y + Block.SIZE;

        updateXY(2);
    }

    @Override
    public void getDirection3() {
        super.getDirection3();
//        0 0
//          0
//          0
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y + Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y - Block.SIZE;
        tempB[3].x = b[0].x - Block.SIZE;
        tempB[3].y = b[0].y - Block.SIZE;

        updateXY(3);
    }

    @Override
    public void getDirection4() {
        super.getDirection4();
//            0
//        0 0 0
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x - Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x + Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x + Block.SIZE;
        tempB[3].y = b[0].y - Block.SIZE;

        updateXY(4);
    }
}
