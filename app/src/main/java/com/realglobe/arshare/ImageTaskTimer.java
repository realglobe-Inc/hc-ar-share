package com.realglobe.arshare;

import android.os.Handler;
import android.widget.ImageView;


/**
 * ImageGetTask を定期的に実行する
 */
public class ImageTaskTimer {
    private Handler handler;
    private ImageView view;
    private UrlHolder urlHolder = new UrlHolder(); // 画像の URL はこちらで管理
    private static final long DELAY = 8 * 1000;

    ImageTaskTimer(ImageView view) {
        this.handler = new Handler();
        this.view = view;
    }

    public void startTimer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ImageGetTask task = new ImageGetTask(view, urlHolder);
                task.execute();

                handler.postDelayed(this, DELAY);
            }
        }, 0);
    }

    public void cancelTimer() {
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 共有されている画像の URL を保持する
     */
    public static class UrlHolder {
        public String url = "";

        public void setUrl(String url) { this.url = url; }

        public String getUrl() { return url; }
    }
}
