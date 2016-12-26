package com.realglobe.arshare;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

    /* 背景画像 */
    private ImageTaskTimer imageTaskTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
