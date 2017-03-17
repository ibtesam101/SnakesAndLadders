package com.example.elfenlied.snakesandladders;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class myGameSurfaceHolder extends AppCompatActivity {

    Level1SurfaceView level1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_game_surface_holder);
        level1 = new Level1SurfaceView(this);
        LinearLayout surfaceLayout = (LinearLayout) findViewById(R.id.surfaceHolderLayout);
        surfaceLayout.getLayoutParams().width = getResources().getSystem().getDisplayMetrics().widthPixels;
        surfaceLayout.addView(level1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        level1.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        level1.resume();
    }

    public void turnPressed(View view) {
        level1.doTurn();
    }
}
