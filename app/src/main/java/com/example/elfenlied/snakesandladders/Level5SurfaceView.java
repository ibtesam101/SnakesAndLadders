package com.example.elfenlied.snakesandladders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;

/**
 * Created by ElfenLied on 3/14/2017.
 */
public class Level5SurfaceView extends SurfaceView implements Runnable, View.OnTouchListener{

    SurfaceHolder myHolder;

    Thread myThread = null;

    TouchButton tButton;
    textHolder TextHolder;
    textHolder regText;

    int playerDie;
    int computerDie;

    boolean playerWin = false;
    boolean computerWin = false;
    boolean playerTurn = true;
    int playerExtra = 0;

    boolean enemyTurn = true;
    int enemyExtra = 0;

    float touchX;
    float touchY;

    boolean isRunning = true;

    DisplayMetrics displayMetrics;

    int width;
    int height;

    Bitmap boardBmp;
    int boardWidth;
    int boardHeight;
    int[] snakeHeads= {25, 53, 61, 57, 75, 93, 97};
    int[] snakeTails = {3, 13, 27, 37, 43, 35, 65};

    int[] ladderHeads= {24, 14, 52, 56, 64, 74, 96};
    int[] ladderTails = {2, 8, 12, 34, 38, 70, 78};

    Bitmap playerBmp;
    Bitmap computerBmp;

    pieceController playerCtrl;
    pieceController enemyCtrl;

    public Level5SurfaceView(Context context) {
        super(context);
        this.setOnTouchListener(this);
        myHolder = getHolder();
        width = getResources().getSystem().getDisplayMetrics().widthPixels;

        height = getResources().getSystem().getDisplayMetrics().heightPixels;

        boardBmp = BitmapFactory.decodeResource(getResources(), R.drawable.board);

        boardWidth = width-width/8;

        playerBmp = BitmapFactory.decodeResource(getResources(), R.drawable.playerpiece);
        playerCtrl = new pieceController(0,boardWidth- boardWidth/10);

        computerBmp = BitmapFactory.decodeResource(getResources(), R.drawable.computerpiece);
        enemyCtrl = new pieceController(0, boardWidth- boardWidth/10);

        Paint buttonPaint = new Paint();
        Paint textPaint = new Paint();
        buttonPaint.setColor(0xff424242);
        tButton = new TouchButton(width/5, (height/5)*4, height/9, (width/5)*2, buttonPaint);

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(width/14);
        TextHolder = new textHolder( width - 3*width/4, height - height/4, textPaint);

        buttonPaint.setColor(0xff000000);
        regText = new textHolder(tButton.getX()+100, tButton.getY()+100, buttonPaint);

    }

