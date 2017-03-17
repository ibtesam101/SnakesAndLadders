package com.example.elfenlied.snakesandladders;

/**
 * Created by ElfenLied on 3/17/2017.
 */
public class pieceController {
    float x;
    float y;

    int currentPos;
    int currentRow;

    float xInit;
    float yInit;

    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }
    public int getPos(){
        return currentPos;
    }
    public int getRow(){
        return currentRow;
    }

    public void setX(float x){
        this.x=x;
    }

    public void setY(float y){
        this.y=y;
    }
    public void addPos(){
        currentPos++;
    }

    public void subPos(){
        currentPos--;
    }

    public void setPos(int p){
        currentPos = p;
    }

    public void addRow(){
        currentRow++;
    }

    public void subRow(){
        currentRow--;
    }
    pieceController(float x, float y){
        this.x = x;
        this.y = y;
        xInit = x;
        yInit = y;
        currentPos = 1;
        currentRow = 1;
    }

    public void animate(float tarX, float tarY){
        float xMove = (tarX-xInit)/100;
        float yMove = (tarY-yInit)/100;
        if(x!=tarX && y!=tarY) {
            x+=xMove;
            y+=yMove;
        }
        else{
            xInit = tarX;
            yInit = tarY;
        }
    }

}
