package com.example.elfenlied.snakesandladders;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ElfenLied on 3/14/2017.
 */
public class TouchButton {
    int startX;
    int startY;
    int width;
    int height;
    Paint paint;

    public TouchButton(int startx, int starty, int height, int width, Paint buttonPaint){
        this.startX = startx;
        this.startY = starty;
        this.width = width;
        this.height = height;
        this.paint = buttonPaint;
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

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }
}