    public void pause(){
        isRunning = false;
        while (true){
            try {
                myThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }

        myThread = null;
    }

    public void resume(){
        isRunning = true;
        myThread = new Thread(this);
        myThread.start();
    }

    @Override
    public void run() {
        while (isRunning){
            if(!myHolder.getSurface().isValid()){
                continue;
            }

            if(playerCtrl.getPos()==100){
                playerWin=true;
            }

            if(enemyCtrl.getPos()==100){
                computerWin=true;
            }

            Canvas canvas = myHolder.lockCanvas();

            canvas.drawRGB(255, 255, 255);

            canvas.drawBitmap(Bitmap.createScaledBitmap(boardBmp, boardWidth, boardWidth, false), 0, 0, null);

            canvas.drawText("Player", TextHolder.getX(), TextHolder.getY(), TextHolder.getPaint());

            canvas.drawBitmap(Bitmap.createScaledBitmap(playerBmp, boardWidth/10, boardWidth/10, false),
                    TextHolder.getX()-100, TextHolder.getY()-70, null);

            canvas.drawBitmap(Bitmap.createScaledBitmap(playerBmp, boardWidth/10, boardWidth/10, false),
                    playerCtrl.getX(), playerCtrl.getY(), null);

            canvas.drawText("Computer", TextHolder.getX(), TextHolder.getY()+100, TextHolder.getPaint());

            canvas.drawBitmap(Bitmap.createScaledBitmap(computerBmp, boardWidth/10, boardWidth/10, false),
                    TextHolder.getX()-100, TextHolder.getY()+30, null);

            canvas.drawBitmap(Bitmap.createScaledBitmap(computerBmp, boardWidth/10, boardWidth/10, false),
                    enemyCtrl.getX(), enemyCtrl.getY(), null);

            if(!playerWin && !computerWin) {
                canvas.drawText("Player got : " + playerDie, TextHolder.getX(), TextHolder.getY() - 300, TextHolder.getPaint());

                canvas.drawText("Computer got : " + computerDie, TextHolder.getX(), TextHolder.getY() - 200, TextHolder.getPaint());
            }
            else if(playerWin){
                canvas.drawText("YOU WON! :)", TextHolder.getX(), TextHolder.getY() - 300, TextHolder.getPaint());
            }
            else if(computerWin){
                canvas.drawText("You lost! :(", TextHolder.getX(), TextHolder.getY() - 300, TextHolder.getPaint());
            }

            if(!playerTurn){
                canvas.drawText("(Need 6).", 0, TextHolder.getY() - 300, TextHolder.getPaint());
            }
            if(!enemyTurn){
                canvas.drawText("(Need 6).", 0, TextHolder.getY() - 200, TextHolder.getPaint());
            }

            if(playerExtra>0){
                canvas.drawText("(Extra).", 0, TextHolder.getY() - 300, TextHolder.getPaint());
                canvas.drawText("(Wait).", 0, TextHolder.getY() - 200, TextHolder.getPaint());
            }

            if(enemyExtra>0){
                canvas.drawText("(Wait).", 0, TextHolder.getY() - 300, TextHolder.getPaint());
                canvas.drawText("(Extra).", 0, TextHolder.getY() - 200, TextHolder.getPaint());
            }

            myHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        touchX = motionEvent.getX();
        touchY = motionEvent.getY();

        System.out.println(touchX+"   "+touchY);

        return true;
    }


    Random random = new Random();

    public void doTurn(){

        int randomNum = random.nextInt((6 - 1) + 1) + 1; //(max-min+1) + min

        if(enemyExtra==0 && (playerCtrl.getPos()+randomNum)<=100) {
            playerDie = randomNum;
            playerExtra = 0;

            if (randomNum == 6) {
                playerExtra = 1;
            }

            if (!computerWin && playerTurn) {

                int type = 0;     //0 is regular, 1 is ladder, 2 is snake

                int targetPos = playerCtrl.getPos() + randomNum;

                int index;

                for (index = 0; index < snakeHeads.length; index++) {
                    if (targetPos == snakeHeads[index]) {
                        type = 2;
                        break;
                    }
                }

                for (int i = 0; i < ladderTails.length; i++) {
                    if (targetPos == ladderTails[i]) {
                        type = 0;
                        targetPos = ladderHeads[i];
                        playerExtra = 1;
                        break;
                    }
                }

                if (type == 0) {
                    while (targetPos != playerCtrl.getPos()) {
                        if (playerCtrl.getPos() % 10 != 0 && playerCtrl.getPos() < 100 && playerCtrl.getRow() % 2 != 0) {
                            playerCtrl.addPos();
                            playerCtrl.setX(playerCtrl.getX() + boardWidth / 10);
                        } else if (playerCtrl.getPos() % 10 != 0 && playerCtrl.getPos() < 100 && playerCtrl.getRow() % 2 == 0) {
                            playerCtrl.addPos();
                            playerCtrl.setX(playerCtrl.getX() - boardWidth / 10);
                        } else if (playerCtrl.getPos() % 10 == 0 && playerCtrl.getRow() % 2 != 0) {
                            playerCtrl.addPos();
                            playerCtrl.setX(boardWidth - boardWidth / 10);
                            playerCtrl.addRow();
                            playerCtrl.setY(playerCtrl.getY() - boardWidth / 10);
                        } else if (playerCtrl.getPos() % 10 == 0 && playerCtrl.getRow() % 2 == 0) {
                            playerCtrl.addPos();
                            playerCtrl.setX(0);
                            playerCtrl.addRow();
                            playerCtrl.setY(playerCtrl.getY() - boardWidth / 10);
                        }
                    }
                } else if (type == 2) {
                    targetPos = snakeTails[index];
                    while (targetPos != playerCtrl.getPos()) {
                        playerCtrl.subPos();
                        if (playerCtrl.getPos() % 10 != 0 && playerCtrl.getRow() % 2 != 0) {
                            playerCtrl.setX(playerCtrl.getX() - boardWidth / 10);
                        } else if (playerCtrl.getPos() % 10 != 0 && playerCtrl.getRow() % 2 == 0) {
                            playerCtrl.setX(playerCtrl.getX() + boardWidth / 10);
                        } else if (playerCtrl.getPos() % 10 == 0 && playerCtrl.getRow() % 2 != 0) {
                            playerCtrl.setX(0);
                            playerCtrl.subRow();
                            playerCtrl.setY(playerCtrl.getY() + boardWidth / 10);
                        } else if (playerCtrl.getPos() % 10 == 0 && playerCtrl.getRow() % 2 == 0) {
                            playerCtrl.setX(boardWidth - boardWidth / 10);
                            playerCtrl.subRow();
                            playerCtrl.setY(playerCtrl.getY() + boardWidth / 10);
                        }
                    }
                    playerTurn = false;
                }
            }
        }
        if (randomNum == 6) {
            playerTurn = true;
        }
        enemyTurn();
    }

    public void enemyTurn(){
        int randomNum = random.nextInt((6 - 1) + 1) + 1; //(max-min+1) + min

        if(enemyCtrl.getPos()<94)
            randomNum = 2*(random.nextInt((3 - 2) + 1) + 2); //(max-min+1) + min

        if(enemyCtrl.getPos()==1){
            randomNum = 1+ (random.nextInt((2 - 0) + 1) + 0); //(max-min+1) + min
        }


        if(playerExtra==0 && (enemyCtrl.getPos()+randomNum)<=100) {
            computerDie = randomNum;
            enemyExtra = 0;

            if (randomNum == 6) {
                enemyExtra = 1;
            }

            if (!playerWin && enemyTurn) {


                int type = 0;     //0 is regular, 1 is ladder, 2 is snake

                int targetPos = enemyCtrl.getPos() + randomNum;

                int index;

                for (index = 0; index < snakeHeads.length; index++) {
                    if (targetPos == snakeHeads[index]) {
                        type = 2;
                        break;
                    }
                }

                for (int i = 0; i < ladderTails.length; i++) {
                    if (targetPos == ladderTails[i]) {
                        type = 0;
                        targetPos = ladderHeads[i];
                        enemyExtra = 1;
                        break;
                    }
                }

                if (type == 0) {
                    while (targetPos != enemyCtrl.getPos()) {
                        if (enemyCtrl.getPos() % 10 != 0 && enemyCtrl.getPos() < 100 && enemyCtrl.getRow() % 2 != 0) {
                            enemyCtrl.addPos();
                            enemyCtrl.setX(enemyCtrl.getX() + boardWidth / 10);
                        } else if (enemyCtrl.getPos() % 10 != 0 && enemyCtrl.getPos() < 100 && enemyCtrl.getRow() % 2 == 0) {
                            enemyCtrl.addPos();
                            enemyCtrl.setX(enemyCtrl.getX() - boardWidth / 10);
                        } else if (enemyCtrl.getPos() % 10 == 0 && enemyCtrl.getRow() % 2 != 0) {
                            enemyCtrl.addPos();
                            enemyCtrl.setX(boardWidth - boardWidth / 10);
                            enemyCtrl.addRow();
                            enemyCtrl.setY(enemyCtrl.getY() - boardWidth / 10);
                        } else if (enemyCtrl.getPos() % 10 == 0 && enemyCtrl.getRow() % 2 == 0) {
                            enemyCtrl.addPos();
                            enemyCtrl.setX(0);
                            enemyCtrl.addRow();
                            enemyCtrl.setY(enemyCtrl.getY() - boardWidth / 10);
                        }
                    }
                } else if (type == 2) {
                    targetPos = snakeTails[index];
                    while (targetPos != enemyCtrl.getPos()) {
                        enemyCtrl.subPos();
                        if (enemyCtrl.getPos() % 10 != 0 && enemyCtrl.getRow() % 2 != 0) {
                            enemyCtrl.setX(enemyCtrl.getX() - boardWidth / 10);
                        } else if (enemyCtrl.getPos() % 10 != 0 && enemyCtrl.getRow() % 2 == 0) {
                            enemyCtrl.setX(enemyCtrl.getX() + boardWidth / 10);
                        } else if (enemyCtrl.getPos() % 10 == 0 && enemyCtrl.getRow() % 2 != 0) {
                            enemyCtrl.setX(0);
                            enemyCtrl.subRow();
                            enemyCtrl.setY(enemyCtrl.getY() + boardWidth / 10);
                        } else if (enemyCtrl.getPos() % 10 == 0 && enemyCtrl.getRow() % 2 == 0) {
                            enemyCtrl.setX(boardWidth - boardWidth / 10);
                            enemyCtrl.subRow();
                            enemyCtrl.setY(enemyCtrl.getY() + boardWidth / 10);
                        }
                    }
                    enemyTurn = false;
                }

            }
        }
        if (randomNum == 6) {
            enemyTurn = true;
        }

    }
}
