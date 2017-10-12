package com.example.diycircleprogress;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private LevelProgressView level;
    private TextView start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (TextView) findViewById(R.id.start);
        level = (LevelProgressView) findViewById(R.id.level);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level.setSweepRadius(135);
                level.start();
            }
        });

        level.setOnDrawingFinishListener(new LevelProgressView.OnDrawingFinishListener() {
            @Override
            public void onDrawFinish(final double xPos, final double yPos) {
                // TODO: do what you want
            }
        });
    }

}
