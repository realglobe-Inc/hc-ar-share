package com.realglobe.arshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    /* 背景画像 */
    private ImageTaskTimer imageTaskTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 背景画像
        ImageView bgView = (ImageView) findViewById(R.id.shared_photo);
        this.imageTaskTimer = new ImageTaskTimer(bgView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.imageTaskTimer.startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.imageTaskTimer.cancelTimer();
    }
}
