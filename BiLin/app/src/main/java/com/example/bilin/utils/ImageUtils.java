package com.example.bilin.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhangdi on 2018/4/16.
 */

public class ImageUtils {
    private static  ImageUtils utils;
    private static AssetManager assetManager;
    private ImageUtils(Context context){
        assetManager = context.getAssets();
    }

    public static ImageUtils getInstance(Context context){
        synchronized (context){
            if(utils == null){
                utils = new ImageUtils(context);
            }
        }
        return utils;
    }

    /**
     * 获取asset 中的图片资源
     * @param fileName 图片名称
     * @return Bitmap
     */
    public static Bitmap getBitmap(String fileName) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            Log.d("zhangdi", "fileName =" + fileName);
            is = assetManager.open(fileName);
            bitmap = BitmapFactory.decodeStream(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("zhangdi", "getbitmap = " + bitmap);
        return bitmap;
    }
}
