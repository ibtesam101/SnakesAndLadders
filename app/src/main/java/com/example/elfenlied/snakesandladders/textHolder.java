package com.example.elfenlied.snakesandladders;

import android.graphics.Paint;

/**
 * Created by ElfenLied on 3/15/2017.
 */
public class textHolder {
    int startX;
    int startY;
    Paint paint;

    public textHolder(int startx, int starty, Paint paint){
        this.startX = startx;
        this.startY = starty;
        this.paint = paint;
    }

    public void setPaint(int color){
        this.paint.setColor(color);
    }

    public Paint getPaint(){
        return paint;
    }

    public int getX(){
        return startX;
    }

    public int getY(){
        return startY;
    }
}
