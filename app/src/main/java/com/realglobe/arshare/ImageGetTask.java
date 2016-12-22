package com.realglobe.arshare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * サーバーから画像を取ってくるタスク。
 * 手元にある画像のハッシュ値とサーバーの画像のハッシュ値を比較して、異なっていたら更新する。
 */
public class ImageGetTask extends AsyncTask<Void, Void, Bitmap> {
    private String tag = this.getClass().getName();
    private ImageTaskTimer.UrlHolder urlHolder;
    private ImageView imageView;

    public ImageGetTask(ImageView imageView, ImageTaskTimer.UrlHolder urlHolder) {
        super();
        this.imageView = imageView;
        this.urlHolder = urlHolder;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            String url = getImageUrlFromServer();
            Log.d(tag, url);
            Boolean shouldUpdate = url.length() > 0 && !this.urlHolder.getUrl().equals(url);
            if (shouldUpdate) {
                this.urlHolder.setUrl(url);
                Bitmap bitmap = getImageFromServer();
                Log.d(tag, "Get Bitmap");
                return bitmap;
            }
        } catch (Exception e) {
            Log.w(tag, e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap == null) {
            return;
        }
        int width = this.imageView.getWidth();
        int height = this.imageView.getHeight();
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        this.imageView.setImageBitmap(bitmap);
    }

    private String getImageUrlFromServer() throws IOException, JSONException {
        URL url = new URL(Const.IMG_URL_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        JSONObject res = InputStreamToJson(con.getInputStream());
        String urlString = res.getString("url");
        return urlString;
    }

    private Bitmap getImageFromServer() throws IOException {
        String urlString = Const.URL + this.urlHolder.getUrl() + Const.IMG_QUERY;
        URL imageUrl = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) imageUrl.openConnection();
        InputStream input = con.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        return bitmap;
    }

    private JSONObject InputStreamToJson(InputStream is) throws IOException, JSONException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        JSONObject obj = new JSONObject(sb.toString());
        return obj;
    }
}
