package com.example.elfenlied.snakesandladders;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class myGameSurfaceHolder3 extends AppCompatActivity {

    Level3SurfaceView level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_game_surface_holder3);
        level = new Level3SurfaceView(this);
        LinearLayout surfaceLayout = (LinearLayout) findViewById(R.id.surfaceHolderLayout3);
        surfaceLayout.getLayoutParams().width = getResources().getSystem().getDisplayMetrics().widthPixels;
        surfaceLayout.addView(level);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        level.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        level.resume();
    }

    public void turnPressed(View view) {
        level.doTurn();
    }
}
