package com.example.elfenlied.snakesandladders;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class myGameSurfaceHolder2 extends AppCompatActivity {

    Level2SurfaceView level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_game_surface_holder2);
        level = new Level2SurfaceView(this);
        LinearLayout surfaceLayout = (LinearLayout) findViewById(R.id.surfaceHolderLayout2);
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
