package com.example.elfenlied.snakesandladders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LevelSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
    }

    public void toLevel1(View view) {
        Intent intent = new Intent(this, myGameSurfaceHolder.class);
        startActivity(intent);
    }
    public void toLevel2(View view) {
        Intent intent = new Intent(this, myGameSurfaceHolder2.class);
        startActivity(intent);
    }
    public void toLevel3(View view) {
        Intent intent = new Intent(this, myGameSurfaceHolder3.class);
        startActivity(intent);
    }
    public void toLevel4(View view) {
        Intent intent = new Intent(this, myGameSurfaceHolder4.class);
        startActivity(intent);
    }
    public void toLevel5(View view) {
        Intent intent = new Intent(this, myGameSurfaceHolder5.class);
        startActivity(intent);
    }
}
